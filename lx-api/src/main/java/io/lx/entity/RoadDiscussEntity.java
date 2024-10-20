package io.lx.entity;

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
@TableName("tb_road_discuss")
public class RoadDiscussEntity {

    /**
     * 
     */
	private Integer id;
    /**
     * 
     */
	private String content;
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
	private Integer conditionId;
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
    private String nickname;
    /**
     *
     */
    private String avatarUrl;
}