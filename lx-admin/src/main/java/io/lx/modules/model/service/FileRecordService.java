package io.lx.modules.model.service;

import io.lx.common.service.BaseService;
import io.lx.modules.model.dto.FileRecordDto;
import io.lx.modules.model.entity.FileRecordEntity;

import java.util.List;

public interface FileRecordService extends BaseService<FileRecordEntity> {
    List<FileRecordDto> getFileList();
    void uploadFile(FileRecordEntity fileRecord);
    String getFileStatus(String fileRecordId);
    void deletefile(String fileRecordId);
    void record(String fileRecordId);
}
