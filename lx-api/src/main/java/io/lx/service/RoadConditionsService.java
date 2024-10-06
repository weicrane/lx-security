package io.lx.service;

import io.lx.common.service.BaseService;
import io.lx.dto.RoadConditionsDTO;
import io.lx.entity.RoadConditionsEntity;

import java.util.List;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-03
 */
public interface RoadConditionsService extends BaseService<RoadConditionsEntity> {
    List<RoadConditionsDTO> getRoadConsList();

    RoadConditionsEntity getRoadConsById(Long id);
}