package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbRoadConditionsDao;
import io.lx.modules.wxapp.dto.TbRoadConditionsDTO;
import io.lx.modules.wxapp.entity.TbRoadConditionsEntity;
import io.lx.modules.wxapp.service.TbRoadConditionsService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-20
 */
@Service
public class TbRoadConditionsServiceImpl extends CrudServiceImpl<TbRoadConditionsDao, TbRoadConditionsEntity, TbRoadConditionsDTO> implements TbRoadConditionsService {

    @Override
    public QueryWrapper<TbRoadConditionsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbRoadConditionsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}