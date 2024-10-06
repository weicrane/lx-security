package io.lx.service.impl;

import io.lx.common.service.impl.BaseServiceImpl;
import io.lx.common.utils.ConvertUtils;
import io.lx.dao.RoadConditionsDao;
import io.lx.dto.RoadConditionsDTO;
import io.lx.entity.RoadConditionsEntity;
import io.lx.service.RoadConditionsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-03
 */
@Service
public class RoadConditionsServiceImpl extends BaseServiceImpl<RoadConditionsDao, RoadConditionsEntity> implements RoadConditionsService {

    @Override
    public List<RoadConditionsDTO> getRoadConsList() {
        List<RoadConditionsEntity> list = baseDao.selectList(null);
        return ConvertUtils.sourceToTarget(list, RoadConditionsDTO.class);
    }

    @Override
    public RoadConditionsEntity getRoadConsById(Long id) {
        RoadConditionsEntity roadConditionsEntity = baseDao.selectById(id);
        return roadConditionsEntity;
    }
}