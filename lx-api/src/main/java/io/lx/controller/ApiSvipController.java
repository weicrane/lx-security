/**
 * 首页相关接口
 */

package io.lx.controller;


import io.lx.common.utils.Result;
import io.lx.service.SvipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付
 *
 * @author wyh
 */
@RestController
@RequestMapping("/svip")
@Tag(name = "Svip会员")
@AllArgsConstructor
public class ApiSvipController {
    private SvipService svipService;

    @GetMapping("getSvipPrice")
    @Operation(summary = "查询会员价格")
    public Result<Map<String, Object>> getSvipPrice(){
        Map<String, Object> price =  new HashMap<>();
        price.put("price",svipService.getSvipPrice());
        return new Result().ok(price);
    }
}
