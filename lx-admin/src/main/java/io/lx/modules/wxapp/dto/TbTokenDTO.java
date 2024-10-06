package io.lx.modules.wxapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户Token
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Data
@Schema(name = "用户Token")
public class TbTokenDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "id")
	private Long id;

	@SchemaProperty(name = "用户ID")
	private Long userId;

	@SchemaProperty(name = "token")
	private String token;

	@SchemaProperty(name = "过期时间")
	private Date expireDate;

	@SchemaProperty(name = "更新时间")
	private Date updateDate;


}
