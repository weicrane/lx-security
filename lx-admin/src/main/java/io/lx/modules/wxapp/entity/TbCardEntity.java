package io.lx.modules.wxapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 卡密表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2025-03-16
 */
@Data
@TableName("tb_card")
public class TbCardEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO) // 使用数据库的自增策略
	private Integer id;
    /**
     * 16位卡密
     */
	private String cardCode;
    /**
     * 状态(0:未使用, 1:已使用)
     */
	private Integer status;
    /**
     * 有效期
     */
	private Date expireTime;
    /**
     * 类别，00-终身会员，01-网盘路书，02-自驾活动，03-四季玩法
     */
	private String productType;
    /**
     * 线路玩法ID
     */
	private Integer routesGuidesId;
    /**
     * 
     */
	private Date createdAt;
    /**
     * 
     */
	private Date updatedAt;
    /**
     * 备注
     */
	private String description;
    /**
     * 标题
     */
    private String tittle;
}