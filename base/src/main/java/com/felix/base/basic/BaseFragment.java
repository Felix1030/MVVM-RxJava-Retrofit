package com.felix.base.basic;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.felix.base.viewmodel.BaseViewModel;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.RxLifecycle;
import com.trello.rxlifecycle3.android.FragmentEvent;
import com.trello.rxlifecycle3.android.RxLifecycleAndroid;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public abstract class BaseFragment<D extends ViewDataBinding, VM extends BaseViewModel> extends QMUIFragment implements LifecycleProvider<FragmentEvent>,ILifeCycleView {

    // DataBinding类
    protected D mDataBinding;
    // ViewModel类
    protected VM mViewModel;
    // private int mViewModelId;
    protected Context mContext;
    protected Fragment mFragment;

    // Fragment生命周期变化通知
    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    public BaseFragment() {
        mFragment = this;
    }

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(mContext, 100);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    protected View onCreateView() {
        mDataBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), getBaseFragmentActivity().getFragmentContainer(), false);
//        mViewModelId = initVariableId();
        mViewModel = initViewModel();
        reInitViewModel();
        getLifecycle().addObserver(mViewModel);

        // 生命周期相关
        mViewModel.injectLifecycleProvider(this);
        mViewModel.injectLifecycleView(this);

        // 注册ProgressDialog 事件回调
        registerUIProgressChangeCallBack();
        // 初始化View
        initView();
        // 服务器加载数据
        initData();
        // 注册事件
        mViewModel.registerRxBus();
        // 下来刷新
        initClassHeader();
        return mDataBinding.getRoot();
    }

    private void initClassHeader() {
        /*ClassicsHeader.REFRESH_HEADER_PULLING = getResources().getString(R.string.str_head_pulldown);//"下拉可以刷新";
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getResources().getString(R.string.str_header_refreshing);//"正在刷新...";
        ClassicsHeader.REFRESH_HEADER_RELEASE = getResources().getString(R.string.str_head_release);//"释放立即刷新";
        ClassicsHeader.REFRESH_HEADER_FINISH = getResources().getString(R.string.str_header_finish);//"刷新完成";
        ClassicsHeader.REFRESH_HEADER_FAILED = getResources().getString(R.string.str_header_failed);//"刷新失败";

        ClassicsFooter.REFRESH_FOOTER_LOADING = getResources().getString(R.string.footer_loading);//"正在加载...";
        ClassicsFooter.REFRESH_FOOTER_FINISH = getResources().getString(R.string.footer_finish);//"加载完成";
        ClassicsFooter.REFRESH_FOOTER_NOTHING = ""; // 没有更多数据*/
    }

    //    public int initVariableId() {
//    } // ViewModelId
    // 布局ID
    protected abstract int getLayoutId();

    // 初始化View
    protected abstract void initView();

    // 子类重写加载数据
    protected void initData() {}

    // 初始化ViewModel
    private void reInitViewModel() {
        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            mViewModel = (VM) createViewModel(this, modelClass);
        }
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return ViewModelProviders.of(fragment).get(cls);
    }

    private VM initViewModel() {
        return null;
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    public <T> FlowableTransformer<T, T> bindToDestroy() {
        return bindUntilEvent(FragmentEvent.DESTROY);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        if (isLight()) {
            // 深色背景  白色字体
            QMUIStatusBarHelper.setStatusBarDarkMode(getBaseFragmentActivity());
        } else {
            // 白色背景  深色字体
            QMUIStatusBarHelper.setStatusBarLightMode(getBaseFragmentActivity());
        }
    }

    // isLight = true  深色背景  白色字体
    // isLight = false 白色背景  深色字体
    protected boolean isLight() {
        return false;
    }

    // Progress显示隐藏事件回调
    private void registerUIProgressChangeCallBack() {
        // 显示Progress事件
        mViewModel.getUIProgressLiveData()
                .getShowProgressEvent().observe(this,progressMessage -> {

        });
        // 隐藏Progress回调事件
        mViewModel.getUIProgressLiveData()
                .getDismissProgressEvent().observe(this, aVoid -> {

                });
    }

    // 显示Progress
    public void showProgress(String progressMessage) {

    }

    // 隐藏Progress
    public void dismissProgress() {

    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
        getLifecycle().removeObserver(mViewModel);
        if (null != mViewModel) mViewModel.removeRxBus();
        if (mDataBinding != null) mDataBinding.unbind();
    }

    @Override
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        mContext = null;
        super.onDetach();
    }
}
