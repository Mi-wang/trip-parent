package cn.wolfcode.service.impl;

import cn.wolfcode.utils.TokenService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/21 18:04
 */
@Service
public class UserTokenService extends TokenService {

    @Override
    protected String getToken(Object request) {
        // 判断请求对象必须是 HttpServletRequest 对象
        if (!(request instanceof HttpServletRequest)) {
            return null;
        }

        // 从 http 请求头中获取名字为 ${header} 的值并返回
        return ((HttpServletRequest) request).getHeader(super.header);
    }
}


