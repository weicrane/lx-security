package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.PoiInfoDao;
import io.lx.dto.PoiInfoDTO;
import io.lx.entity.PoiInfoEntity;
import io.lx.service.PoiInfoService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-10
 */
@Service
public class PoiInfoServiceImpl extends CrudServiceImpl<PoiInfoDao, PoiInfoEntity, PoiInfoDTO> implements PoiInfoService {

    @Override
    public QueryWrapper<PoiInfoEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<PoiInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}