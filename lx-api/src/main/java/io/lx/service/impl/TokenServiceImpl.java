/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.lx.service.impl;

import io.lx.common.service.impl.BaseServiceImpl;
import io.lx.dao.TokenDao;
import io.lx.entity.TokenEntity;
import io.lx.interceptor.AuthorizationInterceptor;
import io.lx.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;


@Service
public class TokenServiceImpl extends BaseServiceImpl<TokenDao, TokenEntity> implements TokenService {
	/**
	 * 24小时后过期*30天，注意：将常量EXPIRE的单位修改为毫秒
	 */
	private final static long EXPIRE = 3600 * 24 * 30 * 1000L;  // 30天，单位为毫秒

	private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);
	@Override
	public TokenEntity getByToken(String token) {
		return baseDao.getByToken(token);
	}
	@Override
	public TokenEntity createToken(Long userId) {
		// 当前时间
		Date now = new Date();
		// 过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE);  // 直接加上毫秒数

		// 使用 SimpleDateFormat 格式化过期时间，确保输出的时间不会受时区影响
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8")); // 设置为北京时间
		String formattedExpireTime = sdf.format(expireTime);

		logger.info("过期时间:{}", formattedExpireTime);

		// 用户token
		String token;

		// 判断是否生成过token
		TokenEntity tokenEntity = baseDao.getByUserId(userId);
		if (tokenEntity == null) {
			// 生成一个token
			token = generateToken();

			tokenEntity = new TokenEntity();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateDate(now);
			tokenEntity.setExpireDate(expireTime);

			// 保存token
			this.insert(tokenEntity);
		} else {
			// 判断token是否过期
			if (tokenEntity.getExpireDate().getTime() < System.currentTimeMillis()) {
				// token过期，重新生成token
				token = generateToken();
			} else {
				token = tokenEntity.getToken();
			}

			tokenEntity.setToken(token);
			tokenEntity.setUpdateDate(now);
			tokenEntity.setExpireDate(expireTime);

			// 更新token
			this.updateById(tokenEntity);
		}

		return tokenEntity;
	}

	@Override
	public void expireToken(Long userId){
		Date now = new Date();

		TokenEntity tokenEntity = new TokenEntity();
		tokenEntity.setUserId(userId);
		tokenEntity.setUpdateDate(now);
		tokenEntity.setExpireDate(now);

		this.updateById(tokenEntity);
	}

	private String generateToken(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
