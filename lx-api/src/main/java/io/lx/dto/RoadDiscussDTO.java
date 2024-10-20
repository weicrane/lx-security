package io.lx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-20
 */
@Data
@Schema(name = "")
public class RoadDiscussDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Integer id;

	@SchemaProperty(name = "内容")
	@NotBlank(message="内容不能为空")
	private String content;

	@SchemaProperty(name = "")
	private String status;

	@SchemaProperty(name = "")
	private Long userId;

	@SchemaProperty(name = "公告id")
	@NotNull(message="公告id不能为空")
	private Integer conditionId;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;

	@SchemaProperty(name = "")
	private String nickname;

	@SchemaProperty(name = "")
	private String avatarUrl;

}
