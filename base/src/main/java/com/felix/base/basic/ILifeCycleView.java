package com.felix.base.basic;

import io.reactivex.FlowableTransformer;

public interface ILifeCycleView {
    /**
     * 将Flowable绑定到view直到被销毁
     * */
    <T> FlowableTransformer<T, T> bindToDestroy();
}
