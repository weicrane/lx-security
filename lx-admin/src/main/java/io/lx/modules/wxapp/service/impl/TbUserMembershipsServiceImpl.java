package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbUserMembershipsDao;
import io.lx.modules.wxapp.dto.TbUserMembershipsDTO;
import io.lx.modules.wxapp.entity.TbUserMembershipsEntity;
import io.lx.modules.wxapp.service.TbUserMembershipsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
@Service
public class TbUserMembershipsServiceImpl extends CrudServiceImpl<TbUserMembershipsDao, TbUserMembershipsEntity, TbUserMembershipsDTO> implements TbUserMembershipsService {

    @Override
    public QueryWrapper<TbUserMembershipsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbUserMembershipsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    /**
     * 是否已出售
     * @param guideId
     * @return
     */
    @Override
    public Boolean isSold(Integer guideId){
        QueryWrapper<TbUserMembershipsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("routes_guides_id",guideId);
        List<TbUserMembershipsEntity> list = baseDao.selectList(wrapper);
        if (list.isEmpty()){
            return false;
        }
        return true;
    }


}