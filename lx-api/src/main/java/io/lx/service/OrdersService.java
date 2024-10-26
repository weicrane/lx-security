package io.lx.service;

import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import io.lx.common.service.CrudService;
import io.lx.dto.OrdersDTO;
import io.lx.entity.OrdersEntity;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-26
 */
public interface OrdersService extends CrudService<OrdersEntity, OrdersDTO> {

    /**
     * 写入预支付订单
     */
    void creatOrder(OrdersDTO ordersDTO);

    /**
     * 查询商品金额
     */
    Amount getAmount(String productType,Long productId);

}