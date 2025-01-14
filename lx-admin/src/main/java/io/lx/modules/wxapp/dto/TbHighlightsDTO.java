package io.lx.modules.wxapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-09
 */
@Data
@Schema(name = "")
public class TbHighlightsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Integer id;

	@SchemaProperty(name = "")
	@NotNull(message = "journeyId不能为空")
	private Integer journeyId;

	@SchemaProperty(name = "")
	private Integer lightSeq;

	@SchemaProperty(name = "")
	@NotBlank(message = "name不能为空")
	private String name;

	@SchemaProperty(name = "")
	@NotBlank(message = "description不能为空")
	private String description;

	@SchemaProperty(name = "")
	private String filePath;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;


}
