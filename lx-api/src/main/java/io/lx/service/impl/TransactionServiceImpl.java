package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.TransactionDao;
import io.lx.dto.TransactionDTO;
import io.lx.entity.TransactionEntity;
import io.lx.service.TransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-27
 */
@Service
public class TransactionServiceImpl extends CrudServiceImpl<TransactionDao, TransactionEntity, TransactionDTO> implements TransactionService {

    @Override
    public QueryWrapper<TransactionEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TransactionEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public void insertTrans(TransactionDTO transactionDTO)  throws Exception{
        TransactionEntity entity = new TransactionEntity();
        BeanUtils.copyProperties(transactionDTO,entity);

        // 检查是否已有相同主键的记录
        if (baseDao.selectById(entity.getTransactionId()) == null) {
            baseDao.insert(entity);
        } else {
            throw new IllegalStateException("流水已存在："+entity.getTransactionId());
        }
    }

}