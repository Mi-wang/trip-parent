package cn.wolfcode.constants;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/13 19:45
 * 自己维护的 http 响应状态码
 */
public interface  HttpStatus {
    /**
     * 成功
     */
    Integer OK = 200;

    /**
     * 服务端错误
     */
    Integer SERVER_ERROR = 500;

    /**
     * 业务异常
     */
    Integer BUSINESS_EXCEPTION = 501;

    /**
     * 参数异常
     */
    Integer BAD_REQUEST = 400;

    /**
     * 认证异常
     */
    Integer UNAUTHORIZED = 401;
}
