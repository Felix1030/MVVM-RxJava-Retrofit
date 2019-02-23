package com.felix.apt.processor;

import com.felix.apt.AnnotationProcessor;
import javax.annotation.processing.RoundEnvironment;

public class ApiFactoryProcessor implements IProcessor{

    // 生成代码之后的包名
    private static final String GENERATE_CLASS_PACKAGE_NAME = "com.felix.apt";
    private static final String GENERATE_CLASS_SUFFIX_NAME = "Services";

    @Override
    public void process(RoundEnvironment roundEnvironment, AnnotationProcessor annotationProcessor) {

    }
}
