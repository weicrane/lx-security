package io.lx.modules.wxapp.entity;

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
public class TbUserMembershipsEntity {

    /**
     * 
     */
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