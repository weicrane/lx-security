package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbPartnersDao;
import io.lx.modules.wxapp.dto.TbPartnersDTO;
import io.lx.modules.wxapp.entity.TbPartnersEntity;
import io.lx.modules.wxapp.service.TbPartnersService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-31
 */
@Service
public class TbPartnersServiceImpl extends CrudServiceImpl<TbPartnersDao, TbPartnersEntity, TbPartnersDTO> implements TbPartnersService {

    @Override
    public QueryWrapper<TbPartnersEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbPartnersEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}