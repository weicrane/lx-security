/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.lx.modules.log.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
@Data
@Schema(title = "登录日志")
public class SysLogLoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@Schema(title = "id")
	private Long id;

	@Schema(title = "用户操作  0：用户登录   1：用户退出")
	private Integer operation;

	@Schema(title = "状态  0：失败    1：成功    2：账号已锁定")
	private Integer status;

	@Schema(title = "用户代理")
	private String userAgent;

	@Schema(title = "操作IP")
	private String ip;

	@Schema(title = "用户名")
	private String creatorName;

	@Schema(title = "创建时间")
	private Date createDate;

}
