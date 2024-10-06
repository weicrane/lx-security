package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbHotWordsDao;
import io.lx.modules.wxapp.dto.TbHotWordsDTO;
import io.lx.modules.wxapp.entity.TbHotWordsEntity;
import io.lx.modules.wxapp.service.TbHotWordsService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 热词
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Service
public class TbHotWordsServiceImpl extends CrudServiceImpl<TbHotWordsDao, TbHotWordsEntity, TbHotWordsDTO> implements TbHotWordsService {

    @Override
    public QueryWrapper<TbHotWordsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbHotWordsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}