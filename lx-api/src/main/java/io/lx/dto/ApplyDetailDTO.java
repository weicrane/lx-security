package io.lx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-29
 */
@Data
@Schema(name = "自驾报名单详情")
public class ApplyDetailDTO implements Serializable {

	// 报名表参数

	@SchemaProperty(name = "报名单号")
	private String applyId;

	@SchemaProperty(name = "")
	private Long userId;

	@SchemaProperty(name = "自驾活动ID不可为空")
	@NotNull
	private Integer selfDrivingId;

	@SchemaProperty(name = "姓名不能为空")
	private String name;

	@SchemaProperty(name = "手机号不能为空")
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

	@SchemaProperty(name = "")
	private String vehicleType;

	@SchemaProperty(name = "")
	private String plate;


	// 自驾活动参数
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
	private BigDecimal price;

	@SchemaProperty(name = "")
	private Integer inventory;

	@SchemaProperty(name = "")
	private Date startDate;

	@SchemaProperty(name = "")
	private Date endDate;

}
