package com.felix.know.fragments;

import com.felix.base.basic.BaseFragment;
import com.felix.know.R;
import com.felix.know.adapter.KnowLeftAdapter;
import com.felix.know.databinding.FragmentKnowBinding;
import com.felix.know.model.KnowBaseModel;
import com.felix.know.viewmodel.KnowViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

public class KnowFragment extends BaseFragment<FragmentKnowBinding, KnowViewModel> {

    public static KnowFragment newInstance() {
        return new KnowFragment();
    }

    private KnowLeftAdapter mKnowLeftAdapter;
    private List<KnowBaseModel> mBaseModels = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_know;
    }

    @Override
    protected void initView() {
        mDataBinding.fragmentKnowTopBar.setTitle(getString(R.string.str_know_title));

        mDataBinding.fragmentKnowRefresh.setEnableLoadMore(false);
        mDataBinding.fragmentKnowRefresh.setEnableRefresh(false);

        mDataBinding.fragmentKnowLeft.setLayoutManager(new LinearLayoutManager(mContext));
        mKnowLeftAdapter = new KnowLeftAdapter(mBaseModels);
        mDataBinding.fragmentKnowLeft.setAdapter(mKnowLeftAdapter);

        mDataBinding.fragmentKnowRight.setLayoutManager(new LinearLayoutManager(mContext));


        mViewModel.getKnowModels().observe(this, knowBaseModels -> {
            mBaseModels.clear();
            knowBaseModels.get(0).isSelect = true;
            mBaseModels.addAll(knowBaseModels);
            mKnowLeftAdapter.notifyDataSetChanged();
        });

        mKnowLeftAdapter.setOnItemClickListener((adapter, view, position) -> {
            for (int i = 0; i < mBaseModels.size(); i++) {
                KnowBaseModel model = mBaseModels.get(i);
                model.isSelect = i == position;
            }
            mKnowLeftAdapter.notifyDataSetChanged();
        });

    }

    @Override
    protected void initData() {
        mViewModel.initKnowModels();
    }
}
