package io.lx.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Data
@TableName("tb_pics")
public class PicsEntity {

    /**
     * 
     */
	private Long id;
    /**
     * 
     */
	private Integer guideId;
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
	private String filePath;
    /**
     * 
     */
	private Date uploadTime;
    /**
     * 
     */
	private Integer status;
    /**
     * 
     */
	private String picType;
    /**
     * 
     */
	private Date createdAt;
    /**
     * 
     */
	private Date updatedAt;
}