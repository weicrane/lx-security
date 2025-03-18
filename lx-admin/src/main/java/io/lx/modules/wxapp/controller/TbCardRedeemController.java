package io.lx.modules.wxapp.controller;

import io.lx.common.annotation.LogOperation;
import io.lx.common.constant.Constant;
import io.lx.common.page.PageData;
import io.lx.common.utils.ExcelUtils;
import io.lx.common.utils.Result;
import io.lx.common.validator.AssertUtils;
import io.lx.common.validator.ValidatorUtils;
import io.lx.common.validator.group.AddGroup;
import io.lx.common.validator.group.DefaultGroup;
import io.lx.common.validator.group.UpdateGroup;
import io.lx.modules.wxapp.dto.TbCardRedeemDTO;
import io.lx.modules.wxapp.excel.TbCardRedeemExcel;
import io.lx.modules.wxapp.service.TbCardRedeemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 卡密兑换记录表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2025-03-16
 */
@RestController
@RequestMapping("wxapp/tbcardredeem")
@Tag(name="卡密兑换记录表")
public class TbCardRedeemController {
    @Autowired
    private TbCardRedeemService tbCardRedeemService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @Parameters({
        @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
        @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
        @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
        @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    @RequiresPermissions("wxapp:tbcardredeem:page")
    public Result<PageData<TbCardRedeemDTO>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params){
        PageData<TbCardRedeemDTO> page = tbCardRedeemService.page(params);

        return new Result<PageData<TbCardRedeemDTO>>().ok(page);
    }

    @GetMapping("searchByPage")
    @Operation(summary = "分页查询")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String"),
            @Parameter(name = "routesGuidesId", description = "线路ID", in = ParameterIn.QUERY, ref="String"),
            @Parameter(name = "productType", description = "卡密类型", in = ParameterIn.QUERY, ref="String"),
            @Parameter(name = "userId", description = "用户ID", in = ParameterIn.QUERY, ref="Long")
    })
    @RequiresPermissions("wxapp:tbcardredeem:page")
    public Result<PageData<TbCardRedeemDTO>> searchByPage(@Parameter(hidden = true) @RequestParam Map<String, Object> params){
        PageData<TbCardRedeemDTO> page = tbCardRedeemService.searchByPage(params);

        return new Result<PageData<TbCardRedeemDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @RequiresPermissions("wxapp:tbcardredeem:info")
    public Result<TbCardRedeemDTO> get(@PathVariable("id") Long id){
        TbCardRedeemDTO data = tbCardRedeemService.get(id);

        return new Result<TbCardRedeemDTO>().ok(data);
    }

    @PostMapping
    @Operation(summary = "保存")
    @LogOperation("保存")
    @RequiresPermissions("wxapp:tbcardredeem:save")
    public Result save(@RequestBody TbCardRedeemDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        tbCardRedeemService.save(dto);

        return new Result();
    }

    @PutMapping
    @Operation(summary = "修改")
    @LogOperation("修改")
    @RequiresPermissions("wxapp:tbcardredeem:update")
    public Result update(@RequestBody TbCardRedeemDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        tbCardRedeemService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @LogOperation("删除")
    @RequiresPermissions("wxapp:tbcardredeem:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        tbCardRedeemService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @Operation(summary = "导出")
    @LogOperation("导出")
    @RequiresPermissions("wxapp:tbcardredeem:export")
    public void export(@Parameter(hidden = true) @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<TbCardRedeemDTO> list = tbCardRedeemService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "卡密兑换记录表", list, TbCardRedeemExcel.class);
    }

}
