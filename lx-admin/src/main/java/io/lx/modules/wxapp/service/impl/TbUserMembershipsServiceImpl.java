package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.exception.RenException;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbUserMembershipsDao;
import io.lx.modules.wxapp.dto.AddUserRoutesDTO;
import io.lx.modules.wxapp.dto.TbRoutesGuidesDTO;
import io.lx.modules.wxapp.dto.TbUserMembershipsDTO;
import io.lx.modules.wxapp.entity.TbRoutesGuidesEntity;
import io.lx.modules.wxapp.entity.TbUserEntity;
import io.lx.modules.wxapp.entity.TbUserMembershipsEntity;
import io.lx.modules.wxapp.service.TbRoutesGuidesService;
import io.lx.modules.wxapp.service.TbUserMembershipsService;
import io.lx.modules.wxapp.service.TbUserService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.lx.common.constant.AdminConstant.ORDER_TYPE_ROUTES;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
@Service
public class TbUserMembershipsServiceImpl extends CrudServiceImpl<TbUserMembershipsDao, TbUserMembershipsEntity, TbUserMembershipsDTO> implements TbUserMembershipsService {

    @Resource
    TbRoutesGuidesService tbRoutesGuidesService;

    @Resource
    TbUserService tbUserService;

    @Override
    public QueryWrapper<TbUserMembershipsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbUserMembershipsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    /**
     * 是否已出售
     * @param guideId
     * @return
     */
    @Override
    public Boolean isSold(Integer guideId){
        QueryWrapper<TbUserMembershipsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("routes_guides_id",guideId);
        List<TbUserMembershipsEntity> list = baseDao.selectList(wrapper);
        if (list.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * 查询用户拥有的线路分页
     */
    @Override
    public PageData<TbRoutesGuidesDTO> getUserRoutesByPage(Map<String, Object> params){

        QueryWrapper<TbUserMembershipsEntity> wrapper = new QueryWrapper<>();
        // 1.用户id
        Long userId = Long.valueOf(params.get("userId").toString());
        wrapper.eq("user_id",userId);
        // 2.线路类型
        wrapper.eq("member_type",ORDER_TYPE_ROUTES);
        // 3.分页查询
        IPage<TbUserMembershipsEntity> page ;
        page = baseDao.selectPage(getPage(params, "updated_at", false), wrapper);
        // 4.查询线路详情，构造返回（根据线路id查询详情）
        List<TbRoutesGuidesDTO> dtoList = page.getRecords().stream().map(entity -> {
            TbRoutesGuidesEntity routeEntity = tbRoutesGuidesService.selectById(entity.getRoutesGuidesId());
            TbRoutesGuidesDTO dto = convertToDTO(routeEntity);
            return dto;
        }).collect(Collectors.toList());

        return new PageData<>(dtoList, page.getTotal(),page.getSize(),page.getPages(),page.getCurrent());

    }
    // 转换实体对象到 DTO
    private TbRoutesGuidesDTO convertToDTO(TbRoutesGuidesEntity entity) {
        TbRoutesGuidesDTO dto = new TbRoutesGuidesDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    /**
     * 给用户添加线路权益
     * 0.判断是否会员
     * 1.查询是否已存在
     * 2.添加
     */
    @Override
    public void addUserRoutes(AddUserRoutesDTO dto){
        // 1.用户id
        Long userId = dto.getUserId();
        TbUserEntity userEntity = tbUserService.getUserEntity(userId);
        if (userEntity == null){
            throw new RenException("找不到用户ID:"+userId);
        }
        if ("1".equals(userEntity.getSvip())){
            throw new RenException("用户已是会员，无需添加");
        }

        QueryWrapper<TbUserMembershipsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        // 2.线路类型
        wrapper.eq("member_type",ORDER_TYPE_ROUTES);
        // 3.线路id
        wrapper.eq("routes_guides_id",dto.getRoutesGuidesId());
        List<TbUserMembershipsEntity> list = baseDao.selectList(wrapper);
        if (!list.isEmpty()){
            throw new RenException("用户已存在此线路权限");
        }

        TbUserMembershipsEntity entity = new TbUserMembershipsEntity();
        entity.setUserId(userId);
        entity.setMemberType(ORDER_TYPE_ROUTES);
        entity.setRoutesGuidesId(dto.getRoutesGuidesId());

        // 写入数据库
        baseDao.insert(entity);
    }

    /**
     * 给用户删除线路权益
     * 1.查询是否存在
     * 2.删除
     */
    @Override
    public void deleteUserRoutes(AddUserRoutesDTO dto){
        QueryWrapper<TbUserMembershipsEntity> wrapper = new QueryWrapper<>();
        // 1.用户id
        Long userId = dto.getUserId();
        wrapper.eq("user_id",userId);
        // 2.线路类型
        wrapper.eq("member_type",ORDER_TYPE_ROUTES);
        // 3.线路id
        wrapper.eq("routes_guides_id",dto.getRoutesGuidesId());
        List<TbUserMembershipsEntity> list = baseDao.selectList(wrapper);
        if (list.isEmpty()){
            throw new RenException("用户不存在此线路权限");
        }
        // 4.删除
        baseDao.delete(wrapper);
    }

}