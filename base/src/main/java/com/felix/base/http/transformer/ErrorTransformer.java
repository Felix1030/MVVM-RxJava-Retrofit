package com.felix.base.http.transformer;

import com.felix.base.http.BaseResponse;
import com.felix.base.http.exceptions.ExceptionHandle;
import com.felix.base.http.exceptions.ServerException;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class ErrorTransformer<T> implements FlowableTransformer<BaseResponse<T>, T> {

    public static <T> ErrorTransformer<T> get() {
        return new ErrorTransformer<>();
    }

    @Override
    public Publisher<T> apply(Flowable<BaseResponse<T>> upstream) {
        return upstream.map(tBaseResponse -> {
            if (!tBaseResponse.isSuccess()) {
                throw new ServerException(tBaseResponse);
            }
            return tBaseResponse.data;
        }).onErrorResumeNext(throwable -> {
            return Flowable.error(ExceptionHandle.handleException(throwable));
        });
    }
}
