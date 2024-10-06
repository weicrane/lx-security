package io.lx.modules.wxapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户信息表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Data
@Schema(name = "用户信息表")
public class TbUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Long id;

	@SchemaProperty(name = "")
	private String username;

	@SchemaProperty(name = "")
	private String password;

	@SchemaProperty(name = "")
	private String email;

	@SchemaProperty(name = "")
	private String mobile;

	@SchemaProperty(name = "")
	private String gender;

	@SchemaProperty(name = "")
	private String avatarUrl;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;


}
