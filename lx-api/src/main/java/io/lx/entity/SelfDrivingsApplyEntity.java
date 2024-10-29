package io.lx.entity;

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
	private Integer adults;
    /**
     * 
     */
	private Integer children;
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
}