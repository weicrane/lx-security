/**
 * 首页相关接口
 */

package io.lx.controller;


import io.lx.common.constant.Constant;
import io.lx.common.page.PageData;
import io.lx.common.utils.Result;
import io.lx.dto.RecommendsDTO;
import io.lx.service.RecommendsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 猜你喜欢
 *
 * @author wyh
 */
@RestController
@RequestMapping("/recommends")
@Tag(name = "猜你喜欢")
@AllArgsConstructor
public class ApiRecommendsController {
    private RecommendsService recommendsService;

    @GetMapping("getRecommendsListByPage")
    @Operation(summary = "获取猜你喜欢列表")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    public Result<PageData<RecommendsDTO>> getRecommendsListByPage(@Parameter(hidden = false) @RequestParam Map<String, Object> params){
        PageData<RecommendsDTO> page = recommendsService.getRecommendsListByPage(params);
        return new Result<PageData<RecommendsDTO>>().ok(page);
    }
}
