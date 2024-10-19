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
 * @since 1.0.0 2024-10-10
 */
@Data
@Schema(name = "")
public class TbPoiImagesDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Long id;

	@SchemaProperty(name = "")
	private Long poiId;

	@SchemaProperty(name = "")
	private String imageUrl;

	@SchemaProperty(name = "")
	private String title;

	@SchemaProperty(name = "")
	private String description;

	@SchemaProperty(name = "")
	private String imgName;

	@SchemaProperty(name = "")
	private String imgPath;

	@SchemaProperty(name = "")
	private Date createdAt;


}
