package cn.wolfcode.utils;

import java.util.Random;
/**
 * @author wby
 * @version 1.0
 * @date 2022/12/17 17:16
 */
public class VerifyCodeUtils {

    public static String genCode(int len){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < len; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
