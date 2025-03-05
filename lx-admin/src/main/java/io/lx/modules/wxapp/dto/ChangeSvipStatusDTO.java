package io.lx.modules.wxapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 更改会员状态
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Data
@Schema(name = "更改会员状态")
public class ChangeSvipStatusDTO implements Serializable {

	@SchemaProperty(name = "用户ID")
	private Long userId;

	/**
	 * 是否svip：0-非会员，1-终身全部会员
	 */
	@SchemaProperty(name = "是否svip")
	private String svip;
}
