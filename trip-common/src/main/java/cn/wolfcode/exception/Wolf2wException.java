package cn.wolfcode.exception;

import cn.wolfcode.vo.AjaxResult;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/13 19:42
 */
@Setter
@Getter
public class Wolf2wException extends RuntimeException {
    private Integer code;

    public Wolf2wException(String message) {
        super(message);
        // 当创建该异常类型, 不指定 code 时, 那 code 为默认异常状态码
        this.code = AjaxResult.DEFAULT_FAILED_CODE;
    }

    public Wolf2wException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
