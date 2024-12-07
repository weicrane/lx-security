package io.lx.dto;

import io.lx.entity.RoadDiscussEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-03
 */
@Data
@Schema(name = "")
public class RoadConditionsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Integer id;

	@SchemaProperty(name = "标题")
	@NotBlank(message="标题不能为空")
	private String title;

	@SchemaProperty(name = "内容")
	@NotBlank(message="内容不能为空")
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

	/**
	 *
	 */
	@SchemaProperty(name = "")
	private String address;

	/**
	 * 评论区
	 */
	private List<RoadDiscussEntity> discussList;

}
