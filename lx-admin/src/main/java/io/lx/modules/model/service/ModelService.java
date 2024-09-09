package io.lx.modules.model.service;

import io.lx.common.service.BaseService;
import io.lx.modules.model.dto.ModelDto;
import io.lx.modules.model.entity.ModelEntity;

import java.util.List;

public interface ModelService extends BaseService<ModelEntity> {
    List<ModelDto> getModelList();
}
