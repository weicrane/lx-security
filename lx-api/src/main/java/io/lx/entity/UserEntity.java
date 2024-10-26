/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.lx.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * 
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("tb_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId
	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 密码
	 */
	@JsonIgnore
	private String password;
	/**
	 * 创建时间
	 */
	private Date createdAt;
	/**
	 *
	 */
	private Date updatedAt;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 头像地址
	 */
	private String avatarUrl;
	/**
	 * 会员类型：0-非会员，1-终身全部会员，2-特定路线会员
	 */
	private String memberType;
	/**
	 * openid
	 */
	private String openid;

}