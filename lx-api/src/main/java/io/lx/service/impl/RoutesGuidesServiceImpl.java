package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.RoutesGuidesDao;
import io.lx.dto.RoutesGuidesDTO;
import io.lx.entity.RoutesGuidesEntity;
import io.lx.entity.UserEntity;
import io.lx.service.RoutesGuidesService;
import io.lx.service.UserMembershipsService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.lx.constant.ApiConstant.ONE_STRING;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
@Service
public class RoutesGuidesServiceImpl extends CrudServiceImpl<RoutesGuidesDao, RoutesGuidesEntity, RoutesGuidesDTO> implements RoutesGuidesService {
    @Resource
    UserMembershipsService userMembershipsService;

    @Override
    public QueryWrapper<RoutesGuidesEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<RoutesGuidesEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public  PageData<RoutesGuidesDTO> getAllRoutesGuidesByPage(String keyword, String season, Map<String, Object> params){

        QueryWrapper<RoutesGuidesEntity> wrapper = new QueryWrapper<>();
        // 0.必须是已上架
        wrapper.eq("onsale",ONE_STRING);

        // 1.关键词模糊匹配
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like("title", keyword)
                    .or().like("sub_title", keyword);
        }

        // 2.季节筛选
        if (StrUtil.isNotBlank(season)) {
            wrapper.eq("season", season);
        }

        // 3.分页查询
        IPage<RoutesGuidesEntity> page ;
        page = baseDao.selectPage(getPage(params, "updated_at", false), wrapper);

        // 4.构造返回
        // 将结果转换为 DTO 并处理解密逻辑
        List<RoutesGuidesDTO> dtoList = page.getRecords().stream().map(entity -> {
            RoutesGuidesDTO dto = convertToDTO(entity);
            return dto;
        }).collect(Collectors.toList());

        return new PageData<>(dtoList, page.getTotal(),page.getSize(),page.getPages(),page.getCurrent());

    }

    // 转换实体对象到 DTO
    private RoutesGuidesDTO convertToDTO(RoutesGuidesEntity entity) {
        RoutesGuidesDTO dto = new RoutesGuidesDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public RoutesGuidesDTO getRoutesGuidesDetail(Integer id){
        RoutesGuidesEntity entity = baseDao.selectById(id);
        RoutesGuidesDTO dto = new RoutesGuidesDTO();
        // 已上架才返回
        if (ONE_STRING.equals(entity.getOnsale())){
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }

    /**
     * 获取我的玩法指南列表
     * @param keyword
     * @param params
     * @param user
     * @return
     */
    @Override
    public PageData<RoutesGuidesDTO> getMyGuidesByPage(String keyword, Map<String, Object> params, UserEntity user){
        // 1.获取用户会员身份
        String svip = user.getSvip();

        // 创建分页对象
        IPage<RoutesGuidesEntity> page = null;

        // 初始化查询条件
        QueryWrapper<RoutesGuidesEntity> wrapper = new QueryWrapper<>();

        // 根据是否svip进行查询: 0-非会员，1-终身全部会员
        if (ONE_STRING.equals(svip)){
            // 终身会员，返回所有产品的分页列表
            // 如果有 keyword，按 title 和 subtitle 模糊匹配
            if (StrUtil.isNotBlank(keyword)) {
                wrapper.like("title", keyword)
                        .or()
                        .like("sub_title", keyword);
            }
            page = baseDao.selectPage(getPage(params, null, false), wrapper);
        }else {
            Map<String, Object> memberships = userMembershipsService.getMembershipsByUserId(user.getId());
            List<Integer> routesGuidesIds = (List<Integer>) memberships.get("routesGuidesIds");
            if (routesGuidesIds.isEmpty()) {
                // 如果用户没有已购产品，返回空分页
                return new PageData<>(Collections.emptyList(), 0,0,0,0);
            }
            // 将购买的产品ID设置为查询条件，结合模糊搜索条件
            wrapper.in("id", routesGuidesIds);
            // 如果有 keyword，按 title 和 subtitle 模糊匹配
            if (StrUtil.isNotBlank(keyword)) {
                wrapper.like("title", keyword)
                        .or()
                        .like("sub_title", keyword);
            }
            page = baseDao.selectPage(getPage(params, null, false), wrapper);
        }

        // 将结果转换为 DTO 并处理解密逻辑
        List<RoutesGuidesDTO> dtoList = page.getRecords().stream().map(entity -> {
            RoutesGuidesDTO dto = convertToDTO(entity);
            return dto;
        }).collect(Collectors.toList());

        return new PageData<>(dtoList, page.getTotal(),page.getSize(),page.getPages(),page.getCurrent());

    }



}