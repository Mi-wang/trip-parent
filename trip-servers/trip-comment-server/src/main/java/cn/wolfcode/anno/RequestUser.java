package cn.wolfcode.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 20463
 */
@Target(ElementType.PARAMETER) // 该注解只能贴在方法参数前
@Retention(RetentionPolicy.RUNTIME) // 该注解存在于运行时期
public @interface RequestUser {

    /**
     * 是否必须有用户对象, 如果为 true 表示必须有, 没有会抛出异常
     */
    boolean required() default true;
}
