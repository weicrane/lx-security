package io.lx.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.dto.SelfDrivingsDTO;
import io.lx.entity.SelfDrivingsEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
public interface SelfDrivingsService extends CrudService<SelfDrivingsEntity, SelfDrivingsDTO> {

    PageData<SelfDrivingsDTO> getSelfDrivingListByPage(Map<String, Object> params,String keyword);
}