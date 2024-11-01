package io.lx.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.dto.PartnersDTO;
import io.lx.entity.PartnersEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-31
 */
public interface PartnersService extends CrudService<PartnersEntity, PartnersDTO> {

    /**
     * 查询商家列表-分页
     * @param params
     * @param keyword
     * @return
     */
    PageData<PartnersDTO> getPartnersListByPage(Map<String, Object> params, String keyword);

    /**
     * 查询商家详情
     * @param id
     * @return
     */
    PartnersEntity getPartnersDetailById(Integer id);
}