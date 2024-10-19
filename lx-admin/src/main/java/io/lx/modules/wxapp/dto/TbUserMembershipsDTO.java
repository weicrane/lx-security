package io.lx.modules.wxapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
@Data
@Schema(name = "")
public class TbUserMembershipsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Long id;

	@SchemaProperty(name = "")
	private Long userId;

	@SchemaProperty(name = "")
	private String memberType;

	@SchemaProperty(name = "")
	private Integer travelGuidesId;

	@SchemaProperty(name = "")
	private Integer selfDrivingsId;

	@SchemaProperty(name = "")
	private Integer routesGuidesId;

	@SchemaProperty(name = "")
	private Date startDate;

	@SchemaProperty(name = "")
	private Date endDate;

	@SchemaProperty(name = "")
	private Date createdAt;


}
