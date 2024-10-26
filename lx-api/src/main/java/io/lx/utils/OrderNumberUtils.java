package io.lx.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderNumberUtils {
    // 生成订单号：时间戳 + 随机数
    public static String generateOrderNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS"); // 格式化日期
        String timestamp = dateFormat.format(new Date()); // 获取当前时间的时间戳
        int randomNum = new Random().nextInt(900000) + 100000; // 生成6位随机数

        return timestamp + randomNum; // 合并时间戳和随机数
    }
}
