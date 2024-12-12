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
import io.lx.dto.*;
import io.lx.entity.OrdersEntity;
import io.lx.service.*;
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
import org.springframework.util.StringUtils;

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

import static io.lx.constant.ApiConstant.*;


/**
 * @author
 */
@Slf4j
@Service
public class WxPayServiceImpl implements WxPayService {
    @Resource
    UserService userService;
    @Resource
    OrdersService ordersService;
    @Resource
    TransactionService transactionService;
    @Resource
    UserMembershipsService  userMembershipsService;
    @Resource
    TravelGuidesService travelGuidesService;
    @Resource
    SvipService svipService;
    @Resource
    RoutesGuidesService routesGuidesService;
    @Resource
    SelfDrivingsService selfDrivingsService;
    @Resource
    SelfDrivingsApplyService selfDrivingsApplyService;
    /**
     * 创建微信支付订单
     */
    @Override
    @Transactional
    public SortedMap<String, String> jsApiOrder(OrdersDTO ordersDTO ,String token) throws Exception {

        // 生成预支付订单参数
        PrepayRequest request = new PrepayRequest();
        request.setAppid(WxV3PayConfig.APP_ID); // appid
        request.setMchid(WxV3PayConfig.MCH_ID); // 商户id
        request.setNotifyUrl(WxV3PayConfig.PAY_BACK_URL); // 回调地址

        SortedMap<String, String> params = new TreeMap<>();
        params.put("appId", WxV3PayConfig.APP_ID);

        Payer payer = new Payer();

        // 检查是否存在订单号，分为有订单号和无订单号的情况
        if (StringUtils.hasText(ordersDTO.getOrderId())) {
            // 查询订单详情
            OrdersEntity orderDetail = ordersService.getOrderDetail(ordersDTO.getOrderId());
            if (orderDetail==null){
                throw new RenException("订单记录为空");
            }
            if (ORDER_STATUS_SUCCESS.equals(orderDetail.getStatus())){
                throw new RenException("订单已完成，请勿重复支付");
            }
            if (ORDER_STATUS_CANCEL.equals(orderDetail.getStatus())){
                throw new RenException("订单已取消，请重新下单");
            }
            payer.setOpenid(orderDetail.getOpenid()); // openid

            BigDecimal price = orderDetail.getAmount();
            Amount amount = new Amount();
            amount.setTotal(price.multiply(BigDecimal.valueOf(100)).intValue());
            request.setAmount(amount); // 金额

            request.setDescription(orderDetail.getDescription());//商品描述

            request.setOutTradeNo(orderDetail.getOrderId());//订单号
            params.put("trans_no", orderDetail.getOrderId());// 订单号(业务需要）

        } else {
            // 处理无订单号的逻辑
            log.info("微信支付 >>>>>>>>>>>>>>>>> 金额：{}元", ordersDTO.getAmount());
            // 1.核实商品金额
            Integer num1 = ORDER_TYPE_DRIVING.equals(ordersDTO.getProductType()) && ordersDTO.getNum1() != null ? ordersDTO.getNum1() : 0;
            Integer num2 = ORDER_TYPE_DRIVING.equals(ordersDTO.getProductType()) && ordersDTO.getNum2() != null ? ordersDTO.getNum2() : 0;
            Integer num3 = ORDER_TYPE_DRIVING.equals(ordersDTO.getProductType()) && ordersDTO.getNum3() != null ? ordersDTO.getNum3() : 0;

            Amount amount = getAmount(ordersDTO.getProductType(),ordersDTO.getProductId(),num1,num2,num3);
            Integer price = ordersDTO.getAmount().multiply(BigDecimal.valueOf(100)).intValue();
            if (!amount.getTotal().equals(price)){
                throw new RenException("价格异常：小程序商品价格与后台不一致");
            }
            // 2.获取用户信息
            UserDetailDTO userDetailDTO =userService.getUserInfoDetailByToken(token);
            if (userDetailDTO.getOpenid()==null){
                throw new RenException("用户openid缺失");
            }
            // 3.写入参数
            payer.setOpenid(userDetailDTO.getOpenid()); // openid
            request.setAmount(amount); // 金额
            String des = ordersDTO.getDescription();
            request.setDescription(des.length() > 125 ? des.substring(0, 125) : des); // 商品描述
            // 4.生成订单号
            String outTradeNo = OrderNumberUtils.generateOrderNumber();
            request.setOutTradeNo(outTradeNo); // 商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一。
            params.put("trans_no", outTradeNo);// 订单号(业务需要）

            log.info("商户订单号 >>>>>>>>>>>>>>>>> 订单号：{}", outTradeNo);
            // 5.写入预支付订单
            OrdersDTO dto = new OrdersDTO();
            BeanUtils.copyProperties(ordersDTO,dto);
            dto.setOrderId(outTradeNo); // 订单号
            dto.setUserId(userDetailDTO.getId()); //用户id
            dto.setOpenid(userDetailDTO.getOpenid()); //openid
            dto.setStatus("0"); //订单状态:0-未支付，1-已支付，2-支付失败，3-取消支付
            ordersService.creatOrder(dto);

        }

        request.setPayer(payer);

        PrepayResponse response = getJsapiService().prepay(request);

        WechatPaySign sign = sign(response.getPrepayId());

        params.put("nonceStr", sign.getNonceStr());
        params.put("package", "prepay_id=" + sign.getPrepayId());
        params.put("signType", "RSA");
        params.put("timeStamp", sign.getTimeStamp());
        params.put("paySign", sign.getSign());

        return params;
    }

