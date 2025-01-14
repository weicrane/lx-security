package io.lx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
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
public class PicsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Long id;

	@SchemaProperty(name = "")
	private Integer guideId;

	@SchemaProperty(name = "")
	private String oriName;

	@SchemaProperty(name = "")
	private String savedName;

	@SchemaProperty(name = "")
	private String filePath;

	@SchemaProperty(name = "")
	private Date uploadTime;

	@SchemaProperty(name = "")
	private Integer status;

	@SchemaProperty(name = "")
	private String picType;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;


}
