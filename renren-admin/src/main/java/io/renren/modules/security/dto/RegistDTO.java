/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 注册用户表单
 *
 * @author wyh
 */
@Data
@Schema(title = "注册表单")
public class RegistDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "用户名", required = true)
    @NotBlank(message="{sysuser.username.require}")
    private String username;

    @Schema(title = "密码")
    @NotBlank(message="{sysuser.password.require}")
    private String password;

    @Schema(title = "手机号码")
    @NotBlank(message="{sysuser.password.require}")
    private String mobile;

    @Schema(title = "真实姓名")
    @NotBlank(message="{sysuser.password.require}")
    private String realname;
}
