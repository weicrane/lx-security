package io.lx.modules.wxapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
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
@Schema(name = "小程序订单")
public class TbOrdersDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private String orderId;

	@SchemaProperty(name = "")
	private Long userId;

	@SchemaProperty(name = "")
	private String openid;

	@SchemaProperty(name = "")
	private String productType;

	@SchemaProperty(name = "")
	private Integer productId;

	@SchemaProperty(name = "")
	private BigDecimal amount;

	@SchemaProperty(name = "")
	private String status;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;

	@SchemaProperty(name = "")
	private String description;

	@SchemaProperty(name = "")
	private String mobile;

}
