package io.lx.modules.model.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.BaseServiceImpl;
import io.lx.common.utils.ConvertUtils;
import io.lx.modules.model.dao.ResRecordDao;
import io.lx.modules.model.dto.ResRecordDto;
import io.lx.modules.model.entity.ResRecordEntity;
import io.lx.modules.model.service.ResRecordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ResRecordServiceImpl extends BaseServiceImpl<ResRecordDao, ResRecordEntity> implements ResRecordService {
    private final ResRecordDao resRecordDao;

    @Override
    public List<ResRecordDto> getFileList() {
        List<ResRecordEntity> entityList = baseDao.selectList(null);
        return ConvertUtils.sourceToTarget(entityList, ResRecordDto.class);
    }

    @Override
    public PageData<ResRecordDto> page(Map<String, Object> params) {
        IPage<ResRecordEntity> page = baseDao.selectPage(
                getPage(params, "upload_time", false),
                getWrapper(params)
        );
        return getPageData(page, ResRecordDto.class);
    }

    private QueryWrapper<ResRecordEntity> getWrapper(Map<String, Object> params) {
        QueryWrapper<ResRecordEntity> wapper = new QueryWrapper<>();
        return wapper;
    }

    @Override
    public void uploadFile(ResRecordEntity resRecord) {
        insert(resRecord);
    }

    @Override
    public String getFileStatus(String resRecordId) {
        return String.valueOf(resRecordDao.selectById(resRecordId).getStatus());
    }

    @Override
    public void deletefile(String resRecordId) {
        deleteById(resRecordId);
    }

}
