package io.lx.modules.model.controller;

import io.lx.common.exception.ErrorCode;
import io.lx.common.exception.RenException;
import io.lx.common.utils.Result;
import io.lx.modules.model.dto.FileRecordDto;
import io.lx.modules.model.entity.FileRecordEntity;
import io.lx.modules.model.service.FileRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传管理
 *
 * @author wyh
 */
@RestController
@RequestMapping("/image")
@Tag(name = "后台图片上传管理")

public class FileUploadController {

    @Value("${web.upload-path}")
    private String uploadPath;

    @Autowired
    private FileRecordService fileRecordService;

    @PostMapping("/upload")
    @Operation(summary = "上传图片")
    public String upload(MultipartFile uploadFile, HttpServletRequest request) {

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

            // 返回上传文件的访问路径
//            String filePath = request.getScheme() + "://" + request.getServerName()
//                    + ":" + request.getServerPort() + request.getContextPath()
//                     + newName;
            String filePath = "images/"+newName;

            // 记录到表里
            FileRecordEntity fileRecord = new FileRecordEntity();
            fileRecord.setOriName(oldName);
            fileRecord.setSavedName(newName);
            fileRecord.setUploadTime(new Date());
            fileRecord.setFilePath(filePath);
            fileRecord.setStatus(0);//0-未处理
            fileRecordService.uploadFile(fileRecord);

            // 获取刚插入记录的 ID
            Long fileRecordId = fileRecord.getId();
            if (fileRecordId == null) {
                throw new RuntimeException("Failed to retrieve file record ID.");
            }

            // 返回文件名
            return filePath;
        } catch (IOException e) {
            throw new RenException(ErrorCode.ACCOUNT_FILE_ERROR, e.getMessage());
        }
    }

    // 虚拟环境
    private void callPythonScript(String fileRecordId) throws IOException {
        // 设置虚拟环境中的 Python 解释器路径
        String pythonInterpreter = "myenv/bin/python3"; // 修改为你的虚拟环境路径
        String pythonScriptPath = "script.py";

        // 创建 ProcessBuilder 对象
        ProcessBuilder processBuilder = new ProcessBuilder(pythonInterpreter, pythonScriptPath, fileRecordId.toString());

        // 设置工作目录（可选）
        processBuilder.directory(new File("/python"));

        // 启动进程
        Process process = processBuilder.start();

        // 读取输出和错误流（可选）
        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            // 读取标准输出
            String s;
            while ((s = stdInput.readLine()) != null) {
                System.out.println("OUTPUT: " + s);
            }

            // 读取标准错误
            while ((s = stdError.readLine()) != null) {
                System.err.println("ERROR: " + s);
            }

            // 等待 Python 脚本执行完成
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                // 处理错误
                throw new IOException("Python script failed with exit code " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Python script execution interrupted", e);
        }
    }

    /**
     * 开始计算
     */
    @PostMapping("/start")
    public Result start(@RequestBody FileRecordDto fileRecordDto) {
        // 调用 Python 脚本
        try {
            callPythonScript(fileRecordDto.getSaved_Name());
            String status = fileRecordService.getFileStatus(String.valueOf(fileRecordDto.getId()));
            Result result = new Result();
            result.setData(status);
            return result;
        } catch (IOException e) {
            throw new RenException(ErrorCode.PYTHON_ERROR);
        }
    }

    /**
     * 清除
     */
    @PostMapping("/deletefile")
    public void deletefile(Long fileRecordId) {
        fileRecordService.deletefile(String.valueOf(fileRecordId));
    }

    /**
     * 清除
     */
    @PostMapping("/record")
    public Result record(@RequestBody FileRecordDto fileRecordDto) {
        fileRecordService.record(String.valueOf(fileRecordDto.getId()));
        return new Result();
    }
}
