package io.lx.modules.wxapp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 首页推荐
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-12-18
 */
@Data
@TableName("tb_recommends")
public class TbRecommendsEntity {

    /**
     * 主键id
     */
    @TableField("id")
    private Integer id;
    /**
     * 类型字段
     */
    @TableField("type")
    private String type;
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
	private String coverImgPath;
    /**
     * 
     */
	private BigDecimal price;
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
	private Integer orders;
    private String onsale;
}