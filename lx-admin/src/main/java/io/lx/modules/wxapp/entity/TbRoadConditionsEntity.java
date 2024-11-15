package io.lx.modules.wxapp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-20
 */
@Data
@TableName("tb_road_conditions")
public class TbRoadConditionsEntity {

    /**
     * 
     */
	private Integer id;
    /**
     * 
     */
	private String title;
    /**
     * 
     */
	private String content;
    /**
     * 
     */
	private String tag;
    /**
     * 
     */
	private String imgPath;
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
	private String status;
    /**
     * 
     */
	private Long userId;
    /**
     * 
     */
	private String userType;
}