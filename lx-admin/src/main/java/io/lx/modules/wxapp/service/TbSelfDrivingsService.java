package io.lx.modules.wxapp.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.modules.wxapp.dto.TbSelfDrivingsDTO;
import io.lx.modules.wxapp.entity.TbSelfDrivingsEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
public interface TbSelfDrivingsService extends CrudService<TbSelfDrivingsEntity, TbSelfDrivingsDTO> {

    PageData<TbSelfDrivingsDTO> getListByPage(Map<String, Object> params, String keyword);

    /**
     * 新增、修改
     */
    void update(TbSelfDrivingsDTO dto);
}