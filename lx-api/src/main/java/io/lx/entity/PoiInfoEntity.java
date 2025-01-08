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
 * @since 1.0.0 2024-11-02
 */
@Data
@TableName("tb_poi_info")
public class PoiInfoEntity {

    /**
     * 
     */
    @TableId(type = IdType.AUTO) // 使用数据库的自增策略
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
	private BigDecimal latitude;
    /**
     * 
     */
	private BigDecimal longitude;
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
    /**
     *日程id
     */
    private String dateId;
    /**
     *类型
     */
    private String journeyType;
}