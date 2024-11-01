package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbPartnersApplyDao;
import io.lx.modules.wxapp.dto.TbPartnersApplyDTO;
import io.lx.modules.wxapp.entity.TbPartnersApplyEntity;
import io.lx.modules.wxapp.service.TbPartnersApplyService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-31
 */
@Service
public class TbPartnersApplyServiceImpl extends CrudServiceImpl<TbPartnersApplyDao, TbPartnersApplyEntity, TbPartnersApplyDTO> implements TbPartnersApplyService {

    @Override
    public QueryWrapper<TbPartnersApplyEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbPartnersApplyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}