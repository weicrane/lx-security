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
import io.lx.modules.wxapp.dto.TbPartnersApplyDTO;
import io.lx.modules.wxapp.excel.TbPartnersApplyExcel;
import io.lx.modules.wxapp.service.TbPartnersApplyService;
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
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-31
 */
@RestController
@RequestMapping("wxapp/tbpartnersapply")
@Tag(name="用户预约合作商家")
public class TbPartnersApplyController {
    @Autowired
    private TbPartnersApplyService tbPartnersApplyService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @Parameters({
        @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
        @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
        @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
        @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    @RequiresPermissions("wxapp:tbpartnersapply:page")
    public Result<PageData<TbPartnersApplyDTO>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params){
        PageData<TbPartnersApplyDTO> page = tbPartnersApplyService.page(params);

        return new Result<PageData<TbPartnersApplyDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    public Result<TbPartnersApplyDTO> get(@PathVariable("id") Long id){
        TbPartnersApplyDTO data = tbPartnersApplyService.get(id);

        return new Result<TbPartnersApplyDTO>().ok(data);
    }

    @PostMapping
    @Operation(summary = "保存")
    @LogOperation("保存")
    public Result save(@RequestBody TbPartnersApplyDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        tbPartnersApplyService.save(dto);

        return new Result();
    }

    @PutMapping
    @Operation(summary = "修改")
    @LogOperation("修改")
    public Result update(@RequestBody TbPartnersApplyDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        tbPartnersApplyService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @LogOperation("删除")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        tbPartnersApplyService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @Operation(summary = "导出")
    @LogOperation("导出")
    public void export(@Parameter(hidden = true) @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<TbPartnersApplyDTO> list = tbPartnersApplyService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "", list, TbPartnersApplyExcel.class);
    }

}
