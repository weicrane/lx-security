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
import io.lx.dto.SelfDrivingsApplyDTO;
import io.lx.dto.SelfDrivingsDTO;
import io.lx.entity.UserEntity;
import io.lx.service.SelfDrivingsApplyService;
import io.lx.service.SelfDrivingsService;
import io.lx.utils.OrderNumberUtils;
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
 * 自驾活动
 *
 * @author wyh
 */
@RestController
@RequestMapping("/selfdriving")
@Tag(name = "自驾活动")
@AllArgsConstructor
public class ApiSelfDrivingController {

    @Resource
    private SelfDrivingsService selfDrivingsService;
    @Resource
    private SelfDrivingsApplyService selfDrivingsApplyService;

    @GetMapping("getSelfDrivingListByPage")
    @Operation(summary = "查询自驾活动列表分页")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    public Result<PageData<SelfDrivingsDTO>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params,@Parameter String keyword){
        PageData<SelfDrivingsDTO> page = selfDrivingsService.getSelfDrivingListByPage(params,keyword);
        return new Result<PageData<SelfDrivingsDTO>>().ok(page);
    }

    @PostMapping("submitApply")
    @Operation(summary = "报名自驾活动")
    @Login
    public Result submitApply(@RequestBody SelfDrivingsApplyDTO dto, @LoginUser UserEntity user) {
        // 表单校验
        ValidatorUtils.validateEntity(dto);


        // 提交报名
        selfDrivingsApplyService.submitApply(dto,user);

        return new Result();
    }


}
