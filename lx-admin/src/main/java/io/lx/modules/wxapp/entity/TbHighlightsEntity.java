package io.lx.modules.wxapp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-09
 */
@Data
@TableName("tb_highlights")
public class TbHighlightsEntity {

    /**
     * 
     */
	private Integer id;
    /**
     * 
     */
	private Integer journeyId;
    /**
     * 
     */
	private Integer lightSeq;
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
	private String filePath;
    /**
     * 
     */
	private Date createdAt;
    /**
     * 
     */
	private Date updatedAt;
}