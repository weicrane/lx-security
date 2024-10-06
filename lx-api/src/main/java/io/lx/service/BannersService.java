package io.lx.service;

import io.lx.common.service.BaseService;
import io.lx.dto.BannersDTO;
import io.lx.entity.BannersEntity;

import java.util.List;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
public interface BannersService extends BaseService<BannersEntity> {
List<BannersDTO> getBannerList();
}