package io.lx.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-03
 */
@Data
@TableName("tb_road_conditions")
public class RoadConditionsEntity {

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
}