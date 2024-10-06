package io.lx.modules.wxapp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户Token
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Data
@TableName("tb_token")
public class TbTokenEntity {

    /**
     * id
     */
	private Long id;
    /**
     * 用户ID
     */
	private Long userId;
    /**
     * token
     */
	private String token;
    /**
     * 过期时间
     */
	private Date expireDate;
    /**
     * 更新时间
     */
	private Date updateDate;
}