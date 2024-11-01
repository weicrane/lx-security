package io.lx.modules.wxapp.controller;

import io.lx.common.exception.ErrorCode;
import io.lx.common.exception.RenException;
import io.lx.modules.model.entity.FileRecordEntity;
import io.lx.modules.model.service.FileRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@RestController
@RequestMapping("common")
@Tag(name = "后台管理通用接口")
public class CommonController {

    @Value("${web.upload-path}")
    private String uploadPath;

    @Autowired
    private FileRecordService fileRecordService;

    @PostMapping("/uploadImg")
    @Operation(summary = "上传图片")
    public Map<String,Object> upload(MultipartFile uploadFile, HttpServletRequest request) {

        // 空文件检验
        if (uploadFile == null){
            throw new RenException("上传内容不可为空");
        }

        // 确保上传路径存在
        File uploadDir = new File(uploadPath);
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
            String filePath = "images/" + newName;

            // 记录到表里
            FileRecordEntity fileRecord = new FileRecordEntity();
            fileRecord.setOriName(oldName);
            fileRecord.setSavedName(newName);
            fileRecord.setUploadTime(new Date());
            fileRecord.setFilePath(filePath);
            fileRecord.setStatus(0);
            fileRecordService.uploadFile(fileRecord);

            // 获取刚插入记录的 ID
            Long fileRecordId = fileRecord.getId();
            if (fileRecordId == null) {
                throw new RuntimeException("Failed to retrieve file record ID.");
            }

            // 返回
            Map<String,Object> map = new HashMap<>();
            map.put("fileId",fileRecordId);
            map.put("oldName",oldName);
            map.put("newName",newName);
            map.put("filePath",filePath);

            return map;
        } catch (IOException e) {
            throw new RenException(ErrorCode.ACCOUNT_FILE_ERROR, e.getMessage());
        }
    }


}
