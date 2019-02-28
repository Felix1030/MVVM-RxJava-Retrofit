package com.felix.nav.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.felix.nav.R;
import com.felix.nav.databinding.FragmentNavContentBinding;
import com.felix.nav.databinding.FragmentNavSectionBinding;
import com.felix.nav.model.NavItemModel;
import com.felix.nav.model.NavModel;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NavAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_SECTION = 0;
    public static final int TYPE_CONTENT = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NavAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_SECTION, R.layout.fragment_nav_section);
        addItemType(TYPE_CONTENT, R.layout.fragment_nav_content);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (item.getItemType()) {
            case TYPE_SECTION:
                NavModel sectionModel = (NavModel) item;
                FragmentNavSectionBinding sectionBinding = DataBindingUtil.bind(helper.itemView);
                sectionBinding.setName(sectionModel.getName());
                sectionBinding.executePendingBindings();
                break;
            case TYPE_CONTENT:
                NavItemModel contentModel = (NavItemModel) item;
                FragmentNavContentBinding contentBinding = DataBindingUtil.bind(helper.itemView);
                contentBinding.setContent(contentModel.getTitle());
                contentBinding.executePendingBindings();
                break;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            GridLayoutManager gridManager = (GridLayoutManager) manager;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    MultiItemEntity item = getItem(position);
                    if (item != null) {
                        if (item.getItemType() == TYPE_CONTENT) {
                            return 1;
                        } else {
                            return 3;
                        }
                    }
                    return 0;
                }
            });
        }
    }
}
