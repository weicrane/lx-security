package io.lx.modules.wxapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("kml_record")
public class KmlRecordEntity {

    /**
     * 
     */
    @TableId(type = IdType.AUTO) // 使用数据库的自增策略
	private Integer id;
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
}