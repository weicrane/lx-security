package io.lx.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.dto.PicsDTO;
import io.lx.entity.PicsEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
public interface PicsService extends CrudService<PicsEntity, PicsDTO> {

    /**
     * 获取照片墙列表
     * @param params
     * @return
     */
    PageData<PicsDTO> getPicsByPage( Map<String, Object> params,Integer guideId);

}