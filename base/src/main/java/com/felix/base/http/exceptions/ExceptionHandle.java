package com.felix.base.http.exceptions;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;

public class ExceptionHandle {
    /**
     * 定义网络异常码
     */
    public static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponseThrowable handleException(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponseThrowable(resultException.getTHttpResponse().errorCode, resultException);
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable(ERROR.HTTP_ERROR, e);
            switch (httpException.code()) {
                case GATEWAY_TIMEOUT:
                    break;
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    break;
            }
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponseThrowable(ERROR.PARSE_ERROR, e);
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            ex = new ResponseThrowable(ERROR.NETWORD_ERROR, e);
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponseThrowable(ERROR.SSL_ERROR, e);
        } else if (e instanceof ConnectTimeoutException || e instanceof java.net.SocketTimeoutException) {
            ex = new ResponseThrowable(ERROR.TIMEOUT_ERROR, e);
        } else {
            ex = new ResponseThrowable(ERROR.UNKNOWN, e);
        }
        return ex;
    }

    /**
     * 约定异常
     */
    public static class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = -1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = -1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = -1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = -1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = -1004;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = -1005;
    }
}
