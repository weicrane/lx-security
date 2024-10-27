package io.lx.service;

import io.lx.common.service.CrudService;
import io.lx.dto.SvipDTO;
import io.lx.entity.SvipEntity;

import java.math.BigDecimal;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-27
 */
public interface SvipService extends CrudService<SvipEntity, SvipDTO> {
    // 查询价格
    BigDecimal getSvipPrice();

}