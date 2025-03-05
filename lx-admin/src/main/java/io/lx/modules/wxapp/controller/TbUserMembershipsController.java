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
import io.lx.modules.wxapp.dto.AddUserRoutesDTO;
import io.lx.modules.wxapp.dto.TbRoutesGuidesDTO;
import io.lx.modules.wxapp.dto.TbUserMembershipsDTO;
import io.lx.modules.wxapp.excel.TbUserMembershipsExcel;
import io.lx.modules.wxapp.service.TbUserMembershipsService;
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
 * @since 1.0.0 2024-10-19
 */
@RestController
@RequestMapping("wxapp/tbusermemberships")
@Tag(name="会员关系")
public class TbUserMembershipsController {
    @Autowired
    private TbUserMembershipsService tbUserMembershipsService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @Parameters({
        @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
        @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
        @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
        @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    public Result<PageData<TbUserMembershipsDTO>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params){
        PageData<TbUserMembershipsDTO> page = tbUserMembershipsService.page(params);

        return new Result<PageData<TbUserMembershipsDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    public Result<TbUserMembershipsDTO> get(@PathVariable("id") Long id){
        TbUserMembershipsDTO data = tbUserMembershipsService.get(id);

        return new Result<TbUserMembershipsDTO>().ok(data);
    }

    @PostMapping
    @Operation(summary = "保存")
    @LogOperation("保存")
    public Result save(@RequestBody TbUserMembershipsDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        tbUserMembershipsService.save(dto);

        return new Result();
    }

    @PutMapping
    @Operation(summary = "修改")
    @LogOperation("修改")
    public Result update(@RequestBody TbUserMembershipsDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        tbUserMembershipsService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @LogOperation("删除")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        tbUserMembershipsService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @Operation(summary = "导出")
    @LogOperation("导出")
    public void export(@Parameter(hidden = true) @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<TbUserMembershipsDTO> list = tbUserMembershipsService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "", list, TbUserMembershipsExcel.class);
    }

    /**
     * 用户权益管理详细设计：
     * 1.查询用户列表
     * 2.点击用户，可查看用户拥有的线路
     * 3.可管理权益：新增、删除
     */

    @GetMapping("getUserRoutesByPage")
    @Operation(summary = "查询用户拥有的线路分页")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String"),
            @Parameter(name = "userId" ,description = "用户ID", required = true, ref="Long")
    })
    public Result<PageData<TbRoutesGuidesDTO>> getUserRoutesByPage(@Parameter(hidden = true) @RequestParam Map<String, Object> params){
        PageData<TbRoutesGuidesDTO> page = tbUserMembershipsService.getUserRoutesByPage(params);

        return new Result<PageData<TbRoutesGuidesDTO>>().ok(page);
    }

    @PostMapping("addUserRoutes")
    @Operation(summary = "给用户新增线路")
    @LogOperation("给用户新增线路")
    public Result addUserRoutes(@RequestBody AddUserRoutesDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        tbUserMembershipsService.addUserRoutes(dto);

        return new Result();
    }

    @DeleteMapping("deleteUserRoutes")
    @Operation(summary = "给用户删除线路")
    @LogOperation("给用户删除线路")
    public Result deleteUserRoutes(@RequestBody AddUserRoutesDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        tbUserMembershipsService.deleteUserRoutes(dto);

        return new Result();
    }

}
