package com.felix.know.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.felix.know.R;
import com.felix.know.databinding.FragmentKnowLeftBinding;
import com.felix.know.model.KnowBaseModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class KnowLeftAdapter extends BaseQuickAdapter<KnowBaseModel, BaseViewHolder> {

    public KnowLeftAdapter( @Nullable List<KnowBaseModel> data) {
        super(R.layout.fragment_know_left, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowBaseModel item) {
        FragmentKnowLeftBinding dataBinding = DataBindingUtil.bind(helper.itemView);
        dataBinding.setBaseModel(item);
        dataBinding.executePendingBindings();
    }
}
