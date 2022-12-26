package cn.wolfcode.vo;

import cn.wolfcode.constants.HttpStatus;
import cn.wolfcode.exception.Wolf2wException;

import java.util.HashMap;

/**
 * 统一结果响应对象
 *
 * @author wby
 * @version 1.0
 * @date 2022/12/13 15:26
 * @param<T> 响应 data 属性数据类型
 */
public class AjaxResult<T> extends HashMap<String, Object> {

    public static final Integer DEFAULT_SUCCESS_CODE = HttpStatus.OK;
    public static final String DEFAULT_SUCCESS_MSG = "success";

    public static final Integer DEFAULT_FAILED_CODE = HttpStatus.SERVER_ERROR;
    public static final String DEFAULT_FAILED_MSG = "error";


    public static final String CODE_NAME = "code";
    public static final String MSG_NAME = "msg";
    public static final String DATA_NAME = "data";

    public AjaxResult(Integer code, String msg, T data) {
        super.put(CODE_NAME, code);
        super.put(MSG_NAME, msg);
        // 当 data 没有数据的时候, 就没有属性
        // 完整数据结构: {code: 200, msg: 'xxx', data: {xxxx}}
        // 没有 data: {code: 200, msg: 'xxx'}
        if (data != null) {
            super.put(DATA_NAME, data);
        }
    }

    @Override
    public AjaxResult<?> put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public AjaxResult(Integer code, String msg) {
        this(code, msg, null);
    }

    public static <T> AjaxResult<T> success(String msg, T data) {
        return new AjaxResult<>(DEFAULT_SUCCESS_CODE, msg, data);
    }

    public static <T> AjaxResult<T> success(T data) {
        return success(DEFAULT_SUCCESS_MSG, data);
    }

    public static <T> AjaxResult<T> success(String msg) {
        return success(msg, null);
    }

    public static <T> AjaxResult<T> success() {
        return success(DEFAULT_SUCCESS_MSG);
    }

    public static <T> AjaxResult<T> failed(Integer code, String msg, T data) {
        return new AjaxResult<>(code, msg, data);
    }

    public static <T> AjaxResult<T> failed(Integer code, String msg) {
        return failed(code, msg, null);
    }

    public static <T> AjaxResult<T> failed(String msg) {
        return failed(DEFAULT_FAILED_CODE, msg);
    }

    public static <T> AjaxResult<T> failed(Wolf2wException ex) {
        return failed(ex.getCode(), ex.getMessage());
    }

    public static <T> AjaxResult<T> failed() {
        return failed(DEFAULT_FAILED_MSG);
    }
}
