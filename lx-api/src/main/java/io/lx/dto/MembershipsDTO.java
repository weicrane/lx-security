package io.lx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 
 * 简化版会员
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
@Data
@Schema(name = "")
public class MembershipsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Integer travelGuidesId;

	@SchemaProperty(name = "")
	private Integer selfDrivingsId;

	@SchemaProperty(name = "")
	private Integer routesGuidesId;

}
