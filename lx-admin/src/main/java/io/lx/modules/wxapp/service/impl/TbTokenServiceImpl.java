package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbTokenDao;
import io.lx.modules.wxapp.dto.TbTokenDTO;
import io.lx.modules.wxapp.entity.TbTokenEntity;
import io.lx.modules.wxapp.service.TbTokenService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户Token
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Service
public class TbTokenServiceImpl extends CrudServiceImpl<TbTokenDao, TbTokenEntity, TbTokenDTO> implements TbTokenService {

    @Override
    public QueryWrapper<TbTokenEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbTokenEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}