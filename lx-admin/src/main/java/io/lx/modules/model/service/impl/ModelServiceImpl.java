package io.lx.modules.model.service.impl;


import io.lx.common.service.impl.BaseServiceImpl;
import io.lx.common.utils.ConvertUtils;
import io.lx.modules.model.dao.ModelDao;
import io.lx.modules.model.dto.ModelDto;
import io.lx.modules.model.entity.ModelEntity;
import io.lx.modules.model.service.ModelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ModelServiceImpl extends BaseServiceImpl<ModelDao, ModelEntity> implements ModelService {
    @Override
    public List<ModelDto> getModelList() {
        List<ModelEntity> entityList = baseDao.selectList(null);
        return ConvertUtils.sourceToTarget(entityList,ModelDto.class);
    }
}
