package io.lx.modules.wxapp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 路线信息表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Data
@TableName("tb_routes")
public class TbRoutesEntity {

    /**
     * 
     */
	private Integer id;
    /**
     * 
     */
	private Integer routeCode;
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
	private BigDecimal price;
    /**
     * 
     */
	private String duration;
    /**
     * 
     */
	private Date createdAt;
    /**
     * 
     */
	private Date updatedAt;
}