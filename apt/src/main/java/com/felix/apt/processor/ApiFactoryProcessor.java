package com.felix.apt.processor;

import com.felix.annotation.ApiAnnotation;
import com.felix.apt.AnnotationProcessor;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

import static com.squareup.javapoet.TypeSpec.classBuilder;

public class ApiFactoryProcessor implements IProcessor {

    // 生成代码之后的包名
    private static final String GENERATE_CLASS_PACKAGE_NAME = "com.felix.apt";
    private static final String GENERATE_CLASS_SUFFIX_NAME = "Services";

    @Override
    public void process(RoundEnvironment roundEnvironment, AnnotationProcessor annotationProcessor) {
        ClassName netWorkManagerClassName = ClassName.get("com.felix.base.http", "NetWorkManager");
        ClassName flowableClassName = ClassName.get("io.reactivex", "Flowable");
        ClassName baseResponseClassName = ClassName.get("com.felix.base.http", "BaseResponse");
        try {
            printMessage(annotationProcessor, "开始查找带注解ApiAnnotation的类");
            // 查找左右带有 ApiAnnotation 注解的类
            for (TypeElement element : ElementFilter.typesIn(roundEnvironment.getElementsAnnotatedWith(ApiAnnotation.class))) {
                printMessage(annotationProcessor, "查找到开始处理: " + element.toString());

                // 生成的文件名称
                String GENERATE_CLASS_NAME = ClassName.get(element).simpleName() + GENERATE_CLASS_SUFFIX_NAME;

                // 生成类文件Builder
                TypeSpec.Builder classTb = classBuilder(GENERATE_CLASS_NAME)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addJavadoc("@ API工厂 此类由apt自动生成");

                // 类文件中生成单例对象
                FieldSpec.Builder singleFieldFb =
                        FieldSpec.builder(ClassName.get(element), "sInstance", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                                .initializer(CodeBlock.builder().add("$T.getInstance().builder($T.class)", netWorkManagerClassName, ClassName.get(element)).build());

                // 生成单例管理类
                TypeSpec.Builder singleHolder = classBuilder("SingleHolder")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .addJavadoc("@ 传入接口的实例单例 此类由apt自动生成")
                        .addField(singleFieldFb.build());

                // 类文件中添加Holder管理
                // 将该方法生成进类里面
                classTb.addType(singleHolder.build());

                // 获取类中定义的方法
                printMessage(annotationProcessor, "开始获取类中定义的方法");

                for (Element enclosedElement : element.getEnclosedElements()) {
                    if (!(enclosedElement instanceof ExecutableElement)) { //
                        continue;
                    }
                    printMessage(annotationProcessor, "开始构建Method 方法");
                    ExecutableElement executableElement = (ExecutableElement) enclosedElement;

                    MethodSpec.Builder methodBuilder =
                            MethodSpec.methodBuilder(enclosedElement.getSimpleName().toString())
                                    .addJavadoc("@此方法由apt自动生成")
                                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC);

                    // 获取方法的返回值
                    TypeMirror returnType = executableElement.getReturnType();
                    try {
                        if (!((ParameterizedTypeName) ClassName.get(returnType)).rawType.simpleName().equals(flowableClassName.simpleName())) {
                            printMessage(annotationProcessor, "暂时只支持Flowable类型");
                            continue;
                        }
                    } catch (Exception e) {
                        printMessage(annotationProcessor, "暂时只支持Flowable类型");
                        continue;
                    }
                    String paramArgs = "";
                    printMessage(annotationProcessor, "开始获取方法参数");
                    for (VariableElement parameter : executableElement.getParameters()) {
                        methodBuilder.addParameter(TypeName.get(parameter.asType()), parameter.getSimpleName().toString());
                        paramArgs += parameter.getSimpleName().toString() + ",";
                    }

                    TypeName httpResponseWithType = ((ParameterizedTypeName) TypeName.get(returnType)).typeArguments.get(0);
                    ClassName stringClassName = ClassName.get(String.class);

                    try {
                        if (httpResponseWithType instanceof ParameterizedTypeName) {
                            // 多级泛型
                            if (!baseResponseClassName.equals(((ParameterizedTypeName) httpResponseWithType).rawType)) {
                                printMessage(annotationProcessor, Diagnostic.Kind.WARNING, ClassName.get(element) + "." + enclosedElement.getSimpleName()
                                        .toString() + "(" + paramArgs.substring(0, paramArgs.length() - 1) + ")需要返回Flowable<BaseResponse<T>>");
                                continue;
                            }
                            //typeNameWithoutHttpResponse:String
                            TypeName typeNameWithoutHttpResponse = ((ParameterizedTypeName) httpResponseWithType).typeArguments.get(0);
                            ParameterizedTypeName finalFlowable = ParameterizedTypeName.get(flowableClassName, typeNameWithoutHttpResponse);
                            methodBuilder.returns(finalFlowable);
                            methodBuilder.addStatement(
                                    "return SingleHolder.sInstance.$L($L)" +
                                            ".compose($T.get())"
                                    , enclosedElement.getSimpleName().toString()
                                    , paramArgs.isEmpty() ? "" : paramArgs.substring(0, paramArgs.length() - 1)
                                    , ClassName.get("com.felix.base.http.transformer", "DefaultTransformer"));
                        } else if (httpResponseWithType instanceof ClassName) {
                            // 一级泛型
                            if (!((ClassName) httpResponseWithType).simpleName().equals(stringClassName.simpleName())) {
                                printMessage(annotationProcessor, Diagnostic.Kind.WARNING, ClassName.get(element) + "." + enclosedElement.getSimpleName()
                                        .toString() + "(" + paramArgs.substring(0, paramArgs.length() - 1) + ")" +
                                        "需要返回Flowable<BaseResponse<T>>或者Flowable<String>");
                                continue;
                            }
                            //到这里说明是Flowable<String>,Flowable<String>里面不做错误处理
                            ParameterizedTypeName finalFlowable = ParameterizedTypeName.get(flowableClassName, stringClassName);
                            methodBuilder.returns(finalFlowable);
                            methodBuilder.addStatement(
                                    "return SingleHolder.sInstance.$L($L)" +
                                            ".compose($T.io_main())"
                                    , enclosedElement.getSimpleName().toString()
                                    , paramArgs.isEmpty() ? "" : paramArgs.substring(0, paramArgs.length() - 1)
                                    , ClassName.get("com.felix.base.http.schedulers", "RxSchedulers")
                            );
                        } else {
                            printMessage(annotationProcessor, Diagnostic.Kind.WARNING, ClassName.get(element) + "." + enclosedElement.getSimpleName()
                                    .toString() + "(" + paramArgs.substring(0, paramArgs.length() - 1) + ")" +
                                    "需要返回Flowable<BaseResponse<T>>或者Flowable<String>");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        printMessage(annotationProcessor, Diagnostic.Kind.WARNING, ClassName.get(element) + "." + enclosedElement.getSimpleName()
                                .toString() + "(" + paramArgs.substring(0, paramArgs.length() - 1) + ")" +
                                "需要返回Flowable<BaseResponse<T>>或者Flowable<String>");
                        continue;
                    }
                    classTb.addMethod(methodBuilder.build());
                }

                // 生成文件
                JavaFile javaFile = JavaFile.builder(GENERATE_CLASS_PACKAGE_NAME, classTb.build()).build();
                // 将文件写出
                javaFile.writeTo(annotationProcessor.mFiler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 打印生成日志指定等级
    private void printMessage(AnnotationProcessor processor, Diagnostic.Kind kind, String message) {
        processor.mMessager.printMessage(kind, message);
    }

    // 打印生成日志 默认等级
    private void printMessage(AnnotationProcessor processor, String message) {
        printMessage(processor, Diagnostic.Kind.NOTE, message);
    }
}
