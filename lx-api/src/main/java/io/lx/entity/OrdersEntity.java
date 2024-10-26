package io.lx.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-26
 */
@Data
@TableName("tb_orders")
public class OrdersEntity {

    /**
     * 
     */
	private String orderId;
    /**
     * 
     */
	private Long userId;
    /**
     * 
     */
	private String openid;
    /**
     * 
     */
	private String productType;
    /**
     * 
     */
	private Long productId;
    /**
     * 
     */
	private BigDecimal amount;
    /**
     * 
     */
	private String status;
    /**
     * 
     */
	private Date createdAt;
    /**
     * 
     */
	private Date updatedAt;
    /**
     *
     */
    private String description;

}