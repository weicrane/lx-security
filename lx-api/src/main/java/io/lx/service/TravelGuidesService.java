package io.lx.service;

import io.lx.common.service.CrudService;
import io.lx.dto.TravelGuidesDTO;
import io.lx.entity.TravelGuidesEntity;

import java.util.List;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-05
 */
public interface TravelGuidesService extends CrudService<TravelGuidesEntity,TravelGuidesDTO> {
    List<TravelGuidesDTO> getTravelGuidesList(String keyword);
}