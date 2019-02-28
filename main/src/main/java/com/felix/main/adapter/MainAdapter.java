package com.felix.main.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.felix.main.R;
import com.felix.main.databinding.FragmentMainArticleItemBinding;
import com.felix.main.model.ArticleModel;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class MainAdapter extends BaseQuickAdapter<ArticleModel, BaseViewHolder> {

    public MainAdapter(@Nullable List<ArticleModel> data) {
        super(R.layout.fragment_main_article_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleModel item) {
        FragmentMainArticleItemBinding binding = DataBindingUtil.bind(helper.itemView);
        if (null != item && null != binding) {
            binding.setArticle(item);
            binding.executePendingBindings();

            int pos = helper.getAdapterPosition();
            int visible = getData().size() - 1 == pos ? View.VISIBLE : View.GONE;
            binding.articleDivider.setVisibility(visible);
        }
    }
}
