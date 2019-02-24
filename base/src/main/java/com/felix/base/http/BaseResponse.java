package com.felix.base.http;

public class BaseResponse<T> {
    private static final int SUCCESS_CODE = 200;
    // 服务器返回Code
    public int code;
    // 错误消息
    public String message;
    // 成功参数
    public T result;

    public boolean isSuccess() {
        return code == SUCCESS_CODE;
    }
}
