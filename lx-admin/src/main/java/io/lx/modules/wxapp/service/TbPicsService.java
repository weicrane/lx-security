package io.lx.modules.wxapp.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.modules.wxapp.dto.TbPicsDTO;
import io.lx.modules.wxapp.entity.TbPicsEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
public interface TbPicsService extends CrudService<TbPicsEntity, TbPicsDTO> {

    void uploadFile(TbPicsEntity pic);

    PageData<TbPicsDTO> getListByPage(Map<String, Object> params, Integer guideId);

    List<TbPicsDTO> getListByGuideId(Integer guideId);


}