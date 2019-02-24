package com.felix.mvvm_rxjava_retrofit.home;

import android.annotation.SuppressLint;
import com.felix.base.basic.BaseFragment;
import com.felix.base.utils.RxThrottleUtils;
import com.felix.mvvm_rxjava_retrofit.R;
import com.felix.mvvm_rxjava_retrofit.databinding.FragmentHomeBinding;
import com.felix.mvvm_rxjava_retrofit.test.TestFragment;
import com.jakewharton.rxbinding3.view.RxView;

public class HomeFragment extends BaseFragment<FragmentHomeBinding,HomeViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        mDataBinding.fragmentHomeTopBar.addRightTextButton("Test",R.id.topbar_right_about_button)
                .setOnClickListener(v -> startFragment(new TestFragment()));
        mDataBinding.fragmentHomeTopBar.setTitle("HomeFragment");

        RxView.clicks(mDataBinding.fragmentHomeTestBtn)
                .compose(RxThrottleUtils.provideClickThrottleObservable())
                .subscribe(unit -> mViewModel.getIsReset().observe(HomeFragment.this, isReset -> {

                        }));
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }
}
