package cn.wolfcode.utils;

import cn.wolfcode.exception.ServiceException;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/13 20:10
 * 断言工具类
 */
public class AssertUtils {

    public static void isTrue(Boolean ret, String msg) {
        if (!ret) {
            throw new ServiceException(msg);
        }
    }

    public static void notNull(Object obj, String msg) {
        if (obj == null) {
            throw new ServiceException(msg);
        }
    }
}
