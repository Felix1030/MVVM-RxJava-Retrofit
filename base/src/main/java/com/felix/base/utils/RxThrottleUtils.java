package com.felix.base.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;

public class RxThrottleUtils {

    private static final int CLICK_THROTTLE_DURATION = 150;
    private static final TimeUnit CLICK_THROTTLE_TIMEUNIT = TimeUnit.MILLISECONDS;

    /**
     * 防止重复点击
     * @param <R>
     * @return
     */
    public static  <R> ObservableTransformer<R,R> provideClickThrottleObservable() {
        return upstream -> upstream.throttleFirst(CLICK_THROTTLE_DURATION,CLICK_THROTTLE_TIMEUNIT);
    }

    /**
     * 防止重复点击
     * @param <R>
     * @return
     */
    public static <R> FlowableTransformer<R,R> provideClickThrottleFlowable() {
        return upstream -> upstream.throttleFirst(CLICK_THROTTLE_DURATION,CLICK_THROTTLE_TIMEUNIT);
    }
}
