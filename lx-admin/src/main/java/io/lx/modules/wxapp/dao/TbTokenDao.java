package io.lx.modules.wxapp.dao;

import io.lx.common.dao.BaseDao;
import io.lx.modules.wxapp.entity.TbTokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Token
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Mapper
public interface TbTokenDao extends BaseDao<TbTokenEntity> {
	
}