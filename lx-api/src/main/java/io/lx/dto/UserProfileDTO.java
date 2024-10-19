/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.lx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/**
 * 用户信息表单
 *
 */
@Data
@Schema(title = "用户信息表单")
public class UserProfileDTO {
    @Schema(title = "昵称")
    @NotBlank(message="昵称不能为空")
    private String nickname;

    @Schema(title = "手机号")
    @NotBlank(message="手机号码不能为空")
    private String mobile;

}
