package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbSelfDrivingsDao;
import io.lx.modules.wxapp.dto.TbSelfDrivingsDTO;
import io.lx.modules.wxapp.entity.TbSelfDrivingsEntity;
import io.lx.modules.wxapp.service.TbSelfDrivingsService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
@Service
public class TbSelfDrivingsServiceImpl extends CrudServiceImpl<TbSelfDrivingsDao, TbSelfDrivingsEntity, TbSelfDrivingsDTO> implements TbSelfDrivingsService {

    @Override
    public QueryWrapper<TbSelfDrivingsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbSelfDrivingsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}