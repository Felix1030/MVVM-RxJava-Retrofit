package com.felix.nav.fragments;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.felix.base.basic.BaseFragment;
import com.felix.nav.R;
import com.felix.nav.adapter.NavAdapter;
import com.felix.nav.databinding.FragmentNavBinding;
import com.felix.nav.viewmodel.NavViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NavFragment extends BaseFragment<FragmentNavBinding, NavViewModel> {

    private NavAdapter mNavAdapter;
    private List<MultiItemEntity> mNavItems = new ArrayList<>();

    public static NavFragment newInstance() {
        return new NavFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nav;
    }

    @Override
    protected void initView() {
        mDataBinding.fragmentNavTopBar.setTitle(getString(R.string.str_nav_title));

        GridLayoutManager gridManager = new GridLayoutManager(mContext, 3, RecyclerView.VERTICAL, false);
        mDataBinding.fragmentNavRcy.setLayoutManager(gridManager);
        mNavAdapter = new NavAdapter(mNavItems);
        mDataBinding.fragmentNavRcy.setAdapter(mNavAdapter);

        mDataBinding.fragmentNavRefresh.setEnableLoadMore(false);
        mDataBinding.fragmentNavRefresh.setOnRefreshListener(refreshLayout -> mViewModel.initNavis());

        // 数据回调
        mViewModel.getNavItems().observe(this, multiItemEntities -> {
            mNavItems.clear();
            mNavItems.addAll(multiItemEntities);
            mNavAdapter.notifyDataSetChanged();
            mDataBinding.fragmentNavRefresh.finishRefresh(true);
        });
    }

    @Override
    protected void initData() {
        mViewModel.initNavis();
    }
}
