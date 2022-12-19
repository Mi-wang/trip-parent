package cn.wolfcode.advice;

import cn.wolfcode.constants.HttpStatus;
import cn.wolfcode.exception.Wolf2wException;
import cn.wolfcode.vo.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/13 19:55
 * 异常处理器
 */
@Slf4j
public class ExceptionControllerAdvice {
    /**
     * 捕获 Controller 中抛出的所有异常, 由该方法进行返回结果
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult<?> commonExceptionHandler(RuntimeException ex) {
        log.error("[统一异常处理] 出现系统异常", ex);

        return AjaxResult.failed(HttpStatus.SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(Wolf2wException.class)
    public AjaxResult<?> commonExceptionHandler(Wolf2wException ex) {
        log.error("[统一异常处理] 服务内部异常", ex);

        return AjaxResult.failed(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public AjaxResult<?> validExceptionHandler(BindException ex) {
        log.debug("[统一异常处理] 捕获参数校验异常", ex);

        // 获取到所有错误中的第一个参数校验错误
        ObjectError error = ex.getAllErrors().get(0);
        return AjaxResult.failed(HttpStatus.BAD_REQUEST, error.getDefaultMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public AjaxResult<?> validExceptionHandler(ConstraintViolationException ex) {
        log.debug("[统一异常处理] 捕获参数校验异常", ex);

        // 获取到所有错误中的第一个参数校验错误
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        String msg = "参数错误";

        for (ConstraintViolation<?> violation : violations) {
            msg = violation.getMessage();
            break;
        }

        return AjaxResult.failed(HttpStatus.BAD_REQUEST, msg);
    }
}
