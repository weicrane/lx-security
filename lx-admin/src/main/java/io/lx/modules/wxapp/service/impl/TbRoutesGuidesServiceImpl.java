package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbRoutesGuidesDao;
import io.lx.modules.wxapp.dto.TbRoutesGuidesDTO;
import io.lx.modules.wxapp.entity.TbRoutesGuidesEntity;
import io.lx.modules.wxapp.service.TbRoutesGuidesService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
@Service
public class TbRoutesGuidesServiceImpl extends CrudServiceImpl<TbRoutesGuidesDao, TbRoutesGuidesEntity, TbRoutesGuidesDTO> implements TbRoutesGuidesService {

    @Override
    public QueryWrapper<TbRoutesGuidesEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbRoutesGuidesEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public TbRoutesGuidesEntity selectById(Integer id){
        TbRoutesGuidesEntity entity = baseDao.selectById(id);
        return entity;
    }

    @Override
    public void onsale(TbRoutesGuidesDTO dto){
        TbRoutesGuidesEntity entity = baseDao.selectById(dto.getId());
        entity.setOnsale(dto.getOnsale());
        entity.setUpdatedAt(new Date());
        baseDao.updateById(entity);
    }

}