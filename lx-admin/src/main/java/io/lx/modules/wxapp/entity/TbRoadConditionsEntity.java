package io.lx.modules.wxapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
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
    @TableId(type = IdType.AUTO) // 使用数据库的自增策略
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
    /**
     *
     */
    private String type;
    /**
     *
     */
    private BigDecimal latitude;
    /**
     *
     */
    private BigDecimal longitude;
    /**
     *
     */
    private String address;
}