package io.lx.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Data
@TableName("tb_routes")
public class RoutesEntity {

    /**
     * 
     */
	private Integer id;
    /**
     * 
     */
	private Integer guideId;
    /**
     * 
     */
	private String name;
    /**
     * 
     */
	private String description;
    /**
     * 
     */
	private String duration;
    /**
     * 
     */
	private Integer kmlId;
    /**
     * 
     */
	private String kmlPath;
    /**
     * 
     */
	private String travelType;
    /**
     * 
     */
	private Date createdAt;
    /**
     * 
     */
	private Date updatedAt;
}