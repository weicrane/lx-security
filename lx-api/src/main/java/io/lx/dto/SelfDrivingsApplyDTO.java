package io.lx.dto;

import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 1.0.0 2024-10-29
 */
@Data
@Schema(name = "")
public class SelfDrivingsApplyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@TableId
	@SchemaProperty(name = "")
	private String applyId;

	@SchemaProperty(name = "")
	private Long userId;

	@SchemaProperty(name = "自驾活动ID不可为空")
	@NotNull
	private Integer selfDrivingId;

	@SchemaProperty(name = "姓名不能为空")
	@NotBlank
	private String name;

	@SchemaProperty(name = "手机号不能为空")
	@NotBlank
	private String phone;

	@SchemaProperty(name = "")
	private String remark;

	@SchemaProperty(name = "")
	private String status;

	@SchemaProperty(name = "")
	private Date createdAt;

	@SchemaProperty(name = "")
	private Date updatedAt;

	/**
	 *
	 */
	@SchemaProperty(name = "")
	private String vehicleType;
	/**
	 *
	 */
	@SchemaProperty(name = "")
	private String plate;

	@SchemaProperty(name = "")
	private String title;

	@SchemaProperty(name = "")
	private String field1;

	@SchemaProperty(name = "")
	private Integer num1;

	@SchemaProperty(name = "")
	private String field2;

	@SchemaProperty(name = "")
	private Integer num2;

	@SchemaProperty(name = "")
	private String field3;

	@SchemaProperty(name = "")
	private Integer num3;

	@SchemaProperty(name = "支付状态")
	private String payStatus;

	@SchemaProperty(name = "订单号")
	private String orderId;

}
