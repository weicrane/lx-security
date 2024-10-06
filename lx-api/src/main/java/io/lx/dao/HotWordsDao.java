package io.lx.dao;

import io.lx.common.dao.BaseDao;
import io.lx.entity.HotWordsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 热词
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Mapper
public interface HotWordsDao extends BaseDao<HotWordsEntity> {

}