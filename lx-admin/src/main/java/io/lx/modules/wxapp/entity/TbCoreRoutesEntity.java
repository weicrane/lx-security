package io.lx.modules.wxapp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-27
 */
@Data
@TableName("tb_core_routes")
public class TbCoreRoutesEntity {

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
	private String des;
    /**
     * 
     */
	private Integer routeCode;
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
    private Integer sort;
}