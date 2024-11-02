package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbJourneyDao;
import io.lx.modules.wxapp.dto.TbJourneyDTO;
import io.lx.modules.wxapp.entity.TbJourneyEntity;
import io.lx.modules.wxapp.service.TbJourneyService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Service
public class TbJourneyServiceImpl extends CrudServiceImpl<TbJourneyDao, TbJourneyEntity, TbJourneyDTO> implements TbJourneyService {

    @Override
    public QueryWrapper<TbJourneyEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbJourneyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}