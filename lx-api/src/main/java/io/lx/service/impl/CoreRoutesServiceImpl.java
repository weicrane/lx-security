package io.lx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.BaseServiceImpl;
import io.lx.common.utils.ConvertUtils;
import io.lx.dao.CoreRoutesDao;
import io.lx.dto.CoreRoutesDTO;
import io.lx.entity.CoreRoutesEntity;
import io.lx.service.CoreRoutesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-27
 */
@Service
public class CoreRoutesServiceImpl extends BaseServiceImpl<CoreRoutesDao, CoreRoutesEntity> implements CoreRoutesService {

    @Override
    public List<CoreRoutesDTO> getCoreList(){
        QueryWrapper<CoreRoutesEntity> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        List<CoreRoutesEntity> list = baseDao.selectList(wrapper);
        return ConvertUtils.sourceToTarget(list,CoreRoutesDTO.class);
    }

}