package io.lx.constant;

/**
 * 常量
 */
public interface ApiConstant {

    //  ==========  strat 微信支付相关 ==========
    String  TRADE_STATE_SUCCESS = "SUCCESS"; // 支付成功
    String  TRADE_STATE_REFUND = "REFUND"; // 转入退款
    String  TRADE_STATE_NOTPAY = "NOTPAY"; // 未支付
    String  TRADE_STATE_CLOSED = "CLOSED"; // 已关闭
    String  TRADE_STATE_REVOKED = "REVOKED"; // 已撤销（付款码支付）
    String  TRADE_STATE_USERPAYING = "USERPAYING"; // 用户支付中（付款码支付）
    String  TRADE_STATE_PAYERROR = "PAYERROR"; // 支付失败(其他原因，如银行返回失败)

    // ============ end 微信支付相关 ==========


    // ============ Start:订单状态:0-未支付，1-已支付，2-支付失败,3-取消支付 ==========
    String ORDER_STATUS_NOTPAY = "0";
    String ORDER_STATUS_SUCCESS = "1";
    String ORDER_STATUS_FAILED = "2";
    String ORDER_STATUS_CANCEL = "3";
// ============ End:订单状态:0-未支付，1-已支付，2-支付失败,3-取消支付 ==========


    // ============ Start:订单类别，00-终身会员，01-网盘路书，02-自驾活动，03-四季玩法 ==========
    String ORDER_TYPE_SVIP = "00";
    String ORDER_TYPE_WANGPAN = "01";
    String ORDER_TYPE_DRIVING = "02";
    String ORDER_TYPE_ROUTES = "03";
    // ============ End:订单类别，00-终身会员，01-网盘路书，02-自驾活动，03-四季玩法 ==========

    // ===== STRAT 常量 ====
    String ZERO_STRING = "0";
    String ONE_STRING = "1";
    // ===== End 常量 ====
}
