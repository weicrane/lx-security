package io.lx.modules.wxapp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-27
 */
@Data
@TableName("tb_svip")
public class TbSvipEntity {

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
}