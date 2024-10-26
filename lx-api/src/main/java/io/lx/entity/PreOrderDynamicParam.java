package io.lx.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Schema(name = "动态下单参数")
@Component
public class PreOrderDynamicParam {
    /**
     * 订单号（业务）：后端生成
     */
    String outTradeNo;
    /**
     * 用户openId
     */
    String openId;
    /**
     * 订单描述
     */
    String description;
    /**
     * 订单总金额，单位为分
     */
    int total;
}
