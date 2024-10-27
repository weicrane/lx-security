package io.lx.service;

import io.lx.common.service.CrudService;
import io.lx.dto.UserMembershipsDTO;
import io.lx.entity.UserMembershipsEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
public interface UserMembershipsService extends CrudService<UserMembershipsEntity, UserMembershipsDTO> {

    Map<String, Object> getMembershipsByUserId(Long userId);

    void updateUserMemShips(String orderId);
}