package com.felix.base.http.exceptions;

import com.felix.base.http.BaseResponse;

public class ServerException extends RuntimeException {
    private final BaseResponse mTHttpResponse;

    public <T> ServerException(BaseResponse<T> tHttpResponse) {
        mTHttpResponse = tHttpResponse;
    }

    public BaseResponse getTHttpResponse() {
        return mTHttpResponse;
    }
}
