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
 * @since 1.0.0 2024-10-31
 */
@Data
@TableName("tb_partners_apply")
public class TbPartnersApplyEntity {

    /**
     * 
     */
    @TableId(type = IdType.AUTO) // 使用数据库的自增策略
	private String applyId;
    /**
     * 
     */
	private Long userId;
    /**
     * 
     */
	private Integer partnersId;
    /**
     * 
     */
	private String title;
    /**
     * 
     */
	private String name;
    /**
     * 
     */
	private String phone;
    /**
     * 
     */
	private Integer adults;
    /**
     * 
     */
	private Integer children;
    /**
     * 
     */
	private String remark;
    /**
     * 
     */
	private String status;
    /**
     * 
     */
	private Date createdAt;
    /**
     * 
     */
	private Date updatedAt;
}