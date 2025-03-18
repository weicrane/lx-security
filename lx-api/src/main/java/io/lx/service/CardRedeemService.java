package io.lx.service;

import io.lx.common.service.CrudService;
import io.lx.dto.CardRedeemDTO;
import io.lx.entity.CardRedeemEntity;

/**
 * 卡密兑换记录表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2025-03-16
 */
public interface CardRedeemService extends CrudService<CardRedeemEntity, CardRedeemDTO> {

    @Override
    boolean insert(CardRedeemEntity entity);
}