package io.lx.dao;

import io.lx.common.dao.BaseDao;
import io.lx.entity.TransactionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-27
 */
@Mapper
public interface TransactionDao extends BaseDao<TransactionEntity> {
	
}