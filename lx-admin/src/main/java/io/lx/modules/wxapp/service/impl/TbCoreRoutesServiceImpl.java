package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbCoreRoutesDao;
import io.lx.modules.wxapp.dto.TbCoreRoutesDTO;
import io.lx.modules.wxapp.entity.TbCoreRoutesEntity;
import io.lx.modules.wxapp.service.TbCoreRoutesService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-27
 */
@Service
public class TbCoreRoutesServiceImpl extends CrudServiceImpl<TbCoreRoutesDao, TbCoreRoutesEntity, TbCoreRoutesDTO> implements TbCoreRoutesService {

    @Override
    public QueryWrapper<TbCoreRoutesEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbCoreRoutesEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}