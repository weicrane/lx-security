/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.lx.controller;

import cn.hutool.crypto.digest.DigestUtil;
import io.lx.common.utils.Result;
import io.lx.common.validator.ValidatorUtils;
import io.lx.dto.RegisterDTO;
import io.lx.entity.UserEntity;
import io.lx.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 注册接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/api")
@Tag(name = "注册接口")
@AllArgsConstructor
public class ApiRegisterController {
    private final UserService userService;

    @PostMapping("register")
    @Operation(summary = "注册")
    public Result register(@RequestBody RegisterDTO dto) {
        //表单校验
        ValidatorUtils.validateEntity(dto);

        UserEntity user = new UserEntity();
        user.setMobile(dto.getMobile());
        user.setUsername(dto.getMobile());
        user.setPassword(DigestUtil.sha256Hex(dto.getPassword()));
        user.setCreatedAt(new Date());
        userService.insert(user);

        return new Result();
    }
}
