package io.lx.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.dto.GetApplyDTO;
import io.lx.dto.PartnersApplyDTO;
import io.lx.entity.PartnersApplyEntity;
import io.lx.entity.UserEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-31
 */
public interface PartnersApplyService extends CrudService<PartnersApplyEntity, PartnersApplyDTO> {

    /**
     * 提交预约表
     * @param dto
     * @param user
     */
    Map<String,String> submitApply(PartnersApplyDTO dto, UserEntity user);

    PageData<PartnersApplyDTO> getPartnersApplyByPage(Map<String, Object> params, String keyword, UserEntity user);

    /**
     * 取消
     * @param dto
     * @param user
     */
    void cancelApply(GetApplyDTO dto, UserEntity user);


    /**
     * 查询我的商家预约详情
     * @param applyId
     * @return
     */
    Map<String, Object> getApplyDetailById(String applyId,UserEntity user);

    /**
     * 更新支付状态
     * @param orderId
     * @param status
     */
    void updatePayStatus(String orderId,String status);
}