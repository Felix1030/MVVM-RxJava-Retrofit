package com.felix.nav.fragments;

import com.felix.base.basic.BaseFragment;
import com.felix.nav.R;
import com.felix.nav.databinding.FragmentNavBinding;
import com.felix.nav.viewmodel.NavViewModel;

public class NavFragment extends BaseFragment<FragmentNavBinding, NavViewModel> {

    public static NavFragment newInstance() {
        return new NavFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nav;
    }

    @Override
    protected void initView() {

    }
}
