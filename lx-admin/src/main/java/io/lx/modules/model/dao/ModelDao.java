package io.lx.modules.model.dao;

import io.lx.common.dao.BaseDao;
import io.lx.modules.model.entity.ModelEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ModelDao extends BaseDao<ModelEntity> {
}
