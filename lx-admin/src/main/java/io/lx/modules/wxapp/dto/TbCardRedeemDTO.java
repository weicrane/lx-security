package io.lx.modules.wxapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 卡密兑换记录表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2025-03-16
 */
@Data
@Schema(name = "卡密兑换记录表")
public class TbCardRedeemDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "主键ID")
	private Integer id;

	@SchemaProperty(name = "用户ID")
	private Long userId;

	@SchemaProperty(name = "类别，00-终身会员，01-网盘路书，02-自驾活动，03-四季玩法")
	private String productType;

	@SchemaProperty(name = "线路玩法ID")
	private Integer routesGuidesId;

	@SchemaProperty(name = "16位卡密")
	private String cardCode;

	@SchemaProperty(name = "兑换时间")
	private Date redeemTime;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;

	@SchemaProperty(name = "备注")
	private String description;

	@SchemaProperty(name = "标题")
	private String tittle;
}
