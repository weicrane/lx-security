package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbPoiImagesDao;
import io.lx.modules.wxapp.dto.TbPoiImagesDTO;
import io.lx.modules.wxapp.entity.TbPoiImagesEntity;
import io.lx.modules.wxapp.service.TbPoiImagesService;
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
public class TbPoiImagesServiceImpl extends CrudServiceImpl<TbPoiImagesDao, TbPoiImagesEntity, TbPoiImagesDTO> implements TbPoiImagesService {

    @Override
    public QueryWrapper<TbPoiImagesEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbPoiImagesEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}