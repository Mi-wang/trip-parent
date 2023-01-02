package cn.wolfcode.handler;

import cn.wolfcode.anno.RequestUser;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.exception.AuthException;
import cn.wolfcode.service.impl.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author 20463
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserTokenService userTokenService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType(); // 获取参数的类型
        RequestUser annotation = methodParameter.getParameterAnnotation(RequestUser.class); // 获取参数前贴的注解

        // 当前的参数必须贴了 RequestUser 注解并且参数类型为 UserInfo 类型, 才进行处理
        return annotation != null && UserInfo.class == parameterType;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        // 如果 supportsPrameter 方法返回 true, 当前方法才会执行
        UserInfo userInfo = userTokenService.getLoginUser(nativeWebRequest.getNativeRequest());

        RequestUser annotation = methodParameter.getParameterAnnotation(RequestUser.class);
        // 通过注解中的 required 属性来决定是否抛出异常
        if ((annotation != null && annotation.required()) && userInfo == null) {
            throw new AuthException("用户未认证");
        }
        return userInfo;
    }
}
