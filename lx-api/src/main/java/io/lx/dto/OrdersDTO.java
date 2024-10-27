package io.lx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-26
 */
@Data
@Schema(name = "小程序订单表")
public class OrdersDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private String orderId;

	@SchemaProperty(name = "")
	private Long userId;

	@SchemaProperty(name = "")
	private String openid;

	@SchemaProperty(name = "产品类型")
	@NotBlank(message="产品类型不能为空")
	private String productType;

	@SchemaProperty(name = "产品ID")
	@NotNull(message="产品ID不能为空")
	private Integer productId;

	@SchemaProperty(name = "金额（元）")
	@NotNull(message="价格不能为空")
	private BigDecimal amount;

	@SchemaProperty(name = "")
	private String status;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;

	@SchemaProperty(name = "订单描述")
	@NotBlank(message="商品描述不能为空")
	private String description;


}
