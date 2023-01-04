package cn.wolfcode.aop;

import cn.wolfcode.anno.KeyChange;
import cn.wolfcode.handler.KeyChangeHandler;
import cn.wolfcode.utils.SpringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author wby
 * @version 1.0
 * @date 2023-01-04 004 19:00
 */
@Aspect
@Component
public class KeyChangeHandlerAspect {

    public static final Logger log = LoggerFactory.getLogger("KeyChangeHandlerAspect");
    /**
     * Spring EL 表达式解析器
     */
    private final SpelExpressionParser parser = new SpelExpressionParser();
    /**
     * 用于获取方法的参数名称
     */
    private final DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    /**
     * 代理所有贴了 @KeyChange 的方法
     */
    @Pointcut(value = "@annotation(cn.wolfcode.anno.KeyChange)")
    public void pointcut() {
    }

    @Before("pointcut() && @annotation(keyChange)")
    public void after(JoinPoint jp, KeyChange keyChange) {
        // 方法签名
        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        // 获取注解上的 key
        String key = keyChange.key();
        if (isSpel(key)) {
            key = parseSpel(key, method, jp.getArgs());
        }

        log.debug("[Key Change] 监听到 key 改变, key={}, pattern={}, method={}", key, keyChange.pattern(), signature);

        // 从 Spring 容器中获取对应的处理器
        KeyChangeHandler handler = SpringUtils.getBean(keyChange.handler());
        log.debug("[Key Change] key:{} 正在进行处理...", key);
        handler.handle(key, jp.getArgs());
        log.debug("[Key Change] key:{} 处理完成...", key);
    }

    public static void main(String[] args) {
        String s = "strategies:stat:sid";
        System.out.println(Pattern.matches("[#'+]+", s));
        Pattern pattern = Pattern.compile("[#+']");
        Matcher matcher = pattern.matcher(s);
        System.out.println(matcher.find());
    }

    /**
     * 判断是否是 spel
     */
    private boolean isSpel(String key) {
        Pattern pattern = Pattern.compile("[#+']");
        return pattern.matcher(key).find();
    }

    /**
     * 解析 spel
     */
    private String parseSpel(String key, Method method, Object[] args) {
        // 解析 key 中的 spel
        Expression expression = parser.parseExpression(key);
        String[] parameterNames = nameDiscoverer.getParameterNames(method);

        if (parameterNames != null && parameterNames.length > 0) {

            // 将参数封装到 Spring 表达式解析器的上下文对象
            StandardEvaluationContext context = new StandardEvaluationContext();
            for (int i = 0; i < args.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }

            // 参数解析
            return Objects.toString(expression.getValue(context));
        }

        // 简单的解析
        return Objects.toString(expression.getValue());
    }
}

