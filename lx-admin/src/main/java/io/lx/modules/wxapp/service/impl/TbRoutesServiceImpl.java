package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbRoutesDao;
import io.lx.modules.wxapp.dto.TbRoutesDTO;
import io.lx.modules.wxapp.entity.TbRoutesEntity;
import io.lx.modules.wxapp.service.TbRoutesService;
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
public class TbRoutesServiceImpl extends CrudServiceImpl<TbRoutesDao, TbRoutesEntity, TbRoutesDTO> implements TbRoutesService {

    @Override
    public QueryWrapper<TbRoutesEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbRoutesEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}