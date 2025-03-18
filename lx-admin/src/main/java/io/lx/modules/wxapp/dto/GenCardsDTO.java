package io.lx.modules.wxapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 批量生成卡密
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2025-03-16
 */
@Data
@Schema(name = "批量生成卡密")
public class GenCardsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@NotNull
	@SchemaProperty(name = "数量")
	private Integer amount;

	@NotNull
	@SchemaProperty(name = "有效期")
	private Date expireTime;

	@NotBlank
	@SchemaProperty(name = "类别，00-终身会员，01-网盘路书，02-自驾活动，03-四季玩法")
	private String productType;

	@SchemaProperty(name = "线路玩法ID")
	private Integer routesGuidesId;

	@SchemaProperty(name = "备注")
	private String description;

}
