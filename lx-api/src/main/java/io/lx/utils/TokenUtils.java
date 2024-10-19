package io.lx.utils;

import cn.hutool.core.util.StrUtil;
import io.lx.common.constant.Constant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
* Token相关
 */
@Component
public class TokenUtils {

    /**
     * 获取请求的token
     */
    public String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader(Constant.TOKEN_HEADER);

        //如果header中不存在token，则从参数中获取token
        if (StrUtil.isBlank(token)) {
            token = httpRequest.getParameter(Constant.TOKEN_HEADER);
        }

        return token;
    }

}
