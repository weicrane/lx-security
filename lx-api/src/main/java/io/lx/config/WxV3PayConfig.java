package io.lx.config;

import lombok.Data;

/**
 * 配置
 */
@Data
public class WxV3PayConfig {

    /**
     * 微信回调通知地址
     */
    public static String PAY_BACK_URL = "https://luxunlvyou.com/lx-api/wepay/callback";

    //平台证书序列号
    public static String MCH_SERIAL_NO = "33089AA10813CB4A2DD0AC6D8E7359016563259D";

    //appID
    public static String APP_ID = "wxd94969d7b7e05f1c";

    //商户id
    public static String MCH_ID = "1694622719";

    // API V3密钥
    public static String API_V3_KEY = "2v2k140h7q6fe5clbljx5feor7jcg42g";

    // 商户私钥 打开apiclient_key.pem拷贝出来的字符串
    public static String PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----" +
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDFtG1woRUjAlOz" +
            "tYcDUaFJ/BKvIn69W5yAbatvGnwrN2zJPihrRd1xM/UQHYdqWdMggDWmJeFMpsSF" +
            "fmsLQhkSbJ2iWu2cuTuETAIFMQr0f6jgF+TT0wY+4dPKz4S3+UY2g0x/enjW3S3E" +
            "0B2So/KHacLmUobYXxBEl8KL3/BPP6JLjcyDJXeasgmLPNBdR9pXHn34i0RstcxE" +
            "EYixtwIAEb/vRdqQuHfeIHtZ0IL8UKGxzdOqMhQawBmbK4InnNACe9n8iTTxZtN7" +
            "kAeBidW78iioUX9eyCENXOoybbQprCZ51pZpQYWfmHqjcQZ1Km7fyEDOUEKs4z8A" +
            "Ddch0/89AgMBAAECggEBAJWye1dXsOB0R5qALb22lnClvCP984c/ka2w3Kjgnfo/" +
            "GlD6jrJDEVxA4iMUqM8/OlyWl4i5j/FxKMaHpVe98awAsA8Z2Gbd3pHa14DE1K2y" +
            "59TeYrZ9R3JfnfUn0i9XQPHP0yGHQ241xQ0MJAg/uUkNLLUm8dFtkazOWFF++7+U" +
            "c3juSbDb1yfyVf5IKX+p0sJGx6mTesu2ulGZtKXxnx5IlqTyDjCSW2yQs+A7WsIN" +
            "7LrGMt1HNOxWw1Ikf8pTk7mu+Qf799Zf2MLTObKgvD3ug4ExW2rdHqE5dOkx2x1o" +
            "U+tRJTNAH9EDzGV+NR8hPdzXXPbInile8LmV2T3XTyUCgYEA5QHnQ6aeEYkPbVra" +
            "z+SX5ITe1n5Kpw5Izcx1xSnCXJRUjKRJtuQIwcroAgk98O+3gOcjExYuITKnsE7t" +
            "twcn+QstSCgtyCwjKzDtLiR1DR91cqc98zu9tJeMMUFoXlTFsQ9y3GAXEDOaAoOM" +
            "llWxfExMi5RbltlPuZHrMhm8LYMCgYEA3QH+opPqJ8lfOZLPAq99GLM4YrkbkRb0" +
            "85HnOhRGuqUiISF+Us2ST4mHab7aZdxzqP5IOr+uEqLdVAXj8knz4zuI7wkaCWng" +
            "Q+6Z+Rtn+1sdZzlkQAQlfrnhofHmKJDsnzG1WnB7PyZHVigqAfk7mmeq5GdcgoD2" +
            "DO5d+mB6RD8CgYEAhPAh6+ehAtTpm7WOJ/ivI/ztzt3pQCjOooj6JMYlCdEhc7M7" +
            "B2xAmR7Mp8Htrxu5Dp8egUny5VqedX6cCA+jaLLQSn7odLcKg1wXkx2exUtpxnDH" +
            "SZd4fIks3Lr3dysGx84MA1nUiZcq5CKxTePY8CrqlGw+7safvT2zppXB4zkCgYAj" +
            "eQl84M7pmem93PF7Fd23VDTNq7ZgAFZKrfV2HgEIPi8PMEgNVE+Skpl4gJifVtX9" +
            "nurK+y8ecKOjs9pVbkdGBq1A6K4UKrlDTg3KrPpwnrxGcDDGEaRPBdjQXHo7sp84" +
            "Igtgu8McbLFwg7vSW0WUz+TfCjQQAlD73FqbMFcPkwKBgG9HqgRTC58aCojr635n" +
            "Xzj3JV2yYZlzTPMaGzQarOoJ90INTakRKdfqoIwAePFNgBGf0Xv4cdI+kmroq6VO" +
            "ZP1Uc8Eb+wyndkVmaUUlLN6NYBJHc3wzdi1+FgAuIxdHc9tMarSbZFm5xb4nGS5f" +
            "ygvTErZCFwgvSjSPpaKGKaJP" +
            "-----END PRIVATE KEY-----";

}
