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
import io.lx.modules.wxapp.dto.GenCardsDTO;
import io.lx.modules.wxapp.dto.TbCardDTO;
import io.lx.modules.wxapp.excel.TbCardExcel;
import io.lx.modules.wxapp.service.TbCardService;
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
 * 卡密表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2025-03-16
 */
@RestController
@RequestMapping("wxapp/tbcard")
@Tag(name="卡密表")
public class TbCardController {
    @Autowired
    private TbCardService tbCardService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @Parameters({
        @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
        @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
        @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
        @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    @RequiresPermissions("wxapp:tbcard:page")
    public Result<PageData<TbCardDTO>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params){
        PageData<TbCardDTO> page = tbCardService.page(params);

        return new Result<PageData<TbCardDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @RequiresPermissions("wxapp:tbcard:info")
    public Result<TbCardDTO> get(@PathVariable("id") Long id){
        TbCardDTO data = tbCardService.get(id);

        return new Result<TbCardDTO>().ok(data);
    }

    @PostMapping
    @Operation(summary = "保存")
    @LogOperation("保存")
    @RequiresPermissions("wxapp:tbcard:save")
    public Result save(@RequestBody TbCardDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        tbCardService.save(dto);

        return new Result();
    }

    @PutMapping
    @Operation(summary = "修改")
    @LogOperation("修改")
    @RequiresPermissions("wxapp:tbcard:update")
    public Result update(@RequestBody TbCardDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        tbCardService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @LogOperation("删除")
    @RequiresPermissions("wxapp:tbcard:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        tbCardService.delete(ids);

        return new Result();
    }

    @PostMapping("/genCards")
    @Operation(summary = "生成卡密")
    @LogOperation("生成卡密")
    public Result genCards(@RequestBody GenCardsDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        tbCardService.genCards(dto);

        return new Result();
    }

    @PostMapping("/editCard")
    @Operation(summary = "修改卡密有效期")
    @LogOperation("修改卡密有效期")
    public Result editCards(@RequestBody TbCardDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        tbCardService.editCard(dto);

        return new Result();
    }

    @GetMapping("searchByPage")
    @Operation(summary = "分页查询")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String"),
            @Parameter(name = "cardCode", description = "卡密", in = ParameterIn.QUERY, ref="String"),
            @Parameter(name = "routesGuidesId", description = "线路ID", in = ParameterIn.QUERY, ref="String"),
            @Parameter(name = "status", description = "兑换状态", in = ParameterIn.QUERY, ref="Integer"),
            @Parameter(name = "productType", description = "卡密类型", in = ParameterIn.QUERY, ref="String")
    })
    @RequiresPermissions("wxapp:tbcard:page")
    public Result<PageData<TbCardDTO>> searchByPage(@Parameter(hidden = true) @RequestParam Map<String, Object> params){
        PageData<TbCardDTO> page = tbCardService.searchByPage(params);

        return new Result<PageData<TbCardDTO>>().ok(page);
    }

    @GetMapping("export")
    @Operation(summary = "导出")
    @LogOperation("导出")
    @RequiresPermissions("wxapp:tbcard:export")
    public void export(@Parameter(hidden = true) @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<TbCardDTO> list = tbCardService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "卡密表", list, TbCardExcel.class);
    }

}
