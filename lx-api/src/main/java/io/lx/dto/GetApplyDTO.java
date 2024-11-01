package io.lx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;


/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-29
 */
@Data
@Schema(name = "")
public class GetApplyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "报名单号")
	@NotBlank
	private String applyId;


}
