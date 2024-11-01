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
 * @since 1.0.0 2024-10-31
 */
@Data
@Schema(name = "")
public class TbPartnersApplyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private String applyId;

	@SchemaProperty(name = "")
	private Long userId;

	@SchemaProperty(name = "")
	private Integer partnersId;

	@SchemaProperty(name = "")
	private String title;

	@SchemaProperty(name = "")
	private String name;

	@SchemaProperty(name = "")
	private String phone;

	@SchemaProperty(name = "")
	private Integer adults;

	@SchemaProperty(name = "")
	private Integer children;

	@SchemaProperty(name = "")
	private String remark;

	@SchemaProperty(name = "")
	private String status;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;


}
