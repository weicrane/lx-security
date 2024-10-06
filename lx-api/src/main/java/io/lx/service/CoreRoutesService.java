package io.lx.service;

import io.lx.common.service.BaseService;
import io.lx.dto.CoreRoutesDTO;
import io.lx.entity.CoreRoutesEntity;

import java.util.List;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-27
 */
public interface CoreRoutesService extends BaseService<CoreRoutesEntity> {
    List<CoreRoutesDTO> getCoreList();
}