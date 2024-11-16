package io.lx.modules.wxapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;


/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Data
@Schema(name = "")
public class SubmitKmlDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private String name;

	@SchemaProperty(name = "")
	private String description;

	@SchemaProperty(name = "")
	@NotBlank(message = "行程类型不可为空，提示： 0-主线总览；1-主线行程；2-小众玩法；3-小众玩法总览")
	private String journeyType;

	@SchemaProperty(name = "")
	private String guideId;

	@SchemaProperty(name = "")
	@NotBlank(message = "kmlPath不可为空")
	private String kmlPath;


}
