package cn.wolfcode.vo;

import cn.wolfcode.constants.HttpStatus;
import cn.wolfcode.exception.Wolf2wException;
import cn.wolfcode.utils.AssertUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 统一结果响应对象
 *
 * @author wby
 * @version 1.0
 * @date 2022/12/13 15:26
 * @param<T> 响应 data 属性数据类型
 */
public class R<T> extends HashMap<String, Object> {

    public static final Integer DEFAULT_SUCCESS_CODE = HttpStatus.OK;
    public static final String DEFAULT_SUCCESS_MSG = "success";

    public static final Integer DEFAULT_FAILED_CODE = HttpStatus.SERVER_ERROR;
    public static final String DEFAULT_FAILED_MSG = "error";

    public static final String CODE_NAME = "code";
    public static final String MSG_NAME = "msg";
    public static final String DATA_NAME = "data";

    public R() {
    }

    public R(Integer code, String msg, T data) {
        super.put(CODE_NAME, code);
        super.put(MSG_NAME, msg);
        // 当 data 没有数据的时候, 就没有属性
        // 完整数据结构: {code: 200, msg: 'xxx', data: {xxxx}}
        // 没有 data: {code: 200, msg: 'xxx'}
        if (data != null) {
            super.put(DATA_NAME, data);
        }
    }

    public R<?> put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public R(Integer code, String msg) {
        this(code, msg, null);
    }

    public static <T> R<T> ok(String msg, T data) {
        return new R<>(DEFAULT_SUCCESS_CODE, msg, data);
    }

    public static <T> R<T> ok(T data) {
        return ok(DEFAULT_SUCCESS_MSG, data);
    }

    public static <T> R<T> ok(String msg) {
        return ok(msg, null);
    }

    public static <T> R<T> ok() {
        return ok(DEFAULT_SUCCESS_MSG);
    }

    public static <T> R<T> err(Integer code, String msg, T data) {
        return new R<>(code, msg, data);
    }

    public static <T> R<T> err(Integer code, String msg) {
        return err(code, msg, null);
    }

    public static <T> R<T> err(String msg) {
        return err(DEFAULT_FAILED_CODE, msg);
    }

    public static <T> R<T> err(Wolf2wException ex) {
        return err(ex.getCode(), ex.getMessage());
    }

    public static <T> R<T> err() {
        return err(DEFAULT_FAILED_MSG);
    }

    public static R<?> map() {
        return new R<>();
    }

    public boolean hasError() {
        // 是否有错误 == 状态码是否等于200
        return !DEFAULT_SUCCESS_CODE.equals(getCode());
    }

    public Integer getCode() {
        return (Integer) super.get(CODE_NAME);
    }

    public String getMsg() {
        return (String) super.get(MSG_NAME);
    }

    public T getData() {
        return (T) super.get(DATA_NAME);
    }

    public T getData(Class<T> clazz) {
        // 判断是否有异常, 如果有异常就不需要获取数据, 直接抛出异常
        AssertUtils.isFalse(this.hasError(), this.getMsg());

        T t = null;
        try {
            Object ret = super.get(DATA_NAME);
            if (ret == null) {
                return null;
            }
            t = clazz.newInstance();
            BeanUtils.copyProperties(t, ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
