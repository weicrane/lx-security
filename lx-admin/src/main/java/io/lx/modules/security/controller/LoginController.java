/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.lx.modules.security.controller;

import io.lx.common.exception.ErrorCode;
import io.lx.common.exception.RenException;
import io.lx.common.utils.IpUtils;
import io.lx.common.utils.Result;
import io.lx.common.validator.AssertUtils;
import io.lx.common.validator.ValidatorUtils;
import io.lx.modules.log.entity.SysLogLoginEntity;
import io.lx.modules.log.enums.LoginOperationEnum;
import io.lx.modules.log.enums.LoginStatusEnum;
import io.lx.modules.log.service.SysLogLoginService;
import io.lx.modules.security.dto.LoginDTO;
import io.lx.modules.security.dto.RegistDTO;
import io.lx.modules.security.dto.ResetDTO;
import io.lx.modules.security.password.PasswordUtils;
import io.lx.modules.security.service.CaptchaService;
import io.lx.modules.security.service.RegistService;
import io.lx.modules.security.service.ResetService;
import io.lx.modules.security.service.SysUserTokenService;
import io.lx.modules.security.user.SecurityUser;
import io.lx.modules.security.user.UserDetail;
import io.lx.modules.sys.dto.SysUserDTO;
import io.lx.modules.sys.enums.UserStatusEnum;
import io.lx.modules.sys.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

/**
 * 登录
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@Tag(name = "登录管理")
@AllArgsConstructor
public class LoginController {
    private final SysUserService sysUserService;
    private final SysUserTokenService sysUserTokenService;
    private final CaptchaService captchaService;
    private final SysLogLoginService sysLogLoginService;
    private final ResetService resetService;
    private final RegistService registService;

    @GetMapping("captcha")
    @Operation(summary = "验证码")
    @Parameter(in = ParameterIn.QUERY, ref = "string", name = "uuid", required = true)
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        //uuid不能为空
        AssertUtils.isBlank(uuid, ErrorCode.IDENTIFIER_NOT_NULL);

        //生成验证码
        captchaService.create(response, uuid);
    }

    @PostMapping("login")
    @Operation(summary = "登录")
    public Result login(HttpServletRequest request, @RequestBody LoginDTO login) {
        //效验数据
        ValidatorUtils.validateEntity(login);

        //验证码是否正确
        boolean flag = captchaService.validate(login.getUuid(), login.getCaptcha());
        if (!flag) {
            return new Result().error(ErrorCode.CAPTCHA_ERROR);
        }

        //用户信息
        SysUserDTO user = sysUserService.getByUsername(login.getUsername());

        SysLogLoginEntity log = new SysLogLoginEntity();
        log.setOperation(LoginOperationEnum.LOGIN.value());
        log.setCreateDate(new Date());
        log.setIp(IpUtils.getIpAddr(request));
        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));

        //用户不存在
        if (user == null) {
            log.setStatus(LoginStatusEnum.FAIL.value());
            log.setCreatorName(login.getUsername());
            sysLogLoginService.save(log);

            throw new RenException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        //密码错误
        if (!PasswordUtils.matches(login.getPassword(), user.getPassword())) {
            log.setStatus(LoginStatusEnum.FAIL.value());
            log.setCreator(user.getId());
            log.setCreatorName(user.getUsername());
            sysLogLoginService.save(log);

            throw new RenException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        //账号停用
        if (user.getStatus() == UserStatusEnum.DISABLE.value()) {
            log.setStatus(LoginStatusEnum.LOCK.value());
            log.setCreator(user.getId());
            log.setCreatorName(user.getUsername());
            sysLogLoginService.save(log);

            throw new RenException(ErrorCode.ACCOUNT_DISABLE);
        }

        //登录成功
        log.setStatus(LoginStatusEnum.SUCCESS.value());
        log.setCreator(user.getId());
        log.setCreatorName(user.getUsername());
        sysLogLoginService.save(log);

        return sysUserTokenService.createToken(user.getId());
    }

    @PostMapping("logout")
    @Operation(summary = "退出")
    public Result logout(HttpServletRequest request) {
        UserDetail user = SecurityUser.getUser();

        //退出
        sysUserTokenService.logout(user.getId());

        //用户信息
        SysLogLoginEntity log = new SysLogLoginEntity();
        log.setOperation(LoginOperationEnum.LOGOUT.value());
        log.setIp(IpUtils.getIpAddr(request));
        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        log.setIp(IpUtils.getIpAddr(request));
        log.setStatus(LoginStatusEnum.SUCCESS.value());
        log.setCreator(user.getId());
        log.setCreatorName(user.getUsername());
        log.setCreateDate(new Date());
        sysLogLoginService.save(log);

        return new Result();
    }

    @PostMapping("reset")
    @Operation(summary = "重置密码")
    public Result reset(HttpServletRequest request, @RequestBody ResetDTO reset) {
        //效验数据
        ValidatorUtils.validateEntity(reset);

        //用户信息
        SysUserDTO user = sysUserService.getByUsername(reset.getUsername());

        //用户不存在
        if (user == null) {
            throw new RenException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        //密码错误
        if (!PasswordUtils.matches(reset.getPassword(), user.getPassword())) {
            throw new RenException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        //账号停用
        if (user.getStatus() == UserStatusEnum.DISABLE.value()) {
            throw new RenException(ErrorCode.ACCOUNT_DISABLE);
        }

        //修改密码
        try {
            resetService.reset(reset.getUsername(), reset.getNewpassword());
            return new Result();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @PostMapping("regist")
    @Operation(summary = "注册")
    public Result regist(HttpServletRequest request, @RequestBody RegistDTO regist) {
        //效验数据
        ValidatorUtils.validateEntity(regist);

        //用户信息
        SysUserDTO user = sysUserService.getByUsername(regist.getUsername());

        //用户已存在
        if (user != null) {
            throw new RenException("用户名已存在");
        }

        //新增用户
        try {
            registService.regist(regist.getUsername(), regist.getPassword(),regist.getMobile(),regist.getRealname());
            return new Result();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
