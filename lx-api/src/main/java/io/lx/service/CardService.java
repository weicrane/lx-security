package io.lx.service;

import io.lx.common.service.CrudService;
import io.lx.dto.CardDTO;
import io.lx.entity.CardEntity;
import io.lx.entity.UserEntity;

/**
 * 卡密表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2025-03-16
 */
public interface CardService extends CrudService<CardEntity, CardDTO> {

    /**
     * 修改卡密
     * @param dto
     */
    void exchangeCard(CardDTO dto, UserEntity user);

}