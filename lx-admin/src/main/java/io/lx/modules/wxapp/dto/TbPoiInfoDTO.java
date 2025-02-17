package io.lx.modules.wxapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Data
@Schema(name = "")
public class TbPoiInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Long id;

	@SchemaProperty(name = "")
	private Integer guidesId;

	@SchemaProperty(name = "")
	private String title;

	@SchemaProperty(name = "")
	private String description;

	@SchemaProperty(name = "")
	private String poiType;

	@SchemaProperty(name = "")
	private BigDecimal latitude;

	@SchemaProperty(name = "")
	private BigDecimal longitude;

	@SchemaProperty(name = "")
	private String filePath;

	@SchemaProperty(name = "")
	private Integer status;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;

	@SchemaProperty(name = "")
	private String dateId;

	@SchemaProperty(name = "")
	private String journeyType;

}
