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
import io.lx.modules.wxapp.dto.TbPartnersDTO;
import io.lx.modules.wxapp.excel.TbPartnersExcel;
import io.lx.modules.wxapp.service.TbPartnersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("wxapp/tbpartners")
@Tag(name="会员福利（合作商家）")
public class TbPartnersController {
    @Autowired
    private TbPartnersService tbPartnersService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @Parameters({
        @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
        @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
        @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
        @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    public Result<PageData<TbPartnersDTO>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params){
        PageData<TbPartnersDTO> page = tbPartnersService.page(params);

        return new Result<PageData<TbPartnersDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    public Result<TbPartnersDTO> get(@PathVariable("id") Long id){
        TbPartnersDTO data = tbPartnersService.get(id);

        return new Result<TbPartnersDTO>().ok(data);
    }

    @PostMapping
    @Operation(summary = "保存")
    @LogOperation("保存")
    public Result save(@RequestBody TbPartnersDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        tbPartnersService.save(dto);

        return new Result();
    }

    @PutMapping
    @Operation(summary = "修改")
    @LogOperation("修改")
    public Result update(@RequestBody TbPartnersDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        tbPartnersService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @LogOperation("删除")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        tbPartnersService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @Operation(summary = "导出")
    @LogOperation("导出")
    public void export(@Parameter(hidden = true) @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<TbPartnersDTO> list = tbPartnersService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "", list, TbPartnersExcel.class);
    }

}
