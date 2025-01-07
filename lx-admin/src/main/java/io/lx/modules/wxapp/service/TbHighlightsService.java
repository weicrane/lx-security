package io.lx.modules.wxapp.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.modules.wxapp.dto.TbHighlightsDTO;
import io.lx.modules.wxapp.entity.TbHighlightsEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-09
 */
public interface TbHighlightsService extends CrudService<TbHighlightsEntity, TbHighlightsDTO> {

    PageData<TbHighlightsDTO> selectPage(Map<String, Object> params, Integer journeyId, Integer id);

    void submit(TbHighlightsDTO dto);

    // 删除亮点
    void deleteByJourneyId(Integer id);
}