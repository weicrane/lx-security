package io.lx.dao;

import io.lx.common.dao.BaseDao;
import io.lx.entity.OrdersEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-26
 */
@Mapper
public interface OrdersDao extends BaseDao<OrdersEntity> {
	
}