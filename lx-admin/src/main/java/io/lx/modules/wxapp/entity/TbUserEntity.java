package io.lx.modules.wxapp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户信息表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Data
@TableName("tb_user")
public class TbUserEntity {

    /**
     * 
     */
	private Long id;
    /**
     * 
     */
	private String username;
    /**
     * 
     */
	private String password;
    /**
     * 
     */
	private String email;
    /**
     * 
     */
	private String mobile;
    /**
     * 
     */
	private String gender;
    /**
     * 
     */
	private String avatarUrl;
    /**
     * 
     */
	private Date createdAt;
    /**
     * 
     */
	private Date updatedAt;
}