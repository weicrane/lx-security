package io.lx.modules.wxapp.controller;

import io.lx.common.annotation.LogOperation;
import io.lx.common.constant.Constant;
import io.lx.common.exception.RenException;
import io.lx.common.page.PageData;
import io.lx.common.utils.ExcelUtils;
import io.lx.common.utils.Result;
import io.lx.common.validator.ValidatorUtils;
import io.lx.common.validator.group.AddGroup;
import io.lx.common.validator.group.DefaultGroup;
import io.lx.common.validator.group.UpdateGroup;
import io.lx.modules.wxapp.dto.TbRecommendsDTO;
import io.lx.modules.wxapp.entity.TbRecommendsEntity;
import io.lx.modules.wxapp.excel.TbRecommendsExcel;
import io.lx.modules.wxapp.service.TbRecommendsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.jsoup.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 首页推荐
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-12-18
 */
@RestController
@RequestMapping("wxapp/tbrecommends")
@Tag(name="首页推荐")
public class TbRecommendsController {
    @Autowired
    private TbRecommendsService tbRecommendsService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @Parameters({
        @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
        @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
        @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
        @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    public Result<PageData<TbRecommendsDTO>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params){
        PageData<TbRecommendsDTO> page = tbRecommendsService.page(params);

        return new Result<PageData<TbRecommendsDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    public Result<TbRecommendsDTO> get(@PathVariable("id") Long id){
        TbRecommendsDTO data = tbRecommendsService.get(id);

        return new Result<TbRecommendsDTO>().ok(data);
    }

    @GetMapping("getTbRecommendsListByPage")
    @Operation(summary = "获取猜你喜欢列表")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    public Result<PageData<TbRecommendsEntity>> getTbRecommendsListByPage(@Parameter(hidden = false) @RequestParam Map<String, Object> params){
        PageData<TbRecommendsEntity> page = tbRecommendsService.getTbRecommendsListByPage(params);
        return new Result<PageData<TbRecommendsEntity>>().ok(page);
    }


    @PostMapping
    @Operation(summary = "保存")
    @LogOperation("保存")
    public Result save(@RequestBody TbRecommendsDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        tbRecommendsService.save(dto);

        return new Result();
    }

    @PutMapping
    @Operation(summary = "修改")
    @LogOperation("修改")
    public Result update(@RequestBody TbRecommendsDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

//        tbRecommendsService.update(dto);
        tbRecommendsService.save(dto);

        return new Result();
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除")
    @LogOperation("删除")
    public Result delete(@RequestBody TbRecommendsDTO dto){
        //效验
        if (dto.getId()==null){
            throw new RenException("id参数为空");
        }
        if (StringUtil.isBlank(dto.getType())){
            throw new RenException("类型参数为空");
        }
        tbRecommendsService.deleteByIdAndType(dto.getId(),dto.getType());

        return new Result();
    }

    @GetMapping("export")
    @Operation(summary = "导出")
    @LogOperation("导出")
    public void export(@Parameter(hidden = true) @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<TbRecommendsDTO> list = tbRecommendsService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "首页推荐", list, TbRecommendsExcel.class);
    }

}