    /**
     * 创建自驾支付订单
     * @param ordersDTO
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public SortedMap<String, String> createSelfDrivingOrder(OrdersDTO ordersDTO ,String token) throws Exception {

        // 生成预支付订单参数
        PrepayRequest request = new PrepayRequest();
        request.setAppid(WxV3PayConfig.APP_ID); // appid
        request.setMchid(WxV3PayConfig.MCH_ID); // 商户id
        request.setNotifyUrl(WxV3PayConfig.PAY_BACK_URL); // 回调地址

        SortedMap<String, String> params = new TreeMap<>();
        params.put("appId", WxV3PayConfig.APP_ID);

        Payer payer = new Payer();

        OrdersEntity orderDetail = ordersService.getOrderDetail(ordersDTO.getOrderId());

        // 检查是否存在订单情况
        if (orderDetail!=null) {
            if (ORDER_STATUS_SUCCESS.equals(orderDetail.getStatus())){
                throw new RenException("订单已完成，请勿重复支付");
            }
            if (ORDER_STATUS_CANCEL.equals(orderDetail.getStatus())){
                throw new RenException("订单已取消，请重新下单");
            }
            payer.setOpenid(orderDetail.getOpenid()); // openid

            BigDecimal price = orderDetail.getAmount();
            Amount amount = new Amount();
            amount.setTotal(price.multiply(BigDecimal.valueOf(100)).intValue());
            request.setAmount(amount); // 金额

            request.setDescription(orderDetail.getDescription());//商品描述

            request.setOutTradeNo(orderDetail.getOrderId());//订单号
            params.put("trans_no", orderDetail.getOrderId());// 订单号(业务需要）

        } else {
            // 处理无订单号的逻辑
            log.info("微信支付 >>>>>>>>>>>>>>>>> 金额：{}元", ordersDTO.getAmount());
            // 1.核实商品金额
            Integer num1 = ORDER_TYPE_DRIVING.equals(ordersDTO.getProductType()) && ordersDTO.getNum1() != null ? ordersDTO.getNum1() : 0;
            Integer num2 = ORDER_TYPE_DRIVING.equals(ordersDTO.getProductType()) && ordersDTO.getNum2() != null ? ordersDTO.getNum2() : 0;
            Integer num3 = ORDER_TYPE_DRIVING.equals(ordersDTO.getProductType()) && ordersDTO.getNum3() != null ? ordersDTO.getNum3() : 0;

            Amount amount = getAmount(ordersDTO.getProductType(),ordersDTO.getProductId(),num1,num2,num3);
            Integer price = ordersDTO.getAmount().multiply(BigDecimal.valueOf(100)).intValue();
            if (!amount.getTotal().equals(price)){
                throw new RenException("价格异常：小程序商品价格与后台不一致");
            }
            // 2.获取用户信息
            UserDetailDTO userDetailDTO =userService.getUserInfoDetailByToken(token);
            if (userDetailDTO.getOpenid()==null){
                throw new RenException("用户openid缺失");
            }
            // 3.写入参数
            payer.setOpenid(userDetailDTO.getOpenid()); // openid
            request.setAmount(amount); // 金额
            String des = ordersDTO.getDescription();
            request.setDescription(des.length() > 125 ? des.substring(0, 125) : des); // 商品描述
            // 4.生成订单号
            String outTradeNo = ordersDTO.getOrderId();
            request.setOutTradeNo(outTradeNo); // 商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一。
            params.put("trans_no", outTradeNo);// 订单号(业务需要）

            log.info("商户订单号 >>>>>>>>>>>>>>>>> 订单号：{}", outTradeNo);
            // 5.写入预支付订单
            OrdersDTO dto = new OrdersDTO();
            BeanUtils.copyProperties(ordersDTO,dto);
            dto.setOrderId(outTradeNo); // 订单号
            dto.setUserId(userDetailDTO.getId()); //用户id
            dto.setOpenid(userDetailDTO.getOpenid()); //openid
            dto.setStatus("0"); //订单状态:0-未支付，1-已支付，2-支付失败，3-取消支付
            ordersService.creatOrder(dto);

        }

        request.setPayer(payer);

        PrepayResponse response = getJsapiService().prepay(request);

        WechatPaySign sign = sign(response.getPrepayId());

        params.put("nonceStr", sign.getNonceStr());
        params.put("package", "prepay_id=" + sign.getPrepayId());
        params.put("signType", "RSA");
        params.put("timeStamp", sign.getTimeStamp());
        params.put("paySign", sign.getSign());

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
        log.info("微信回调v3 >>>>>>>>>>>>>>>>> 微信回调报文{}",request);
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
            String orderId = transaction.getOutTradeNo();
            String tradeState = String.valueOf(transaction.getTradeState());
            switch (tradeState) {
                case TRADE_STATE_SUCCESS:
                    // 支付成功
                    // 更新订单表
                    if (!ORDER_STATUS_SUCCESS.equals(ordersService.getOederStatus(orderId))){
                        ordersService.updateOrderStatus(transaction.getOutTradeNo(),ORDER_STATUS_SUCCESS);
                    }
                    System.out.println("微信支付成功,更新订单："+orderId);
                    // 更新用户会员身份、报名状态等
                    updateUserMemShips(orderId);
                    System.out.println("更新用户会员身份完成");
                    break;
                case TRADE_STATE_CLOSED:
                    // 已关闭的处理逻辑
                    if (!ORDER_STATUS_CANCEL.equals(ordersService.getOederStatus(orderId))){
                        ordersService.updateOrderStatus(transaction.getOutTradeNo(),ORDER_STATUS_CANCEL);
                    }
                    System.out.println("订单已关闭:"+orderId);
                    break;
                case TRADE_STATE_PAYERROR:
                    // 支付失败的处理逻辑
                    if (!ORDER_STATUS_FAILED.equals(ordersService.getOederStatus(orderId))){
                        ordersService.updateOrderStatus(transaction.getOutTradeNo(),ORDER_STATUS_FAILED);
                    }
                    System.out.println("支付失败:"+orderId);
                    break;
            }

            // TODO 写入交易流水表
            TransactionDTO transactionDTO = new TransactionDTO();
            BeanUtils.copyProperties(transaction,transactionDTO);
            try {
                transactionService.insertTrans(transactionDTO);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

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
        // 取消微信支付
        CloseOrderRequest closeRequest = new CloseOrderRequest();
        closeRequest.setMchid(WxV3PayConfig.MCH_ID);
        closeRequest.setOutTradeNo(outTradeNo);
        getJsapiService().closeOrder(closeRequest);
        // 更新订单表
        ordersService.updateOrderStatus(outTradeNo,ORDER_STATUS_CANCEL);
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

    /**
     * 获取产品价格 单位：分
     * 00-终身会员，01-网盘路书，a02-自驾活动，03-四季玩法
     */
    public Amount getAmount(String productType, Integer productId,Integer num1,Integer num2,Integer num3){
        Amount amount = new Amount();
        switch (productType) {
            case "00":
                // 处理终身会员的逻辑
                // 查询价格
                try {
                    BigDecimal svipPrice = svipService.getSvipPrice();
                    amount.setTotal(svipPrice.multiply(BigDecimal.valueOf(100)).intValue());
                }catch (Exception e){
                    throw new RenException("查询会员价格失败");
                }
                break;

            case "01":
                // 处理网盘路书的逻辑
                TravelGuidesDTO dto = travelGuidesService.getTravelGuidesDetail(productId);
                try {
                    BigDecimal price = dto.getPrice();
                    amount.setTotal(price.multiply(BigDecimal.valueOf(100)).intValue());
                }catch (Exception e){
                    throw new RenException("查询商品价格失败");
                }
                break;

            case "02":
                // 处理自驾活动的逻辑
                try {
                    BigDecimal totalAmount = selfDrivingsService.calculateTotalAmount(num1,num2,num3,productId);
                    amount.setTotal(totalAmount.multiply(BigDecimal.valueOf(100)).intValue());
                }catch (Exception e){
                    throw new RenException("查询活动价格失败");
                }
                break;

            case "03":
                // 处理四季玩法的逻辑
                RoutesGuidesDTO rdto = routesGuidesService.getRoutesGuidesDetail(productId);
                try {
                    BigDecimal price = rdto.getPrice();
                    amount.setTotal(price.multiply(BigDecimal.valueOf(100)).intValue());
                }catch (Exception e){
                    throw new RenException("查询商品价格失败");
                }
                break;

            default:
                // 处理其他情况的逻辑
                break;
        }
        return amount;
    }

    /**
     * 更新用户会员
     * @param orderId
     */
    public void updateUserMemShips(String orderId) {
        // 1.查询订单详情
        OrdersEntity orderEntity = ordersService.getOrderDetail(orderId);
        // 2.判断开通的会员订单类型
        String productType = orderEntity.getProductType();
        switch (productType){
            case ORDER_TYPE_SVIP:
                // case00:终身会员,写入表
                userService.setSvip(orderEntity.getUserId());
                break;
            case ORDER_TYPE_DRIVING:
                // case02:自驾活动
                selfDrivingsApplyService.updatePayStatus(orderEntity.getOrderId(),ORDER_STATUS_SUCCESS);
                break;
            default:
                // 处理其他情况的逻辑
                // case:其他会员,写入表
                userMembershipsService.updateUserMemShips(orderId);
                break;
        }

        // 3.其他情况
    }


}
