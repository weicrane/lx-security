package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.SelfDrivingsDao;
import io.lx.dto.SelfDrivingsDTO;
import io.lx.entity.SelfDrivingsEntity;
import io.lx.service.SelfDrivingsService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
@Service
public class SelfDrivingsServiceImpl extends CrudServiceImpl<SelfDrivingsDao, SelfDrivingsEntity, SelfDrivingsDTO> implements SelfDrivingsService {

    @Override
    public QueryWrapper<SelfDrivingsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SelfDrivingsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}