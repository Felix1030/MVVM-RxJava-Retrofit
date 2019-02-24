package com.felix.mvvm_rxjava_retrofit.test;

import com.felix.base.basic.BaseFragment;
import com.felix.base.viewmodel.BaseViewModel;
import com.felix.mvvm_rxjava_retrofit.R;
import com.felix.mvvm_rxjava_retrofit.databinding.FragmentTestBinding;

public class TestFragment extends BaseFragment<FragmentTestBinding, BaseViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initView() {
        mDataBinding.fragmentTestTopBar
                .addLeftBackImageButton()
                .setOnClickListener(v -> popBackStack());

        mDataBinding.fragmentTestTopBar.setTitle("TestFragment");
    }
}
