package io.lx.modules.wxapp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Data
@TableName("tb_poi_info")
public class PoiExcelEntity {

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
	private BigDecimal longitude;
    /**
     *
     */
    private BigDecimal latitude;
    /**
     *
     */
    private String dateId;
    /**
     *
     */
    private String journeyType;
    /**
     *
     */
    private String filePath;
}