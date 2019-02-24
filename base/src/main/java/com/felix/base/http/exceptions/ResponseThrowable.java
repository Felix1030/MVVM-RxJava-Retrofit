package com.felix.base.http.exceptions;

public class ResponseThrowable extends Exception {

    public Throwable mThrowable;
    public int mCode;

    public ResponseThrowable(int code, Throwable e) {
        mCode = code;
        mThrowable = e;
    }
}
