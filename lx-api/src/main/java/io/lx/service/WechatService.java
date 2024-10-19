
package io.lx.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import dm.jdbc.util.StringUtil;
import io.lx.common.exception.RenException;
import io.lx.common.utils.OkHttpUtil;
import io.lx.dto.CodeLoginDTO;
import io.lx.dto.UserProfileDTO;
import io.lx.entity.TokenEntity;
import io.lx.entity.UserEntity;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信相关服务
 */
@Service
public class WechatService {
    protected Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private OkHttpUtil okHttpUtil;

    @Resource
    private UserService userService;
    @Resource
    private TokenService tokenService;

    String getPhoneNumberUrl = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=%s";
    String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";

    @Value("${wechat.appid}")
    private String appid;
    @Value("${wechat.secret}")
    private String secret;

    /**
     * 该接口用于将code换取用户手机号。 说明，每个code只能使用一次，code的有效期为5min
     *
     * @param code 授权码
     * @return 手机号
     */
    public String getPhoneNumber(String code) {
        Map<String, String> paramMap = new HashMap<>();
        String accessToken = getAccessToken();
        paramMap.put("code", code);
        log.info("微信api获取手机号请求参数：{}", paramMap);
        String strRes = okHttpUtil.post(String.format(getPhoneNumberUrl, accessToken), JSON.toJSONString(paramMap));
        JSONObject jsonRes = JSONObject.parseObject(strRes);
        JSONObject phoneInfo = jsonRes.getJSONObject("phone_info");
        Integer errorCode = jsonRes.getInteger("errcode");
        if (errorCode != 0) {
            throw new RenException("凭证码已过期，请稍后重试");
        }
        log.info("微信api获取手机号返回结果：{}", jsonRes);
        String phoneNumber = phoneInfo.getString("phoneNumber");
        if (StringUtil.isNotEmpty(phoneNumber)) {
            return phoneNumber;
        } else {
            throw new RenException("获取手机号为空");
        }
    }

    /**
     * 获取小程序全局唯一后台接口调用凭据，token有效期为7200s，开发者需要进行妥善保存
     *
     * @return token
     */
    private String getAccessToken() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("grant_type", "client_credential");
        paramMap.put("appid", appid);
        paramMap.put("secret", secret);
        log.info("微信api获取接口调用凭据请求参数：{}", paramMap);
        String strRes = okHttpUtil.get(getAccessTokenUrl, paramMap);
        JSONObject jsonRes = JSONObject.parseObject(strRes);
        log.info("微信api获取接口调用凭据返回结果：{}", jsonRes);
        return jsonRes.getString("access_token");
    }

    private UserEntity getUser(String phone) {
        return userService.getByMobile(phone);
    }

    /**
     * 1.通过code获取accessToken
     * 2.通过accessToken获取手机号
     * 3.通过手机号判断是否注册，若已注册，直接登录；未注册则添加新用户并登录。
     */
	public Map<String, Object> codeLogin(CodeLoginDTO dto) {
        String phone = getPhoneNumber(dto.getCode());
        UserEntity user = getUser(phone);
        boolean isNewUser = false;
        if (user == null) {
            // 新用户 注册
            UserEntity newUser = new UserEntity();

            newUser.setMobile(phone);
            newUser.setUsername(phone);
            newUser.setCreatedAt(new Date());
            userService.insert(newUser);
            isNewUser = true;

            user = newUser;
        }

        //获取登录token
        TokenEntity tokenEntity = tokenService.createToken(user.getId());

        Map<String, Object> map = new HashMap<>(2);
        map.put("isNewUser",isNewUser);
        map.put("mobile",phone);
        map.put("token", tokenEntity.getToken());
        map.put("expire", tokenEntity.getExpireDate().getTime() - System.currentTimeMillis());

        return map;
    }

/**
 * 1.获取用户信息
 * 2.更新资料
 * */
   public void updateUserProfile(UserProfileDTO dto){
       UserEntity user = getUser(dto.getMobile());
       user.setNickname(dto.getNickname());
       userService.updateById(user);
   }
}
