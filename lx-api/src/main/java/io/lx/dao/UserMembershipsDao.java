package io.lx.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.dao.BaseDao;
import io.lx.dto.MembershipsDTO;
import io.lx.entity.UserMembershipsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
@Mapper
public interface UserMembershipsDao extends BaseDao<UserMembershipsEntity> {

}