
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
import io.lx.utils.WeChatUtils;
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
//    private String getAccessToken() {
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("grant_type", "client_credential");
//        paramMap.put("appid", appid);
//        paramMap.put("secret", secret);
//        log.info("微信api获取接口调用凭据请求参数：{}", paramMap);
//        String strRes = okHttpUtil.get(getAccessTokenUrl, paramMap);
//        JSONObject jsonRes = JSONObject.parseObject(strRes);
//        log.info("微信api获取接口调用凭据返回结果：{}", jsonRes);
//        return jsonRes.getString("access_token");
//    }

    /**
     * 获取小程序全局唯一后台接口调用凭据，token有效期为7200s，开发者需要进行妥善保存
     *
     * @return token
     */
    private String getAccessToken() throws RenException{
        // 重试次数
        int retryCount = 3;
        String accessToken = null;

        // 尝试获取 accessToken，最多重试 3 次
        for (int i = 0; i < retryCount; i++) {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("grant_type", "client_credential");
            paramMap.put("appid", appid);
            paramMap.put("secret", secret);
            log.info("微信API获取接口调用凭据请求参数：{}", paramMap);

            try {
                String strRes = okHttpUtil.get(getAccessTokenUrl, paramMap);
                JSONObject jsonRes = JSONObject.parseObject(strRes);

                log.info("微信API获取接口调用凭据返回结果：{}", jsonRes);

                // 检查返回结果是否有效
                if (jsonRes != null && jsonRes.containsKey("access_token")) {
                    accessToken = jsonRes.getString("access_token");
                    break;  // 成功获取 accessToken，跳出重试循环
                } else {
                    log.error("获取微信API凭据失败，响应为空或缺少 access_token，重试次数：{}", i + 1);
                }
            } catch (Exception e) {
                log.error("请求微信API失败，重试次数：{}", i + 1, e);
            }

            // 如果是最后一次重试且仍然没有获取到 token，则返回空
            if (i == retryCount - 1) {
                log.error("获取微信API凭据失败，已达到最大重试次数：{}", retryCount);
            }

            // 失败后等待一定时间再重试，防止频繁请求
            try {
                Thread.sleep(500);  // 延迟0.5秒后重试
            } catch (InterruptedException e) {
                log.error("线程睡眠中断", e);
            }
        }

        return accessToken;
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
            newUser.setOpenid(dto.getOpenid());
            userService.insert(newUser);
            isNewUser = true;

            user = newUser;
        }else {
            // 更新openid 一般不变
            user.setOpenid(dto.getOpenid());
            userService.updateById(user);
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

    /**
     * 获取openid
     */
    public Map<String, Object> getOpenId(String code){
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
    // 发送请求，返回Json字符串
        String str = WeChatUtils.httpRequest(url, "GET", null);
        // 转成Json对象 获取openid
        JSONObject jsonObject = JSONObject.parseObject(str);
        // 我们需要的openid，在一个小程序中，openid是唯一的
        try {
            String openid = jsonObject.get("openid").toString();
        }catch (Exception e){
            if ("40029".equals(jsonObject.get("errcode"))){
                throw new RenException("40029,获取openid失败");
            }
            throw new RenException("获取openid失败");
        }
        String openid = jsonObject.get("openid").toString();
        Map<String, Object> map = new HashMap<>();
        map.put("openid",openid);
        return map;
    }
}
