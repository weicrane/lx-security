package io.lx.modules.wxapp.service;

import io.lx.common.service.CrudService;
import io.lx.modules.wxapp.dto.TbRoutesGuidesDTO;
import io.lx.modules.wxapp.entity.TbRoutesGuidesEntity;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
public interface TbRoutesGuidesService extends CrudService<TbRoutesGuidesEntity, TbRoutesGuidesDTO> {

    /**
     * 查询线路
     * @param id
     * @return
     */
    TbRoutesGuidesEntity selectById(Integer id);
}