package io.lx.modules.wxapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-27
 */
@Data
@Schema(name = "")
public class TbTransactionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private String transactionId;

	@SchemaProperty(name = "")
	private String outTradeNo;

	@SchemaProperty(name = "")
	private Integer total;

	@SchemaProperty(name = "")
	private String tradeState;

	@SchemaProperty(name = "")
	private String tradeType;

	@SchemaProperty(name = "")
	private String tradeStateDesc;

	@SchemaProperty(name = "")
	private String bankType;

	@SchemaProperty(name = "")
	private String successTime;

	@SchemaProperty(name = "")
	private String payer;

	@SchemaProperty(name = "")
	private String amount;

	@SchemaProperty(name = "")
	private String sceneInfo;

	@SchemaProperty(name = "")
	private String promotionDetail;

	@SchemaProperty(name = "")
	private String failMessage;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;


}
