/**
 * 首页相关接口
 */

package io.lx.controller;


import io.lx.annotation.Login;
import io.lx.annotation.LoginUser;
import io.lx.common.constant.Constant;
import io.lx.common.exception.RenException;
import io.lx.common.page.PageData;
import io.lx.common.utils.Result;
import io.lx.common.validator.ValidatorUtils;
import io.lx.dto.OrdersDTO;
import io.lx.entity.UserEntity;
import io.lx.service.OrdersService;
import io.lx.service.WxPayService;
import io.lx.utils.TokenUtils;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

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
    private WxPayService wxPayService;
    @Resource
    private OrdersService ordersService;

    @PostMapping("creatOrder")
    @Operation(summary = "创建订单")
    @Login
    public Result creatOrder(@RequestBody OrdersDTO ordersDTO, HttpServletRequest request) throws Exception {
        //表单校验
        ValidatorUtils.validateEntity(ordersDTO);
        // 从请求中获取 Token
        String token = tokenUtils.getRequestToken(request);
        // 创建订单
        return new Result().ok(wxPayService.jsApiOrder(ordersDTO,token));
    }

    @PostMapping("creatSelfDrivingOrder")
    @Operation(summary = "创建自驾订单")
    @Login
    public Result creatSelfDrivingOrder(@RequestBody OrdersDTO ordersDTO, HttpServletRequest request) throws Exception {
        //表单校验
        ValidatorUtils.validateEntity(ordersDTO);
        // 从请求中获取 Token
        String token = tokenUtils.getRequestToken(request);
        // 创建订单
        return new Result().ok(wxPayService.createSelfDrivingOrder(ordersDTO,token));
    }

    @PostMapping("creatOthersOrder")
    @Operation(summary = "创建会员福利、自驾订单")
    @Login
    public Result creatOthersOrder(@RequestBody OrdersDTO ordersDTO, HttpServletRequest request) throws Exception {
        //表单校验
        ValidatorUtils.validateEntity(ordersDTO);
        // 从请求中获取 Token
        String token = tokenUtils.getRequestToken(request);
        // 创建订单
        return new Result().ok(wxPayService.createOthersOrder(ordersDTO,token));
    }

    @PostMapping("closeOrder")
    @Operation(summary = "关闭订单")
    @Login
    public Result closeOrder(@RequestBody OrdersDTO ordersDTO){
        if (StringUtils.isBlank(ordersDTO.getOrderId())){
            throw new RenException("订单号码为空");
        }
        wxPayService.closePay(ordersDTO.getOrderId());
        return new Result();
    }


    @PostMapping("callback")
    @Operation(summary = "微信异步回调")
    public ResponseEntity callback(HttpServletRequest request){
        try {
            wxPayService.callback(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @PostMapping("updateOrderStatus")
    @Operation(summary = "更新订单状态")
    @Login
    public Result updateOrderStatus(@Parameter String orderId,@Parameter String status){
        ordersService.updateOrderStatus(orderId,status);
        return new Result();
    }

    @GetMapping("getOrderListByPage")
    @Operation(summary = "查询订单列表")
    @Login
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    public Result<PageData<OrdersDTO>> getOrderListByPage(@Parameter(hidden = false) @RequestParam Map<String, Object> params,
                                                          @Parameter String keyword,
                                                          @Parameter String status,
                                                          @LoginUser UserEntity user){
        PageData<OrdersDTO> page =  ordersService.getOrderListByPage(params,keyword,status,user);
        return new Result<PageData<OrdersDTO>>().ok(page);
    }
}
