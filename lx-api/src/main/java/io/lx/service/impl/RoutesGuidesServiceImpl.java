package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.RoutesGuidesDao;
import io.lx.dto.RoutesGuidesDTO;
import io.lx.entity.RoutesGuidesEntity;
import io.lx.service.RoutesGuidesService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
@Service
public class RoutesGuidesServiceImpl extends CrudServiceImpl<RoutesGuidesDao, RoutesGuidesEntity, RoutesGuidesDTO> implements RoutesGuidesService {

    @Override
    public QueryWrapper<RoutesGuidesEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<RoutesGuidesEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}