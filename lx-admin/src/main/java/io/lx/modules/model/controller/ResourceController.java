package io.lx.modules.model.controller;
import io.lx.common.constant.Constant;
import io.lx.common.page.PageData;
import io.lx.common.utils.Result;
import io.lx.modules.model.dto.ResRecordDto;
import io.lx.modules.model.service.ModelService;
import io.lx.modules.model.service.ResRecordService;
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
 * 模型管理
 *
 * @author wyh
 */
@RestController
@RequestMapping("/resource")
@Tag(name = "资源管理")
@AllArgsConstructor
public class ResourceController {
    private final ModelService modelService;
    private final ResRecordService resRecordService;

    @GetMapping("page")
    @Operation(summary = "分页获取资源列表")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref = "int"),
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY, required = true, ref = "int"),
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref = "String"),
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref = "String"),
            @Parameter(name = "status", description = "状态  0：失败    1：成功", in = ParameterIn.QUERY, ref = "int")
    })
    public Result<PageData<ResRecordDto>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params){
        PageData<ResRecordDto> page = resRecordService.page(params);
        return new Result<PageData<ResRecordDto>>().ok(page);
    }
}
