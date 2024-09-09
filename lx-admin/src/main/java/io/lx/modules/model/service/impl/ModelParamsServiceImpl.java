package io.lx.modules.model.service.impl;


import io.lx.common.service.impl.BaseServiceImpl;
import io.lx.common.utils.ConvertUtils;
import io.lx.modules.model.dao.ModelParamsDao;
import io.lx.modules.model.dto.ModelParamsDto;
import io.lx.modules.model.entity.ModelParamsEntity;
import io.lx.modules.model.service.ModelParamsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ModelParamsServiceImpl extends BaseServiceImpl<ModelParamsDao, ModelParamsEntity> implements ModelParamsService {
@Override
public List<ModelParamsDto> getModelParamsList() {
    List<ModelParamsEntity> entityList = baseDao.selectList(null);
    return ConvertUtils.sourceToTarget(entityList,ModelParamsDto.class);
}
}
