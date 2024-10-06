package io.lx.service.impl;

import io.lx.common.service.impl.BaseServiceImpl;
import io.lx.common.utils.ConvertUtils;
import io.lx.dao.BannersDao;
import io.lx.dto.BannersDTO;
import io.lx.entity.BannersEntity;
import io.lx.service.BannersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Service
@AllArgsConstructor
public class BannersServiceImpl extends BaseServiceImpl<BannersDao, BannersEntity> implements BannersService {
@Override
public List<BannersDTO> getBannerList(){
    List<BannersEntity> list = baseDao.selectList(null);
    return ConvertUtils.sourceToTarget(list, BannersDTO.class);
}

}