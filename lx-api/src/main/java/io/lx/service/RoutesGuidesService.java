package io.lx.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.dto.RoutesGuidesDTO;
import io.lx.entity.RoutesGuidesEntity;
import io.lx.entity.UserEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
public interface RoutesGuidesService extends CrudService<RoutesGuidesEntity, RoutesGuidesDTO> {


    /**
     * 获取玩法路线指南列表-分页、搜索
     * @param keyword
     * @param season
     * @param params
     * @return
     */
    PageData<RoutesGuidesDTO> getAllRoutesGuidesByPage( String keyword, String season,Map<String, Object> params);

    /**
     * 获取玩法详情
     * @param id
     * @return
     */
    RoutesGuidesDTO getRoutesGuidesDetail(Integer id);

    PageData<RoutesGuidesDTO> getMyGuidesByPage(String keyword, Map<String, Object> params, UserEntity user);

}