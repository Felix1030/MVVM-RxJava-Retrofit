package com.felix.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 自动生成网络请求接口注解类
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ApiAnnotation {
}
