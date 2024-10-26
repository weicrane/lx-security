package io.lx.utils;

import java.security.SecureRandom;

public class RandomUtils {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成指定长度的随机字符串
     *
     * @param length 随机字符串的长度
     * @return 生成的随机字符串
     */
    public static String randomEles(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }
        return result.toString();
    }

//    public static void main(String[] args) {
//        String nonceStr = RandomUtils.randomEles(30);
//        System.out.println("随机字符串: " + nonceStr);
//    }
}
