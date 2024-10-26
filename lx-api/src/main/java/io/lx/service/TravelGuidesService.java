package io.lx.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.dto.MyTravelGuidesDTO;
import io.lx.dto.TravelGuidesDTO;
import io.lx.entity.TravelGuidesEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-05
 */
public interface TravelGuidesService extends CrudService<TravelGuidesEntity,TravelGuidesDTO> {
    List<TravelGuidesDTO> getTravelGuidesList(String keyword,String orderField,String order);
    TravelGuidesDTO getTravelGuidesDetail(Long id);
    PageData<MyTravelGuidesDTO> getMyTravelGuides(String token,String keyword, Map<String, Object> params);
}