package com.felix.base.http;

public class BaseResponse<T> {
    private static final int SUCCESS_CODE = 0;
    public static final int RELOGIN_CODE = -1001;
    // 服务器返回Code
    public int errorCode;
    // 错误消息
    public String errorMsg;
    // 成功参数
    public T data;

    public boolean isSuccess() {
        return errorCode == SUCCESS_CODE;
    }
}
