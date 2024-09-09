/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.lx.modules.sys.dao;

import io.lx.common.dao.BaseDao;
import io.lx.modules.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色管理
 * 
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysRoleDao extends BaseDao<SysRoleEntity> {
	

}
