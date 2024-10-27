package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbSvipDao;
import io.lx.modules.wxapp.dto.TbSvipDTO;
import io.lx.modules.wxapp.entity.TbSvipEntity;
import io.lx.modules.wxapp.service.TbSvipService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-27
 */
@Service
public class TbSvipServiceImpl extends CrudServiceImpl<TbSvipDao, TbSvipEntity, TbSvipDTO> implements TbSvipService {

    @Override
    public QueryWrapper<TbSvipEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbSvipEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}