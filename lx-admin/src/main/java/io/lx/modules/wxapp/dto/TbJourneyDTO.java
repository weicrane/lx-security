package io.lx.modules.wxapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Data
@Schema(name = "")
public class TbJourneyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Integer id;

	@SchemaProperty(name = "")
	@NotBlank(message = "name不能为空")
	private String name;

	@SchemaProperty(name = "")
	private String description;

	@SchemaProperty(name = "")
	@NotBlank(message = "journeyType类型不能为空，提示：1-主线行程；2-小众玩法")
	private String journeyType;

	@SchemaProperty(name = "")
	private Integer dateId;

	@SchemaProperty(name = "")
	private String duration;

	@SchemaProperty(name = "")
	private String kmlPath;

	@SchemaProperty(name = "")
	private String travelType;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;

	@SchemaProperty(name = "")
	private String intro;

	@SchemaProperty(name = "")
	@NotBlank(message = "线路id不能为空")
	private String guideId;

}
