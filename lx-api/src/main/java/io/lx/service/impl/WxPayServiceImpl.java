package io.lx.service.impl;

import com.alibaba.fastjson.JSON;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.core.util.PemUtil;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import com.wechat.pay.java.service.payments.model.Transaction;
import io.lx.common.exception.RenException;
import io.lx.config.WxV3PayConfig;
import io.lx.dto.OrdersDTO;
import io.lx.dto.UserDetailDTO;
import io.lx.dto.WechatPaySign;
import io.lx.service.IWxPayService;
import io.lx.service.OrdersService;
import io.lx.service.UserService;
import io.lx.utils.OrderNumberUtils;
import io.lx.utils.RandomUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * @author
 */
@Slf4j
@Service
public class WxPayServiceImpl implements IWxPayService {
    @Resource
    UserService userService;

    @Resource
    OrdersService ordersService;

    /**
     * 创建微信支付订单
     */
    @Override
    @Transactional
    public SortedMap<String, String> jsApiOrder(OrdersDTO ordersDTO ,String token) throws Exception {
        log.info("微信支付 >>>>>>>>>>>>>>>>> 金额：{}元", ordersDTO.getAmount());
        // 1.核实商品金额
        Amount amount = ordersService.getAmount(ordersDTO.getProductType(),ordersDTO.getProductId());
        Integer price = ordersDTO.getAmount().multiply(BigDecimal.valueOf(100)).intValue();
        if (!amount.getTotal().equals(price)){
            throw new RenException("价格异常：小程序商品价格与后台不一致");
        }

        // 2.获取用户信息
        UserDetailDTO userDetailDTO =userService.getUserInfoDetailByToken(token);
        if (userDetailDTO.getOpenid()==null){
            throw new RenException("用户openid缺失");
        }

        // 3.生成预支付订单参数
        PrepayRequest request = new PrepayRequest();
        SortedMap<String, String> params = new TreeMap<>();
        Payer payer = new Payer();
        payer.setOpenid(userDetailDTO.getOpenid()); // openid
        request.setAmount(amount); // 金额
        request.setPayer(payer);
        request.setAppid(WxV3PayConfig.APP_ID); // appid
        request.setMchid(WxV3PayConfig.MCH_ID); // 商户id
        String des = ordersDTO.getDescription();
        request.setDescription(des.length() > 125 ? des.substring(0, 125) : des); // 商品描述

        request.setNotifyUrl(WxV3PayConfig.PAY_BACK_URL); // 回调地址

        // 生成订单号
        String outTradeNo = OrderNumberUtils.generateOrderNumber();
        request.setOutTradeNo(outTradeNo); // 商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一。
        log.info("商户订单号 >>>>>>>>>>>>>>>>> 订单号：{}", outTradeNo);
        PrepayResponse response = getJsapiService().prepay(request);

        WechatPaySign sign = sign(response.getPrepayId());
        params.put("trans_no", outTradeNo);// 订单号(业务需要）
        params.put("appId", WxV3PayConfig.APP_ID);
        params.put("nonceStr", sign.getNonceStr());
        params.put("package", "prepay_id=" + sign.getPrepayId());
        params.put("signType", "RSA");
        params.put("timeStamp", sign.getTimeStamp());
        params.put("paySign", sign.getSign());

        // 写入预支付订单
        OrdersDTO dto = new OrdersDTO();
        BeanUtils.copyProperties(ordersDTO,dto);
        dto.setOrderId(outTradeNo); // 订单号
        dto.setUserId(userDetailDTO.getId()); //用户id
        dto.setOpenid(userDetailDTO.getOpenid()); //openid
        dto.setStatus("0"); //订单状态:0-未支付，1-已支付，2-支付失败
        ordersService.creatOrder(dto);

        return params;
    }

