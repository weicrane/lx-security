/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.lx.service;

import io.lx.common.exception.RenException;
import io.lx.common.service.BaseService;
import io.lx.dto.LoginDTO;
import io.lx.dto.UserDetailDTO;
import io.lx.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 用户
 * 
 * @author Mark sunlightcs@gmail.com
 */
public interface UserService extends BaseService<UserEntity> {

	UserEntity getByMobile(String mobile);

	UserEntity getUserByUserId(Long userId);

	/**
	 * 用户登录
	 * @param dto    登录表单
	 * @return        返回登录信息
	 */
	Map<String, Object> login(LoginDTO dto);

	/**
	 * 上传头像文件
	 * @param file
	 * @return
	 */
	String uploadHeadIcon(MultipartFile file,String token) throws RenException;

	UserDetailDTO getUserInfoDetailByToken(String token);

}
