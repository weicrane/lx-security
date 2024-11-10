package io.lx.service;

import io.lx.common.service.CrudService;
import io.lx.dto.HighlightsDTO;
import io.lx.entity.HighlightsEntity;

import java.util.List;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-09
 */
public interface HighlightsService extends CrudService<HighlightsEntity, HighlightsDTO> {

    /**
     * 获取玩法详情
     * @param journeyId
     * @return
     */
    List<HighlightsDTO> getJourneyDetail(Integer journeyId);
}