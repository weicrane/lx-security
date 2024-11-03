package io.lx.service;

import io.lx.common.service.CrudService;
import io.lx.dto.PoiInfoDTO;
import io.lx.entity.PoiInfoEntity;

import java.util.List;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
public interface PoiInfoService extends CrudService<PoiInfoEntity, PoiInfoDTO> {

    List<PoiInfoDTO> getPoiList(Integer routeGuideId,String poiType);

    PoiInfoDTO getPoiInfo(Long pointId);
}