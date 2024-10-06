package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbBannersDao;
import io.lx.modules.wxapp.dto.TbBannersDTO;
import io.lx.modules.wxapp.entity.TbBannersEntity;
import io.lx.modules.wxapp.service.TbBannersService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Service
public class TbBannersServiceImpl extends CrudServiceImpl<TbBannersDao, TbBannersEntity, TbBannersDTO> implements TbBannersService {

    @Override
    public QueryWrapper<TbBannersEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbBannersEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}