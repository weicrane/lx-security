package io.lx.modules.wxapp.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.modules.wxapp.dto.TbCardRedeemDTO;
import io.lx.modules.wxapp.entity.TbCardRedeemEntity;

import java.util.Map;

/**
 * 卡密兑换记录表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2025-03-16
 */
public interface TbCardRedeemService extends CrudService<TbCardRedeemEntity, TbCardRedeemDTO> {
    /**
     * 搜索卡密兑换列表-分页
     */
    PageData<TbCardRedeemDTO> searchByPage(Map<String, Object> params);
}