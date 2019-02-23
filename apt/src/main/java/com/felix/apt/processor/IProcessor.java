package com.felix.apt.processor;

import com.felix.apt.AnnotationProcessor;
import javax.annotation.processing.RoundEnvironment;

public interface IProcessor { // 注解生成代理类
    void process(RoundEnvironment roundEnvironment, AnnotationProcessor annotationProcessor);
}
