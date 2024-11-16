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
 * @since 1.0.0 2024-10-19
 */
@Data
@TableName("tb_user_memberships")
public class UserMembershipsEntity {

    /**
     * 
     */
    @TableId(type = IdType.AUTO) // 使用数据库的自增策略
	private Long id;
    /**
     * 
     */
	private Long userId;
    /**
     * 
     */
	private String memberType;
    /**
     * 
     */
	private Integer travelGuidesId;
    /**
     * 
     */
	private Integer selfDrivingsId;
    /**
     * 
     */
	private Integer routesGuidesId;
    /**
     * 
     */
	private Date startDate;
    /**
     * 
     */
	private Date endDate;
    /**
     * 
     */
	private Date createdAt;
}