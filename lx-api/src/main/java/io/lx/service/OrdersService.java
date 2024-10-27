package io.lx.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.dto.OrdersDTO;
import io.lx.entity.OrdersEntity;
import io.lx.entity.UserEntity;

import java.util.Map;

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
     * 查询商品金额 单位：分
     */
//    Amount getAmount(String productType,Integer productId);

    /**
     * 查询订单状态
     */
    String getOederStatus(String orderId);

    /**
     * 更新订单状态
     */
    void updateOrderStatus(String orderId,String status);

    /**
     * 查询订单详情
     */
    OrdersEntity getOrderDetail(String orderId);

    PageData<OrdersDTO> getOrderListByPage(Map<String, Object> params,
                                            String keyword,
                                            String status,
                                            UserEntity user);

}