package cn.wolfcode.service.impl;

import cn.wolfcode.utils.TokenService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-02 002 17:03
 */
@Service
public class UserTokenService extends TokenService {
    @Override
    protected String getToken(Object request) {
        if (request instanceof HttpServletRequest) {
            ((HttpServletRequest) request).getHeader(super.header);
        }
        return null;
    }
}
