package com.trendhu.common.util.id;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * ID生成工具
 * 
 * 
 */
public class IDUtil {
    private final static long START_LONG_ID = 1507305600000l;

    /**
     * 这里生成时间码的公式是： id=在一个时间点后的毫秒值*1000+nanotime的后三位
     */
    public static long generateTimedID() {
        return (System.currentTimeMillis() - START_LONG_ID) * 1000 + System.nanoTime() % 1000;
    }

    public static String generateTimedIDStr() {
        return generateTimedID() + "";
    }

    /**
     * 这个使用apache的生成random的方式生成，冲突率更小
     */
    public static String generateRandomKey(int count) {
        return RandomStringUtils.random(count, true, true);
    }

    public static String generateRandomKey() {
        return generateRandomKey(9);
    }

    /**
     * 生成包含时间信息的随机id号（传入位数） 生成方法：14位时间信息 + 4位随机数
     */
    public static String generateTimeRandomIdStr(int count) {
        // 生成时间 年月日时分秒
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeString = sdf.format(dt);

        int nanoLen = (count - 14) / 2;
        Long nano = System.nanoTime() % (int) Math.pow(10, (count - 14) / 2);
        String nanoString = "" + nano;
        // 如果类型位数少于位数补0
        int strLen = nanoString.length();
        if (strLen < nanoLen) {
            while (strLen < nanoLen) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(nanoString);// 左补0
                nanoString = sb.toString();
                strLen = nanoString.length();
            }
        }

        Integer random = new Random().nextInt((int) Math.pow(10, count - 14 - nanoLen));
        String randomString = "" + random;
        // 如果类型位数少于位数补0
        int randomLen = randomString.length();
        if (randomLen < (count - 14 - nanoLen)) {
            while (randomLen < (count - 14 - nanoLen)) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(randomString);// 左补0
                randomString = sb.toString();
                randomLen = randomString.length();
            }
        }

        return timeString + nanoString + randomString;
    }

    /**
     * 生成文章的id号，输入参数是作者的id号 格式是用户id号后四位+时间标志
     */
    public static String generateArticleIdStr(String authorId) {
        StringBuffer sb = new StringBuffer();
        if (authorId.length() < 4) {
            sb.append(authorId);
        } else {
            sb.append(authorId.substring(authorId.length() - 4));
        }
        sb.append(generateTimedID());
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(System.nanoTime() % 1000);
        System.out.println(System.currentTimeMillis());
        System.out.println(generateTimedID());
    }
}
