package io.lx.modules.wxapp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Data
@TableName("tb_banners")
public class TbBannersEntity {

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
	private String oriName;
    /**
     * 
     */
	private String savedName;
    /**
     * 
     */
	private String path;
    /**
     * 
     */
	private Integer displayOrder;
    /**
     * 
     */
	private Date createdAt;
    /**
     * 
     */
	private Date updatedAt;
}