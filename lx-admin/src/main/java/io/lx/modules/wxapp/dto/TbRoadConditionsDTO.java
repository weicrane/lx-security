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
 * @since 1.0.0 2024-10-20
 */
@Data
@Schema(name = "")
public class TbRoadConditionsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Integer id;

	@SchemaProperty(name = "")
	private String title;

	@SchemaProperty(name = "")
	private String content;

	@SchemaProperty(name = "")
	private String tag;

	@SchemaProperty(name = "")
	private String imgPath;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;

	@SchemaProperty(name = "")
	private String status;

	@SchemaProperty(name = "")
	private Long userId;

	@SchemaProperty(name = "")
	private String userType;

	@SchemaProperty(name = "")
	private String type;
	/**
	 *
	 */
	@SchemaProperty(name = "")
	private BigDecimal latitude;
	/**
	 *
	 */
	@SchemaProperty(name = "")
	private BigDecimal longitude;

	@SchemaProperty(name = "")
	private String address;


}
