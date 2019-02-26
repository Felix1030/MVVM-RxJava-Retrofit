package com.felix.mine.fragments;

import com.felix.base.basic.BaseFragment;
import com.felix.mine.R;
import com.felix.mine.databinding.FragmentMineBinding;
import com.felix.mine.viewmodel.MineViewModel;

public class MineFragment extends BaseFragment<FragmentMineBinding, MineViewModel> {

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {

    }

}
