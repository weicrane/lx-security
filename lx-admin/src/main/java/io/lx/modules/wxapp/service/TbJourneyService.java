package io.lx.modules.wxapp.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.modules.wxapp.dto.SubmitKmlDTO;
import io.lx.modules.wxapp.dto.TbJourneyDTO;
import io.lx.modules.wxapp.entity.TbJourneyEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
public interface TbJourneyService extends CrudService<TbJourneyEntity, TbJourneyDTO> {


    /**
     * 提交kml文件
     * @param dto
     */
    void submitKml(SubmitKmlDTO dto);

    /**
     * 获取kml信息
     * @return
     */
    Map<String,Object> getKmlInfo(Integer guideId);

    /**
     * 分页查询
     * @param params
     * @param guideId
     * @return
     */
    PageData<TbJourneyDTO> selectPage(Map<String, Object> params, Integer guideId,Integer id);

    void submitJourney(TbJourneyDTO dto);

    /**
     * 查询行程
     * @param id
     * @return
     */
    TbJourneyEntity selectById(Integer id);

    /**
     * 删除线路相关行程
     * @param id
     */
    void deleteByRouteId(Integer id);
}