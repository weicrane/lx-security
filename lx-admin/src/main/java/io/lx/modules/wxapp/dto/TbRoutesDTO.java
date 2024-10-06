package io.lx.modules.wxapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import java.math.BigDecimal;

/**
 * 路线信息表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Data
@Schema(name = "路线信息表")
public class TbRoutesDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Integer id;

	@SchemaProperty(name = "")
	private Integer routeCode;

	@SchemaProperty(name = "")
	private String name;

	@SchemaProperty(name = "")
	private String description;

	@SchemaProperty(name = "")
	private BigDecimal price;

	@SchemaProperty(name = "")
	private String duration;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;


}
