package io.lx.service;

import io.lx.common.service.CrudService;
import io.lx.dto.SelfDrivingsApplyDTO;
import io.lx.entity.SelfDrivingsApplyEntity;
import io.lx.entity.UserEntity;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-29
 */
public interface SelfDrivingsApplyService extends CrudService<SelfDrivingsApplyEntity, SelfDrivingsApplyDTO> {

    /**
     * 提交报名表
     * @param dto
     * @param user
     */
    void submitApply(SelfDrivingsApplyDTO dto, UserEntity user);
}