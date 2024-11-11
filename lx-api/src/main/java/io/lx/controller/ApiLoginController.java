
package io.lx.controller;


import io.lx.annotation.Login;
import io.lx.common.exception.RenException;
import io.lx.common.utils.Result;
import io.lx.common.validator.ValidatorUtils;
import io.lx.dto.CodeLoginDTO;
import io.lx.dto.LoginDTO;
import io.lx.dto.UserProfileDTO;
import io.lx.service.TokenService;
import io.lx.service.UserService;
import io.lx.service.WechatService;
import io.lx.utils.TokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 登录接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/api")
@Tag(name = "登录接口")
@AllArgsConstructor
public class ApiLoginController {
    private final UserService userService;
    private final TokenService tokenService;
    @Resource
    private WechatService wechatService;
    private final TokenUtils tokenUtils;

    @PostMapping("login")
    @Operation(summary = "登录")
    public Result<Map<String, Object>> login(@RequestBody LoginDTO dto) {
        //表单校验
        ValidatorUtils.validateEntity(dto);

        //用户登录
        Map<String, Object> map = userService.login(dto);

        return new Result().ok(map);
    }

    @Login
    @PostMapping("logout")
    @Operation(summary = "退出")
    public Result logout(@Parameter(hidden = true) @RequestAttribute("userId") Long userId) {
        tokenService.expireToken(userId);
        return new Result();
    }

    @PostMapping("codeLogin")
    @Operation(summary = "微信快捷登录")
    public Result<Map<String, Object>> codeLogin(@RequestBody CodeLoginDTO dto) {
        //表单校验
        ValidatorUtils.validateEntity(dto);

        //用户登录
        Map<String, Object> map = wechatService.codeLogin(dto);

        return new Result().ok(map);
    }

    @PostMapping("getOpenId")
    @Operation(summary = "获取openid")
    public Result<Map<String, Object>> getOpenid (@RequestBody CodeLoginDTO dto){
        Map<String, Object> map =wechatService.getOpenId(dto.getCode());
        return new Result().ok(map);
    }


    @PostMapping("updateUserProfile")
    @Operation(summary = "更新用户信息")
    public Result updateUserProfile(@RequestBody UserProfileDTO dto) throws RenException  {
        //表单校验
        ValidatorUtils.validateEntity(dto);

        //更新信息
        wechatService.updateUserProfile(dto);

        return new Result();
    }

    @PostMapping("uploadHeadIcon")
    @Operation(summary = "上传用户头像")
    public Result uploadHeadIcon(@RequestBody MultipartFile file, HttpServletRequest request) throws RenException {
        // 从请求中获取 Token
        String token = tokenUtils.getRequestToken(request);
        return new Result().ok(userService.uploadHeadIcon(file,token));
    }

    @GetMapping("getUserInfoDetail")
    @Operation(summary = "通过token获取用户信息详情")
    @Login
    public Result getUserInfoDetail(HttpServletRequest request) throws RenException{
        // 从请求中获取 Token
        String token = tokenUtils.getRequestToken(request);
        return new Result().ok(userService.getUserInfoDetailByToken(token));
    }



}
