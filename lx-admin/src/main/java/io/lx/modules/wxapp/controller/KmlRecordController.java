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
import io.lx.modules.wxapp.dto.KmlRecordDTO;
import io.lx.modules.wxapp.entity.KmlRecordEntity;
import io.lx.modules.wxapp.excel.KmlRecordExcel;
import io.lx.modules.wxapp.service.KmlRecordService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@RestController
@RequestMapping("wxapp/kmlrecord")
@Tag(name = "kml文件管理")
public class KmlRecordController {
    @Autowired
    private KmlRecordService kmlRecordService;

    @Value("${web.kml-path}")
    private String kmlPath;

    @GetMapping("page")
    @Operation(summary = "分页")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref = "int"),
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY, required = true, ref = "int"),
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref = "String"),
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref = "String")
    })
    @RequiresPermissions("wxapp:kmlrecord:page")
    public Result<PageData<KmlRecordDTO>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params) {
        PageData<KmlRecordDTO> page = kmlRecordService.page(params);

        return new Result<PageData<KmlRecordDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @RequiresPermissions("wxapp:kmlrecord:info")
    public Result<KmlRecordDTO> get(@PathVariable("id") Long id) {
        KmlRecordDTO data = kmlRecordService.get(id);

        return new Result<KmlRecordDTO>().ok(data);
    }

    @PostMapping
    @Operation(summary = "保存")
    @LogOperation("保存")
    @RequiresPermissions("wxapp:kmlrecord:save")
    public Result save(@RequestBody KmlRecordDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        kmlRecordService.save(dto);

        return new Result();
    }

    @PutMapping
    @Operation(summary = "修改")
    @LogOperation("修改")
    @RequiresPermissions("wxapp:kmlrecord:update")
    public Result update(@RequestBody KmlRecordDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        kmlRecordService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @LogOperation("删除")
    @RequiresPermissions("wxapp:kmlrecord:delete")
    public Result delete(@RequestBody Long[] ids) {
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        kmlRecordService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @Operation(summary = "导出")
    @LogOperation("导出")
    @RequiresPermissions("wxapp:kmlrecord:export")
    public void export(@Parameter(hidden = true) @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<KmlRecordDTO> list = kmlRecordService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "", list, KmlRecordExcel.class);
    }

    @PostMapping("/uploadKml")
    @Operation(summary = "上传kml文件")
    public Map<String, Object> upload(MultipartFile uploadFile) {

        // 空文件检验
        if (uploadFile == null) {
            throw new RenException("上传内容不可为空");
        }

        // 检查文件后缀是否为 .kml
        String fileName = uploadFile.getOriginalFilename();
        if (fileName == null || !fileName.toLowerCase().endsWith(".kml")) {
            throw new RenException("上传的文件必须是 KML 格式");
        }

        // 确保上传路径存在
        File uploadDir = new File(kmlPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 对上传的文件重命名，避免文件重名
        String oldName = uploadFile.getOriginalFilename();
        if (oldName == null || oldName.isEmpty()) {
            throw new RenException(ErrorCode.ACCOUNT_FILE_ERROR);
        }
        // 获取文件扩展名
        String extension = oldName.substring(oldName.lastIndexOf("."));
        // 获取当前时间的年月日时分秒
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        // 创建新文件名，拼接旧文件名和时间戳
        String newName = oldName.substring(0, oldName.lastIndexOf(".")) + "_" + timestamp + extension;
        File destinationFile = new File(uploadDir, newName);

        try {
            // 文件保存
            uploadFile.transferTo(destinationFile);
            // 文件路径
            String filePath = newName;

            // 记录到表里
            KmlRecordEntity fileRecord = new KmlRecordEntity();
            fileRecord.setOriName(oldName);
            fileRecord.setSavedName(newName);
            fileRecord.setUploadTime(new Date());
            fileRecord.setFilePath(filePath);
            kmlRecordService.uploadFile(fileRecord);

            // 获取刚插入记录的 ID
            Integer fileRecordId = fileRecord.getId();
            if (fileRecordId == null) {
                throw new RuntimeException("Failed to retrieve file record ID.");
            }

            // 返回
            Map<String, Object> map = new HashMap<>();
            map.put("fileId", fileRecordId);
            map.put("oldName", oldName);
            map.put("newName", newName);
            map.put("filePath", filePath);

            return map;
        } catch (IOException e) {
            throw new RenException(ErrorCode.ACCOUNT_FILE_ERROR, e.getMessage());
        }
    }


}
