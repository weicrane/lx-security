package io.lx.modules.wxapp.dao;

import io.lx.common.dao.BaseDao;
import io.lx.modules.wxapp.entity.TbUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Mapper
public interface TbUserDao extends BaseDao<TbUserEntity> {
	
}