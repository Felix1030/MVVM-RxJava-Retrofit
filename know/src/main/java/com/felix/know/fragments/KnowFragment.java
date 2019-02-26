package com.felix.know.fragments;

import com.felix.base.basic.BaseFragment;
import com.felix.know.R;
import com.felix.know.databinding.FragmentKnowBinding;
import com.felix.know.viewmodel.KonwViewModel;

public class KnowFragment extends BaseFragment<FragmentKnowBinding, KonwViewModel> {

    public static KnowFragment newInstance() {
        return new KnowFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_know;
    }

    @Override
    protected void initView() {

    }

}
