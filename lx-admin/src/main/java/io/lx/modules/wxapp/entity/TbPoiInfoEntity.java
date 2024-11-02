package io.lx.modules.wxapp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Data
@TableName("tb_poi_info")
public class TbPoiInfoEntity {

    /**
     * 
     */
	private Long id;
    /**
     * 
     */
	private Integer guidesId;
    /**
     * 
     */
	private String title;
    /**
     * 
     */
	private String description;
    /**
     * 
     */
	private String poiType;
    /**
     * 
     */
	private BigDecimal price;
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
	private Long imageId;
    /**
     * 
     */
	private String filePath;
    /**
     * 
     */
	private Integer status;
    /**
     * 
     */
	private Date createdAt;
    /**
     * 
     */
	private Date updatedAt;
}