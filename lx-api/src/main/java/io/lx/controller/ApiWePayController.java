/**
 * 首页相关接口
 */

package io.lx.controller;


import io.lx.common.utils.Result;
import io.lx.common.validator.ValidatorUtils;
import io.lx.dto.OrdersDTO;
import io.lx.service.IWxPayService;
import io.lx.utils.TokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 微信支付
 *
 * @author wyh
 */
@RestController
@RequestMapping("/wepay")
@Tag(name = "微信支付")
@AllArgsConstructor
public class ApiWePayController {

    @Resource
    private TokenUtils tokenUtils;
    @Resource
    private IWxPayService iWxPayService;

    @PostMapping("creatOrder")
    @Operation(summary = "创建订单")
    public Result creatOrder(@RequestBody OrdersDTO ordersDTO, HttpServletRequest request) throws Exception {
        //表单校验
        ValidatorUtils.validateEntity(ordersDTO);
        // 从请求中获取 Token
        String token = tokenUtils.getRequestToken(request);
        // 创建订单
        return new Result().ok(iWxPayService.jsApiOrder(ordersDTO,token));
    }


    @PostMapping("callback")
    @Operation(summary = "微信异步回调")
    public ResponseEntity callback(HttpServletRequest request){
        try {
            iWxPayService.callback(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
