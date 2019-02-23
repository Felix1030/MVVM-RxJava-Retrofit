package com.felix.mvvm_rxjava_retrofit.home;

import com.felix.base.BaseFragment;
import com.felix.mvvm_rxjava_retrofit.R;
import com.felix.mvvm_rxjava_retrofit.databinding.FragmentHomeBinding;
import com.felix.mvvm_rxjava_retrofit.test.TestFragment;

public class HomeFragment extends BaseFragment<FragmentHomeBinding,HomeViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mDataBinding.fragmentHomeTopBar.addRightTextButton("Test",R.id.topbar_right_about_button)
                .setOnClickListener(v -> startFragment(new TestFragment()));
        mDataBinding.fragmentHomeTopBar.setTitle("HomeFragment");
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }
}
