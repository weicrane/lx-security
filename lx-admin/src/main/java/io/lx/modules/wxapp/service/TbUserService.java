package io.lx.modules.wxapp.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.modules.wxapp.dto.ChangeSvipStatusDTO;
import io.lx.modules.wxapp.dto.TbUserDTO;
import io.lx.modules.wxapp.entity.TbUserEntity;

import java.util.Map;

/**
 * 用户信息表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
public interface TbUserService extends CrudService<TbUserEntity, TbUserDTO> {

    /**
     * 查询用户信息
     *
     */
    TbUserEntity getUserEntity(Long userId);

    /**
     * 更改svip会员状态
     */
    void changeSvipStatus(ChangeSvipStatusDTO dto);

    /**
     * 获取用户列表-分页
     */
    PageData<TbUserDTO> getUserListByPage(Map<String, Object> params);

}