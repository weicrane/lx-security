package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.exception.RenException;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbUserDao;
import io.lx.modules.wxapp.dto.ChangeSvipStatusDTO;
import io.lx.modules.wxapp.dto.TbUserDTO;
import io.lx.modules.wxapp.entity.TbUserEntity;
import io.lx.modules.wxapp.service.TbUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户信息表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Service
public class TbUserServiceImpl extends CrudServiceImpl<TbUserDao, TbUserEntity, TbUserDTO> implements TbUserService {

    @Override
    public QueryWrapper<TbUserEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbUserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    /**
     * 查询用户信息
     *
     */
    @Override
    public TbUserEntity getUserEntity(Long userId){
        if (userId ==null){
            throw new RenException("用户ID为空");
        }
        return baseDao.selectById(userId);
    }

    /**
     * 更改svip会员状态
     */
    public void changeSvipStatus(ChangeSvipStatusDTO dto){
        TbUserEntity entity = baseDao.selectById(dto.getUserId());
        if (entity == null) {
            throw new RenException("用户ID查询为空: " + dto.getUserId());
        }

        if (Objects.equals(entity.getSvip(), dto.getSvip())) {
            throw new RenException("与当前会员状态一致，无需更改");
        }

        String svip = String.valueOf(dto.getSvip());
        if ("0".equals(svip) || "1".equals(svip)) {
            entity.setSvip(svip);
            entity.setUpdatedAt(new Date());
            baseDao.updateById(entity);
        } else {
            throw new RenException("入参非法");
        }
    }

    /**
     * 获取用户列表-分页
     */
    public PageData<TbUserDTO> getUserListByPage(Map<String, Object> params){

        QueryWrapper<TbUserEntity> wrapper = new QueryWrapper<>();

        // 处理 userId 查询
        if (params.get("userId") != null && !params.get("userId").toString().isBlank()) {
            try {
                Long userId = Long.parseLong(params.get("userId").toString());
                wrapper.eq("id", userId);
            } catch (NumberFormatException e) {
                throw new RenException("用户ID格式不正确");
            }
        }
        // 处理 mobile 查询
        if (params.get("mobile") != null && StrUtil.isNotBlank(params.get("mobile").toString())) {
            wrapper.like("mobile", params.get("mobile").toString());
        }
        // 处理 isSvip 查询
        if (params.get("isSvip") != null && StrUtil.isNotBlank(params.get("isSvip").toString())) {
            wrapper.eq("svip", params.get("isSvip").toString());
        }

        // 分页查询
        IPage<TbUserEntity> page = baseDao.selectPage(getPage(params, "updated_at", false), wrapper);

        List<TbUserDTO> dtoList = page.getRecords().stream().map(entity -> {
            TbUserDTO dto = convertToDTO(entity);
            return dto;
        }).collect(Collectors.toList());

        return new PageData<>(dtoList, page.getTotal(),page.getSize(),page.getPages(),page.getCurrent());

    }
    // 转换实体对象到 DTO
    private TbUserDTO convertToDTO(TbUserEntity entity) {
        TbUserDTO dto = new TbUserDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}