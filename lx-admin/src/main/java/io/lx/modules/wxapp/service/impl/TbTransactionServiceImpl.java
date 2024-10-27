package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbTransactionDao;
import io.lx.modules.wxapp.dto.TbTransactionDTO;
import io.lx.modules.wxapp.entity.TbTransactionEntity;
import io.lx.modules.wxapp.service.TbTransactionService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-27
 */
@Service
public class TbTransactionServiceImpl extends CrudServiceImpl<TbTransactionDao, TbTransactionEntity, TbTransactionDTO> implements TbTransactionService {

    @Override
    public QueryWrapper<TbTransactionEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbTransactionEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}