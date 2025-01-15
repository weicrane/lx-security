package io.lx.modules.wxapp.controller;

import io.lx.common.annotation.LogOperation;
import io.lx.common.constant.Constant;
import io.lx.common.exception.RenException;
import io.lx.common.page.PageData;
import io.lx.common.utils.Result;
import io.lx.common.validator.AssertUtils;
import io.lx.common.validator.ValidatorUtils;
import io.lx.common.validator.group.AddGroup;
import io.lx.common.validator.group.DefaultGroup;
import io.lx.common.validator.group.UpdateGroup;
import io.lx.modules.wxapp.dto.TbPoiInfoDTO;
import io.lx.modules.wxapp.service.TbPoiInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@RestController
@RequestMapping("wxapp/tbpoiinfo")
@Tag(name="POI点管理")
public class TbPoiInfoController {
    @Autowired
    private TbPoiInfoService tbPoiInfoService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @Parameters({
        @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
        @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
        @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
        @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    @RequiresPermissions("wxapp:tbpoiinfo:page")
    public Result<PageData<TbPoiInfoDTO>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params){
        PageData<TbPoiInfoDTO> page = tbPoiInfoService.page(params);

        return new Result<PageData<TbPoiInfoDTO>>().ok(page);
    }

    @GetMapping("selectPage")
    @Operation(summary = "分页")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    public Result<PageData<TbPoiInfoDTO>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params,
                                               @Parameter Integer guidesId,
                                               @Parameter String dateId,
                                               @Parameter String journeyType){
        PageData<TbPoiInfoDTO> page = tbPoiInfoService.selectPage(params,guidesId,dateId,journeyType);

        return new Result<PageData<TbPoiInfoDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @RequiresPermissions("wxapp:tbpoiinfo:info")
    public Result<TbPoiInfoDTO> get(@PathVariable("id") Long id){
        TbPoiInfoDTO data = tbPoiInfoService.get(id);

        return new Result<TbPoiInfoDTO>().ok(data);
    }

    @PostMapping
    @Operation(summary = "保存")
    @LogOperation("保存")
    @RequiresPermissions("wxapp:tbpoiinfo:save")
    public Result save(@RequestBody TbPoiInfoDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        tbPoiInfoService.save(dto);

        return new Result();
    }

    @PutMapping
    @Operation(summary = "修改")
    @LogOperation("修改")
    @RequiresPermissions("wxapp:tbpoiinfo:update")
    public Result update(@RequestBody TbPoiInfoDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        tbPoiInfoService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @LogOperation("删除")
    @RequiresPermissions("wxapp:tbpoiinfo:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        tbPoiInfoService.delete(ids);

        return new Result();
    }

//    @GetMapping("export")
//    @Operation(summary = "导出")
//    @LogOperation("导出")
//    @RequiresPermissions("wxapp:tbpoiinfo:export")
//    public void export(@Parameter(hidden = true) @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
//        List<TbPoiInfoDTO> list = tbPoiInfoService.list(params);
//
//        ExcelUtils.exportExcelToTarget(response, null, "", list, TbPoiInfoExcel.class);
//    }

    @PostMapping("/importPoiXlsx")
    @Operation(summary = "批量导入excel")
    @LogOperation("导入文件")
    public Result importPoiXlsx(@RequestParam("uploadFile") MultipartFile file) {
        try {
            // 批量导入数据库
            tbPoiInfoService.importPoiXlsx(file);
        } catch (Exception e) {
            throw new RenException("文件解析失败", e);
        }
        return new Result();
    }


}
