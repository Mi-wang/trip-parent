package cn.wolfcode.exception;

import cn.wolfcode.constants.HttpStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wby
 * @version 1.0 业务异常
 * @date 2022/12/13 19:44
 */
@Getter
@Setter
public class AuthException extends Wolf2wException {

    public AuthException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
