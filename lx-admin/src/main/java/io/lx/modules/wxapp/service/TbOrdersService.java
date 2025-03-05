package io.lx.modules.wxapp.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.modules.wxapp.dto.TbOrdersDTO;
import io.lx.modules.wxapp.entity.TbOrdersEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-26
 */
public interface TbOrdersService extends CrudService<TbOrdersEntity, TbOrdersDTO> {

    /**
     * 条件查询订单-分页
     * @return
     */
    PageData<TbOrdersDTO> getOrderListByPage(Map<String, Object> params);
}