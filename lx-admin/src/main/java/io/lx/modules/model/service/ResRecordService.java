package io.lx.modules.model.service;

import io.lx.common.page.PageData;
import io.lx.common.service.BaseService;
import io.lx.modules.model.dto.ResRecordDto;
import io.lx.modules.model.entity.ResRecordEntity;

import java.util.List;
import java.util.Map;

public interface ResRecordService extends BaseService<ResRecordEntity> {
    List<ResRecordDto> getFileList();
    PageData<ResRecordDto> page(Map<String,Object> params);
    void uploadFile(ResRecordEntity resRecord);
    String getFileStatus(String resRecordId);
    void deletefile(String resRecordId);
}
