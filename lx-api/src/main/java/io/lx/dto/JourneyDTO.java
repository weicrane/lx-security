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
public class JourneyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Integer id;

	@SchemaProperty(name = "")
	private String name;

	@SchemaProperty(name = "")
	private String description;

	@SchemaProperty(name = "")
	private String journeyType;

	@SchemaProperty(name = "")
	private String dateId;

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


}
