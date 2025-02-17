package io.lx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-29
 */
@Data
@TableName("tb_self_drivings_apply")
public class SelfDrivingsApplyEntity {

    /**
     * 
     */
    @TableId(type = IdType.AUTO) // 使用数据库的自增策略
	private String applyId;
    /**
     * 
     */
	private Long userId;
    /**
     * 
     */
	private Integer selfDrivingId;
    /**
     * 
     */
	private String name;
    /**
     * 
     */
	private String phone;
    /**
     * 
     */
	private String remark;
    /**
     * 
     */
	private String status;
    /**
     * 
     */
	private Date createdAt;
    /**
     * 
     */
	private Date updatedAt;
    /**
     *
     */
    private String vehicleType;
    /**
     *
     */
    private String plate;
    /**
     *
     */
    private String title;

    private String field1;

    private Integer num1;

    private String field2;

    private Integer num2;

    private String field3;

    private Integer num3;

    private String payStatus;

    private String orderId;
}