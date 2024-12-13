/**
 * 首页相关接口
 */

package io.lx.controller;


import io.lx.annotation.Login;
import io.lx.annotation.LoginUser;
import io.lx.common.constant.Constant;
import io.lx.common.page.PageData;
import io.lx.common.utils.Result;
import io.lx.common.validator.ValidatorUtils;
import io.lx.dto.GetApplyDTO;
import io.lx.dto.PartnersApplyDTO;
import io.lx.dto.PartnersDTO;
import io.lx.entity.UserEntity;
import io.lx.service.PartnersApplyService;
import io.lx.service.PartnersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 合作商家
 *
 * @author wyh
 */
@RestController
@RequestMapping("/partners")
@Tag(name = "商家福利")
@AllArgsConstructor
public class ApiPartnersController {

    @Resource
    private PartnersService partnersService;
    @Resource
    private PartnersApplyService partnersApplyService;

    @GetMapping("getPartnersListByPage")
    @Operation(summary = "查询商家列表分页")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    public Result<PageData<PartnersDTO>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params, @Parameter String keyword){
        PageData<PartnersDTO> page = partnersService.getPartnersListByPage(params,keyword);
        return new Result<PageData<PartnersDTO>>().ok(page);
    }

    @PostMapping("submitApply")
    @Operation(summary = "预约商家服务")
    @Login
    public Result submitApply(@RequestBody PartnersApplyDTO dto, @LoginUser UserEntity user) {
        // 表单校验
        ValidatorUtils.validateEntity(dto);

        // 提交报名
        Map<String,String> orderId = partnersApplyService.submitApply(dto,user);

        return new Result().ok(orderId);
    }

    @GetMapping("getPartnersApplyByPage")
    @Operation(summary = "查询我的商家预约列表-分页")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    @Login
    public Result<PageData<PartnersApplyDTO>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params, @Parameter String keyword, @LoginUser UserEntity user){
        PageData<PartnersApplyDTO> page = partnersApplyService.getPartnersApplyByPage(params,keyword,user);
        return new Result<PageData<PartnersApplyDTO>>().ok(page);
    }

    @PostMapping("cancelApply")
    @Operation(summary = "取消预约商家活动")
    @Login
    public Result cancelApply(@RequestBody GetApplyDTO dto, @LoginUser UserEntity user) {
        // 表单校验
        ValidatorUtils.validateEntity(dto);
        // 取消报名
        partnersApplyService.cancelApply(dto,user);
        return new Result();
    }

    @GetMapping("getApplyDetailById")
    @Operation(summary = "查询我的商家预约详情")
    @Login
    public Result<Map<String, Object>> getApplyDetailById(@Parameter String applyId,@LoginUser UserEntity user){
        Map<String, Object> map = partnersApplyService.getApplyDetailById(applyId, user);
        return new Result().ok(map);
    }


}
