package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbOrdersDao;
import io.lx.modules.wxapp.dto.TbOrdersDTO;
import io.lx.modules.wxapp.entity.TbOrdersEntity;
import io.lx.modules.wxapp.service.TbOrdersService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-26
 */
@Service
public class TbOrdersServiceImpl extends CrudServiceImpl<TbOrdersDao, TbOrdersEntity, TbOrdersDTO> implements TbOrdersService {

    @Override
    public QueryWrapper<TbOrdersEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbOrdersEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}