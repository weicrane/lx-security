package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbRoadDiscussDao;
import io.lx.modules.wxapp.dto.TbRoadDiscussDTO;
import io.lx.modules.wxapp.entity.TbRoadDiscussEntity;
import io.lx.modules.wxapp.service.TbRoadDiscussService;
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
public class TbRoadDiscussServiceImpl extends CrudServiceImpl<TbRoadDiscussDao, TbRoadDiscussEntity, TbRoadDiscussDTO> implements TbRoadDiscussService {

    @Override
    public QueryWrapper<TbRoadDiscussEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbRoadDiscussEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}