package io.lx.entity;

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
 * @since 1.0.0 2024-10-05
 */
@Data
@TableName("tb_travel_guides")
public class TravelGuidesEntity {

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
	private String subTitle;
    /**
     * 
     */
	private Integer travelType;
    /**
     * 
     */
	private String destinationCity;
    /**
     * 
     */
	private String departureCity;
    /**
     * 
     */
	private String tag;
    /**
     * 
     */
	private String purchaseNotes;
    /**
     * 
     */
	private String coverImgPath;
    /**
     * 
     */
	private String detailImgPath;
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
	private BigDecimal price;
    /**
     *
     */
    private Integer inventory;
    /**
     *
     */
    private String asset;
}