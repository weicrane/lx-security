/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.security.service;

import io.renren.common.service.BaseService;
import io.renren.modules.sys.entity.SysUserEntity;

/**
 * 注册
 * 
 * @author wyh
 */
public interface RegistService extends BaseService<SysUserEntity> {
	void regist(String username, String passowrd, String mobile,String realname);
}