package io.lx.modules.wxapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class TbOrdersEntity {

    /**
     * 
     */
    @TableId(type = IdType.AUTO) // 使用数据库的自增策略
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
	private Integer productId;
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
    /**
     *
     */
    private String mobile;
}