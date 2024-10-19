package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbPoiInfoDao;
import io.lx.modules.wxapp.dto.TbPoiInfoDTO;
import io.lx.modules.wxapp.entity.TbPoiInfoEntity;
import io.lx.modules.wxapp.service.TbPoiInfoService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-10
 */
@Service
public class TbPoiInfoServiceImpl extends CrudServiceImpl<TbPoiInfoDao, TbPoiInfoEntity, TbPoiInfoDTO> implements TbPoiInfoService {

    @Override
    public QueryWrapper<TbPoiInfoEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbPoiInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}