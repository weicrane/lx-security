

package io.lx.service;

import org.springframework.beans.factory.annotation.Value;


/**
 * 微信相关服务
 */
public class WechatService {
	String getPhoneNumberUrl = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=%s";
	String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";

	@Value("${wechat.appid}")
	private String appid;
	@Value("${wechat.secret}")
	private String secret;



}
