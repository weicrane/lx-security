package io.lx.modules.model.service;

import io.lx.common.service.BaseService;
import io.lx.modules.model.dto.ModelParamsDto;
import io.lx.modules.model.entity.ModelParamsEntity;

import java.util.List;

public interface ModelParamsService extends BaseService<ModelParamsEntity> {
    List<ModelParamsDto> getModelParamsList();
}
