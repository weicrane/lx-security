package io.lx.modules.wxapp.service;

import io.lx.common.service.CrudService;
import io.lx.modules.wxapp.dto.TbPicsDTO;
import io.lx.modules.wxapp.entity.TbPicsEntity;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
public interface TbPicsService extends CrudService<TbPicsEntity, TbPicsDTO> {

    void uploadFile(TbPicsEntity pic);
}