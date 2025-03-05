package io.lx.modules.wxapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;


/**
 * 
 * 给用户添加线路权益
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
@Data
@Schema(name = "")
public class AddUserRoutesDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@NotNull
	@SchemaProperty(name = "")
	private Long userId;

	@NotBlank
	@SchemaProperty(name = "")
	private Integer routesGuidesId;

}
