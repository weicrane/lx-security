package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.CardRedeemDao;
import io.lx.dto.CardRedeemDTO;
import io.lx.entity.CardRedeemEntity;
import io.lx.service.CardRedeemService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 卡密兑换记录表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2025-03-16
 */
@Service
public class CardRedeemServiceImpl extends CrudServiceImpl<CardRedeemDao, CardRedeemEntity, CardRedeemDTO> implements CardRedeemService {

    @Override
    public QueryWrapper<CardRedeemEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<CardRedeemEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}