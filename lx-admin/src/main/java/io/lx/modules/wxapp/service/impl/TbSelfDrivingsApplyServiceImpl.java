package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbSelfDrivingsApplyDao;
import io.lx.modules.wxapp.dto.TbSelfDrivingsApplyDTO;
import io.lx.modules.wxapp.entity.TbSelfDrivingsApplyEntity;
import io.lx.modules.wxapp.service.TbSelfDrivingsApplyService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-29
 */
@Service
public class TbSelfDrivingsApplyServiceImpl extends CrudServiceImpl<TbSelfDrivingsApplyDao, TbSelfDrivingsApplyEntity, TbSelfDrivingsApplyDTO> implements TbSelfDrivingsApplyService {

    @Override
    public QueryWrapper<TbSelfDrivingsApplyEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbSelfDrivingsApplyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}