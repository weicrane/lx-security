package io.lx.service;

import io.lx.common.service.CrudService;
import io.lx.dto.JourneyDTO;
import io.lx.entity.JourneyEntity;
import io.lx.entity.UserEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
public interface JourneyService extends CrudService<JourneyEntity, JourneyDTO> {

    /**
     * 获取主线行程信息
     * @param routeId
     * @param journeyType
     * @param entity
     * @return
     */
    Map<String,Object> getMainJourney(Integer guideId, String journeyType, UserEntity entity);
}