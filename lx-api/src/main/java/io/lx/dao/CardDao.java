package io.lx.dao;

import io.lx.common.dao.BaseDao;
import io.lx.entity.CardEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 卡密表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2025-03-16
 */
@Mapper
public interface CardDao extends BaseDao<CardEntity> {
	
}