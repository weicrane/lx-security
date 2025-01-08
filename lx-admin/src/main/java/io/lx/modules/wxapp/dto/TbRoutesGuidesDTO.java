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
 * @since 1.0.0 2024-10-19
 */
@Data
@Schema(name = "")
public class TbRoutesGuidesDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Integer id;

	@SchemaProperty(name = "")
	private String title;

	@SchemaProperty(name = "")
	private String subTitle;

	@SchemaProperty(name = "")
	private Integer travelType;

	@SchemaProperty(name = "")
	private String season;

	@SchemaProperty(name = "")
	private String destinationCity;

	@SchemaProperty(name = "")
	private String departureCity;

	@SchemaProperty(name = "")
	private String region;

	@SchemaProperty(name = "")
	private String purchaseNotes;

	@SchemaProperty(name = "")
	private String coverImgPath;

	@SchemaProperty(name = "")
	private String detailImgPath;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;

	@SchemaProperty(name = "")
	private BigDecimal price;

	@SchemaProperty(name = "")
	private Integer inventory;

	@SchemaProperty(name = "")
	private String mapImgPath;

	@SchemaProperty(name = "")
	private String onsale;

}
