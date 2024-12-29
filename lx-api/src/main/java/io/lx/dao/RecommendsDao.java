package io.lx.dao;

import io.lx.common.dao.BaseDao;
import io.lx.entity.RecommendsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 首页推荐
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-12-18
 */
@Mapper
public interface RecommendsDao extends BaseDao<RecommendsEntity> {
	
}