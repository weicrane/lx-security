package io.lx.modules.wxapp.controller;

import io.lx.common.annotation.LogOperation;
import io.lx.common.constant.Constant;
import io.lx.common.exception.ErrorCode;
import io.lx.common.exception.RenException;
import io.lx.common.page.PageData;
import io.lx.common.utils.ExcelUtils;
import io.lx.common.utils.Result;
import io.lx.common.validator.AssertUtils;
import io.lx.common.validator.ValidatorUtils;
import io.lx.common.validator.group.AddGroup;
import io.lx.common.validator.group.DefaultGroup;
import io.lx.common.validator.group.UpdateGroup;
import io.lx.modules.wxapp.dto.TbPicsDTO;
import io.lx.modules.wxapp.entity.TbPicsEntity;
import io.lx.modules.wxapp.excel.TbPicsExcel;
import io.lx.modules.wxapp.service.TbPicsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@RestController
@RequestMapping("wxapp/tbpics")
@Tag(name="玩法路线照片墙")
public class TbPicsController {
    @Autowired
    private TbPicsService tbPicsService;

    @Value("${web.pic-path}")
    private String picPath;

    @GetMapping("page")
    @Operation(summary = "分页")
    @Parameters({
        @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
        @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
        @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
        @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    @RequiresPermissions("wxapp:tbpics:page")
    public Result<PageData<TbPicsDTO>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params){
        PageData<TbPicsDTO> page = tbPicsService.page(params);

        return new Result<PageData<TbPicsDTO>>().ok(page);
    }

    @GetMapping("getListByPage")
    @Operation(summary = "根据线路id查询照片墙-分页")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    public Result<PageData<TbPicsDTO>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params,
                                            @Parameter Integer guideId){

        if (guideId==null) {
            throw new RenException("线路Id不可为空");
        }
        PageData<TbPicsDTO> page = tbPicsService.getListByPage(params,guideId);

        return new Result<PageData<TbPicsDTO>>().ok(page);
    }

    @GetMapping("getListByGuideId")
    @Operation(summary = "根据线路id查询照片墙-不分页")
    public Result<List<TbPicsDTO>> getListByGuideId(@Parameter Integer guideId){

        if (guideId==null) {
            throw new RenException("线路Id不可为空");
        }
        List<TbPicsDTO> list = tbPicsService.getListByGuideId(guideId);

        return new Result<List<TbPicsDTO>>().ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @RequiresPermissions("wxapp:tbpics:info")
    public Result<TbPicsDTO> get(@PathVariable("id") Long id){
        TbPicsDTO data = tbPicsService.get(id);

        return new Result<TbPicsDTO>().ok(data);
    }

    @PostMapping
    @Operation(summary = "保存")
    @LogOperation("保存")
    @RequiresPermissions("wxapp:tbpics:save")
    public Result save(@RequestBody TbPicsDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        tbPicsService.save(dto);

        return new Result();
    }

    @PutMapping
    @Operation(summary = "修改")
    @LogOperation("修改")
    @RequiresPermissions("wxapp:tbpics:update")
    public Result update(@RequestBody TbPicsDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        tbPicsService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @LogOperation("删除")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        tbPicsService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @Operation(summary = "导出")
    @LogOperation("导出")
    @RequiresPermissions("wxapp:tbpics:export")
    public void export(@Parameter(hidden = true) @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<TbPicsDTO> list = tbPicsService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "", list, TbPicsExcel.class);
    }

    @PostMapping("/uploadImg")
    @Operation(summary = "上传图片")
    public Result upload(MultipartFile uploadFile,@Parameter Integer guideId) {

        // 空文件检验
        if (uploadFile == null){
            throw new RenException("上传内容不可为空");
        }

        if (guideId == null){
            throw new RenException("玩法指南id不可为空");
        }

        // 确保上传路径存在
        File uploadDir = new File(picPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 对上传的文件重命名，避免文件重名
        String oldName = uploadFile.getOriginalFilename();
        if (oldName == null || oldName.isEmpty()) {
            throw new RenException(ErrorCode.ACCOUNT_FILE_ERROR);
        }
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        File destinationFile = new File(uploadDir, newName);

        try {
            // 文件保存
            uploadFile.transferTo(destinationFile);
            // 文件路径
            String filePath = "pics/" + newName;

            // 记录到表里
            TbPicsEntity fileRecord = new TbPicsEntity();
            fileRecord.setOriName(oldName);
            fileRecord.setSavedName(newName);
            fileRecord.setUploadTime(new Date());
            fileRecord.setFilePath(filePath);
            fileRecord.setGuideId(guideId);
            tbPicsService.uploadFile(fileRecord);

            // 获取刚插入记录的 ID
            Long fileRecordId = fileRecord.getId();
            if (fileRecordId == null) {
                throw new RuntimeException("Failed to retrieve file record ID.");
            }

            // 返回
            Map<String,Object> map = new HashMap<>();
            map.put("id",fileRecordId);
            map.put("oldName",oldName);
            map.put("newName",newName);
            map.put("filePath",filePath);

            return new Result().ok(map);
        } catch (IOException e) {
            throw new RenException(ErrorCode.ACCOUNT_FILE_ERROR, e.getMessage());
        }
    }

    @PostMapping("/uploadImgsList")
    @Operation(summary = "批量上传图片")
    public List<Map<String, Object>> uploadMultiple(
            @RequestParam("uploadFiles") MultipartFile[] uploadFiles,
            @RequestParam("guideId") Integer guideId) {

        // 空文件检验
        if (uploadFiles == null || uploadFiles.length == 0) {
            throw new RenException("上传内容不可为空");
        }
        if (guideId == null) {
            throw new RenException("玩法指南id不可为空");
        }

        // 确保上传路径存在
        File uploadDir = new File(picPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 存储所有文件的上传结果
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (MultipartFile uploadFile : uploadFiles) {
            // 对上传的文件重命名，避免文件重名
            String oldName = uploadFile.getOriginalFilename();
            if (oldName == null || oldName.isEmpty()) {
                throw new RenException(ErrorCode.ACCOUNT_FILE_ERROR);
            }
            String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
            File destinationFile = new File(uploadDir, newName);

            try {
                // 文件保存
                uploadFile.transferTo(destinationFile);
                // 文件路径
                String filePath = "pics/" + newName;

                // 记录到表里
                TbPicsEntity fileRecord = new TbPicsEntity();
                fileRecord.setOriName(oldName);
                fileRecord.setSavedName(newName);
                fileRecord.setUploadTime(new Date());
                fileRecord.setFilePath(filePath);
                fileRecord.setGuideId(guideId);
                tbPicsService.uploadFile(fileRecord);

                // 获取刚插入记录的 ID
                Long fileRecordId = fileRecord.getId();
                if (fileRecordId == null) {
                    throw new RuntimeException("Failed to retrieve file record ID.");
                }

                // 返回单个文件的上传结果
                Map<String, Object> fileMap = new HashMap<>();
                fileMap.put("id", fileRecordId);
                fileMap.put("oldName", oldName);
                fileMap.put("newName", newName);
                fileMap.put("filePath", filePath);

                resultList.add(fileMap);
            } catch (IOException e) {
                throw new RenException(ErrorCode.ACCOUNT_FILE_ERROR, e.getMessage());
            }
        }

        // 返回所有文件的上传结果
        return resultList;
    }


}
