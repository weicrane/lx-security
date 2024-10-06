package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbUserDao;
import io.lx.modules.wxapp.dto.TbUserDTO;
import io.lx.modules.wxapp.entity.TbUserEntity;
import io.lx.modules.wxapp.service.TbUserService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户信息表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Service
public class TbUserServiceImpl extends CrudServiceImpl<TbUserDao, TbUserEntity, TbUserDTO> implements TbUserService {

    @Override
    public QueryWrapper<TbUserEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbUserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}