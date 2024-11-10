package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbHighlightsDao;
import io.lx.modules.wxapp.dto.TbHighlightsDTO;
import io.lx.modules.wxapp.entity.TbHighlightsEntity;
import io.lx.modules.wxapp.service.TbHighlightsService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-09
 */
@Service
public class TbHighlightsServiceImpl extends CrudServiceImpl<TbHighlightsDao, TbHighlightsEntity, TbHighlightsDTO> implements TbHighlightsService {

    @Override
    public QueryWrapper<TbHighlightsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbHighlightsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}