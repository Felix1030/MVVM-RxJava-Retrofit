package com.felix.base.http.transformer;

import com.felix.base.http.BaseResponse;
import com.felix.base.http.schedulers.RxSchedulers;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class DefaultTransformer<T> implements FlowableTransformer<BaseResponse<T>, T> {

    public static <T> DefaultTransformer<T> get() {
        return new DefaultTransformer<>();
    }
    @Override
    public Publisher<T> apply(Flowable<BaseResponse<T>> upstream) {
        return upstream.compose(RxSchedulers.io_main())
                .compose(ErrorTransformer.<T>get())
                ;
    }
}
