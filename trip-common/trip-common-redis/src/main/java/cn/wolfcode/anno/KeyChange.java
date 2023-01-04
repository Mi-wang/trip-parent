package cn.wolfcode.anno;

import cn.wolfcode.handler.KeyChangeHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-04 004 18:58
 * key 改变的 aop 处理
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface  KeyChange {
    /**
     * 改变的 key, 支持 Spring EL 表达式
     */
    String key();

    /**
     * 按照正则表达式匹配成功的才进行处理
     */
    String[] pattern() default "";

    /**
     * Key 变动后的处理器
     */
    Class<? extends KeyChangeHandler> handler();
}
