package io.lx.modules.wxapp.service;

import io.lx.common.service.CrudService;
import io.lx.modules.wxapp.dto.TbPartnersDTO;
import io.lx.modules.wxapp.entity.TbPartnersEntity;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-31
 */
public interface TbPartnersService extends CrudService<TbPartnersEntity, TbPartnersDTO> {

    /**
     * 新增、修改
     */
    void update(TbPartnersDTO dto);
    /**
     * 上架
     * @param dto
     */
    void onsale(TbPartnersDTO dto);
}