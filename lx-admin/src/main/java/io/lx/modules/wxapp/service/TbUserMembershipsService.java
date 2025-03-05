package io.lx.modules.wxapp.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.modules.wxapp.dto.AddUserRoutesDTO;
import io.lx.modules.wxapp.dto.TbRoutesGuidesDTO;
import io.lx.modules.wxapp.dto.TbUserMembershipsDTO;
import io.lx.modules.wxapp.entity.TbUserMembershipsEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
public interface TbUserMembershipsService extends CrudService<TbUserMembershipsEntity, TbUserMembershipsDTO> {

    /**
     * 是否有人购买
     */
    Boolean isSold(Integer guideId);

    /**
     * 查询用户拥有的线路分页
     */
    PageData<TbRoutesGuidesDTO> getUserRoutesByPage(Map<String, Object> params);

    /**
     * 给用户新增线路权益
     *
     */
    void addUserRoutes(AddUserRoutesDTO dto);
    /**
     * 给用户删除线路权益
     *
     */
    void deleteUserRoutes(AddUserRoutesDTO dto);

}