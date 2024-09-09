package io.lx.modules.model.service.impl;


import io.lx.common.service.impl.BaseServiceImpl;
import io.lx.common.utils.ConvertUtils;
import io.lx.modules.model.dao.FileRecordDao;
import io.lx.modules.model.dto.FileRecordDto;
import io.lx.modules.model.entity.FileRecordEntity;
import io.lx.modules.model.entity.ResRecordEntity;
import io.lx.modules.model.service.FileRecordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FileRecordServiceImpl extends BaseServiceImpl<FileRecordDao, FileRecordEntity> implements FileRecordService {
    private final FileRecordDao fileRecordDao;
    @Override
    public List<FileRecordDto> getFileList() {
        List<FileRecordEntity> entityList = baseDao.selectList(null);
        return ConvertUtils.sourceToTarget(entityList,FileRecordDto.class);
    }

    @Override
    public void uploadFile(FileRecordEntity fileRecord){
        insert(fileRecord);
    }

    @Override
    public String getFileStatus(String fileRecordId){
        return String.valueOf(fileRecordDao.selectById(fileRecordId).getStatus());
    }

    @Override
    public void deletefile(String fileRecordId){
        deleteById(fileRecordId);
    }

    @Override
    public void record(String fileRecordId){
        FileRecordEntity fileRecordEntity  = selectById(fileRecordId);
        ResRecordEntity resRecordEntity = new ResRecordEntity();
        resRecordEntity.setId(fileRecordEntity.getId());
        resRecordEntity.setOriName(fileRecordEntity.getOriName());
        String newSavedName = fileRecordEntity.getSavedName().substring(0,fileRecordEntity.getSavedName().indexOf('.'))+"_result.jpg";
        resRecordEntity.setSavedName(newSavedName);
        String newFilePath = fileRecordEntity.getFilePath().substring(0,fileRecordEntity.getFilePath().indexOf('.'))+"_result.jpg";
        resRecordEntity.setFilePath(newFilePath);
        resRecordEntity.setStatus(fileRecordEntity.getStatus());
    }

}
