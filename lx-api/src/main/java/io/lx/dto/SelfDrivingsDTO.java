package io.lx.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class SelfDrivingsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@TableId
	@SchemaProperty(name = "")
	private Integer id;

	@SchemaProperty(name = "")
	private String title;

	@SchemaProperty(name = "")
	private String subTitle;

	@SchemaProperty(name = "")
	private Integer travelType;

	@SchemaProperty(name = "")
	private String destinationCity;

	@SchemaProperty(name = "")
	private String departureCity;

	@SchemaProperty(name = "")
	private String tag;

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

	@JsonFormat(pattern = "yyyy-MM-dd")
	@SchemaProperty(name = "")
	private Date startDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@SchemaProperty(name = "")
	private Date endDate;

}