    /**
     * 读取请求体并以 UTF-8 编码返回字符串
     * @param request
     * @return
     */
    String getBodyUTF8(HttpServletRequest request) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 微信回调
     *
     * @param request
     * @return
     * @throws IOException
     */
    @Transactional
    public ResponseEntity callback(HttpServletRequest request) throws IOException {
        log.info("微信回调v3 >>>>>>>>>>>>>>>>> ");
        RequestParam requestParam = new RequestParam.Builder()
                .serialNumber(request.getHeader("Wechatpay-Serial"))
                .nonce(request.getHeader("Wechatpay-Nonce"))
                .timestamp(request.getHeader("Wechatpay-Timestamp"))
                .signature(request.getHeader("Wechatpay-Signature"))
                .body(getBodyUTF8(request))
                .build();

        NotificationConfig config = new RSAAutoCertificateConfig.Builder()
                .merchantId(WxV3PayConfig.MCH_ID)
                .privateKey(WxV3PayConfig.PRIVATE_KEY)
                .merchantSerialNumber(WxV3PayConfig.MCH_SERIAL_NO)
                .apiV3Key(WxV3PayConfig.API_V3_KEY)
                .build();

        NotificationParser parser = new NotificationParser(config);

        try {
            // 验签、解密并转换成 Transaction（返回参数对象）
            Transaction transaction = parser.parse(requestParam, Transaction.class);
            log.info("微信支付回调 成功，解析" + JSON.toJSONString(transaction));
            // TODO 更新订单账单
            // TODO 写入支付表
            // TODO 更新用户会员身份

            // 处理成功，返回 200 OK 状态码
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ValidationException e) {
            log.error("sign verification failed", e);
            log.error("微信支付回调v3java失败=" + e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 关闭微信支付订单
     */
    @Override
    public void closePay(String outTradeNo) {
        CloseOrderRequest closeRequest = new CloseOrderRequest();
        closeRequest.setMchid(WxV3PayConfig.MCH_ID);
        closeRequest.setOutTradeNo(outTradeNo);
        getJsapiService().closeOrder(closeRequest);
    }

    @Override
    public Transaction queryWxPayOrderOutTradeNo(String transNo) {
        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(WxV3PayConfig.MCH_ID);
        request.setOutTradeNo(transNo);
        Transaction transaction = getJsapiService().queryOrderByOutTradeNo(request);
        // TODO 处理你的业务逻辑
        return transaction;
    }


    /**
     * 创建小程序支付服务
     *
     * @return
     */
    protected JsapiService getJsapiService() {
        Config config =
                new RSAAutoCertificateConfig.Builder()
                        .merchantId(WxV3PayConfig.MCH_ID)
                        .privateKey(WxV3PayConfig.PRIVATE_KEY)
                        .merchantSerialNumber(WxV3PayConfig.MCH_SERIAL_NO)
                        .apiV3Key(WxV3PayConfig.API_V3_KEY)
                        .build();
        config.createSigner().getAlgorithm();
        return new JsapiService.Builder().config(config).build();
    }



    /**
     * 获取支付签名
     *
     * @param prepayId
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static WechatPaySign sign(String prepayId) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = RandomUtils.randomEles(30);//随机字符串
                String packageStr = "prepay_id=" + prepayId;

        // 不能去除'.append("\n")'，否则失败
        String signStr = WxV3PayConfig.APP_ID + "\n" +
                timeStamp + "\n" +
                nonceStr + "\n" +
                packageStr + "\n";

        byte[] message = signStr.getBytes(StandardCharsets.UTF_8);

        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(PemUtil.loadPrivateKeyFromString(WxV3PayConfig.PRIVATE_KEY));
        sign.update(message);
        String signStrBase64 = Base64.getEncoder().encodeToString(sign.sign());

        WechatPaySign wechatPaySign = new WechatPaySign();
        wechatPaySign.setPrepayId(prepayId);
        wechatPaySign.setTimeStamp(timeStamp);
        wechatPaySign.setNonceStr(nonceStr);
        wechatPaySign.setPackageStr(packageStr);
        wechatPaySign.setSign(signStrBase64);
        return wechatPaySign;
    }
}
