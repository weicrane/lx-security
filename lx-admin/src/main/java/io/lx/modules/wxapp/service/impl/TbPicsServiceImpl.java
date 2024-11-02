package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbPicsDao;
import io.lx.modules.wxapp.dto.TbPicsDTO;
import io.lx.modules.wxapp.entity.TbPicsEntity;
import io.lx.modules.wxapp.service.TbPicsService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Service
public class TbPicsServiceImpl extends CrudServiceImpl<TbPicsDao, TbPicsEntity, TbPicsDTO> implements TbPicsService {

    @Override
    public QueryWrapper<TbPicsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbPicsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public void uploadFile(TbPicsEntity fileRecord){
        insert(fileRecord);
    }

}