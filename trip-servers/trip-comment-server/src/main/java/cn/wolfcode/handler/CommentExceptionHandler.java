package cn.wolfcode.handler;

import cn.wolfcode.advice.ExceptionControllerAdvice;
import cn.wolfcode.constants.HttpStatus;
import cn.wolfcode.exception.AuthException;
import cn.wolfcode.vo.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 20463
 */
@RestControllerAdvice
public class CommentExceptionHandler extends ExceptionControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger("CommentExceptionHandler");

    @ExceptionHandler(AuthException.class)
    public R<?> commonExceptionHandler(AuthException ex) {
        log.error("[统一异常处理] 用户未认证", ex);

        return R.err(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }
}
