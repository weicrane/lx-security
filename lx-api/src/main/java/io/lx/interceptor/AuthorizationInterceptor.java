/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.lx.interceptor;

import cn.hutool.core.util.StrUtil;
import io.lx.annotation.Login;
import io.lx.common.exception.ErrorCode;
import io.lx.common.exception.RenException;
import io.lx.entity.TokenEntity;
import io.lx.service.TokenService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;

/**
 * 权限(Token)验证
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Resource
    private TokenService tokenService;
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    public static final String USER_KEY = "userId";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("触发AuthorizationInterceptor: Checking Token");

        // 打印请求的 headers
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            logger.info("=== Incoming Request Headers ===");
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                logger.info("{}: {}", headerName, headerValue);
            }
        }

        Login annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        } else {
            return true;
        }

        if (annotation == null) {
            return true;
        }

        //从header中获取token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }
        //token为空
        if (StrUtil.isBlank(token)) {
            throw new RenException(ErrorCode.TOKEN_NOT_EMPTY);
        }

        //查询token信息
        TokenEntity tokenEntity = tokenService.getByToken(token);
        if (tokenEntity == null || tokenEntity.getExpireDate().getTime() < System.currentTimeMillis()) {
            throw new RenException(ErrorCode.TOKEN_INVALID);
        }

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(USER_KEY, tokenEntity.getUserId());

        return true;
    }
}
