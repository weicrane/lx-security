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
 * @since 1.0.0 2024-10-20
 */
@Data
@Schema(name = "")
public class TbRoadDiscussDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Integer id;

	@SchemaProperty(name = "")
	private String content;

	@SchemaProperty(name = "")
	private String status;

	@SchemaProperty(name = "")
	private Long userId;

	@SchemaProperty(name = "")
	private Integer conditionId;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;


}
