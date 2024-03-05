package com.sky.annotation;

/*
    自定义注解，用于标识某个方法需要进行功能字段自动填充处理
 */

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 作用于方法
@Retention(RetentionPolicy.RUNTIME) // 运行时保留
public @interface AutoFill {
    //数据库操作类型: UPDATE, INSERT
    OperationType value();
}
