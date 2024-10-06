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
 * @since 1.0.0 2024-09-21
 */
@Data
@Schema(name = "")
public class BannersDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Integer id;

	@SchemaProperty(name = "")
	private Integer routeCode;

	@SchemaProperty(name = "")
	private String oriName;

	@SchemaProperty(name = "")
	private String savedName;

	@SchemaProperty(name = "")
	private String path;

	@SchemaProperty(name = "")
	private Integer displayOrder;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;


}
