package io.lx.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.dto.GetApplyDTO;
import io.lx.dto.SelfDrivingsApplyDTO;
import io.lx.entity.SelfDrivingsApplyEntity;
import io.lx.entity.UserEntity;

import java.util.Map;

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
    Map<String,String> submitApply(SelfDrivingsApplyDTO dto, UserEntity user);


    PageData<SelfDrivingsApplyDTO> getSelfDriApplyByPage(Map<String, Object> params, String keyword,UserEntity user);

    /**
     * 取消
     * @param dto
     * @param user
     */
    void cancelApply(GetApplyDTO dto, UserEntity user);

    /**
     * 查询我的自驾报名详情
     * @param applyId
     * @return
     */
    Map<String, Object> getApplyDetailById(String applyId,UserEntity user);

    /**
     * 更新支付
     * @param orderId
     * @param status
     */
    void updatePayStatus(String orderId,String status);

    /**
     * 查询详情
     * @param applyId
     * @return
     */
    SelfDrivingsApplyEntity getSelfDrivingsApply(String applyId);

}