package io.lx.modules.wxapp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-10
 */
@Data
@TableName("tb_poi_images")
public class TbPoiImagesEntity {

    /**
     * 
     */
	private Long id;
    /**
     * 
     */
	private Long poiId;
    /**
     * 
     */
	private String imageUrl;
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
	private String imgName;
    /**
     * 
     */
	private String imgPath;
    /**
     * 
     */
	private Date createdAt;
}