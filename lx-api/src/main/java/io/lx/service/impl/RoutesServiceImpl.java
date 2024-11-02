package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.RoutesDao;
import io.lx.dto.RoutesDTO;
import io.lx.entity.RoutesEntity;
import io.lx.service.RoutesService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Service
public class RoutesServiceImpl extends CrudServiceImpl<RoutesDao, RoutesEntity, RoutesDTO> implements RoutesService {

    @Override
    public QueryWrapper<RoutesEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<RoutesEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}