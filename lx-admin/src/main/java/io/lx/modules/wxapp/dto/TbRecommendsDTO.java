package io.lx.modules.wxapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 首页推荐
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-12-18
 */
@Data
@Schema(name = "首页推荐")
public class TbRecommendsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Integer id;

	@SchemaProperty(name = "")
	private String title;

	@SchemaProperty(name = "")
	private String subTitle;

	@SchemaProperty(name = "")
	private String type;

	@SchemaProperty(name = "")
	private String coverImgPath;

	@SchemaProperty(name = "")
	private BigDecimal price;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;

	@SchemaProperty(name = "")
	private Integer orders;


}
