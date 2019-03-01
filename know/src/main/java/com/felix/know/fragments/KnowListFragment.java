package com.felix.know.fragments;

import android.os.Bundle;

import com.felix.base.basic.BaseFragment;
import com.felix.know.R;
import com.felix.know.databinding.FragmentKnowListBinding;
import com.felix.know.viewmodel.KnowListViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import androidx.annotation.NonNull;

public class KnowListFragment extends BaseFragment<FragmentKnowListBinding, KnowListViewModel> {

    private static final String C_ID = "c_id";

    public static KnowListFragment newInstance(int cId) {
        KnowListFragment fragment = new KnowListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(C_ID, cId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_know_list;
    }

    @Override
    protected void initView() {
        Bundle aragments = getArguments();
        mViewModel.mCidField.set(aragments.getInt(C_ID));

        mDataBinding.fragmentKnowListRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });

        mDataBinding.fragmentKnowListRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    protected void lazyFetchData() {
        mViewModel.initDataWithPage(1);
    }
}
