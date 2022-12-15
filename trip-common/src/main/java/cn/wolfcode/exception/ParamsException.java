package cn.wolfcode.exception;

import cn.wolfcode.constants.HttpStatus;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/13 19:44
 */
public class ParamsException extends Wolf2wException {

    public ParamsException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
