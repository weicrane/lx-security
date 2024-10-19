package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.PoiImagesDao;
import io.lx.dto.PoiImagesDTO;
import io.lx.entity.PoiImagesEntity;
import io.lx.service.PoiImagesService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-10
 */
@Service
public class PoiImagesServiceImpl extends CrudServiceImpl<PoiImagesDao, PoiImagesEntity, PoiImagesDTO> implements PoiImagesService {

    @Override
    public QueryWrapper<PoiImagesEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<PoiImagesEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}