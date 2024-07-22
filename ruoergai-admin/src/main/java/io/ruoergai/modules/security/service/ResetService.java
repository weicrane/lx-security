/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.ruoergai.modules.security.service;

import io.ruoergai.common.service.BaseService;
import io.ruoergai.modules.sys.entity.SysUserEntity;

/**
 * 重置密码
 * 
 * @author wyh
 */
public interface ResetService extends BaseService<SysUserEntity> {
	void reset(String username, String passowrd);
}