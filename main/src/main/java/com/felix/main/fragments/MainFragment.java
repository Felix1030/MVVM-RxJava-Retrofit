package com.felix.main.fragments;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import com.felix.base.basic.BaseFragment;
import com.felix.base.utils.loader.ImageLoader;
import com.felix.main.R;
import com.felix.main.adapter.MainAdapter;
import com.felix.main.databinding.FragmentMainBinding;
import com.felix.main.model.ArticleBaseModel;
import com.felix.main.model.ArticleModel;
import com.felix.main.model.BannerModel;
import com.felix.main.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import cn.bingoogolapple.bgabanner.BGABanner;
import io.reactivex.Observable;

public class MainFragment extends BaseFragment<FragmentMainBinding, MainViewModel> {

    // 轮播图
    private List<BannerModel> mBanners = new ArrayList<>();
    // 下拉刷新
    private boolean mIsRefresh;
    // 界面适配器
    private MainAdapter mMainAdapter;
    // 数据
    private List<ArticleModel> mArticleModels = new ArrayList<>();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {
        mViewModel.getBanner().observe(this, this::setBanner);
        mViewModel.getArticleModel().observe(this,this::setArticle);

        mDataBinding.mainRecy.setLayoutManager(new LinearLayoutManager(mContext));
        mMainAdapter = new MainAdapter(mArticleModels);
        mDataBinding.mainRecy.setAdapter(mMainAdapter);

        mDataBinding.mainRefresh.setOnRefreshListener(refreshLayout -> onRefreshMethod());
        mDataBinding.mainRefresh.setOnLoadMoreListener(refreshLayout -> onLoadMoreMethod());
    }

    /**加载更多*/
    private void onLoadMoreMethod() {
        mIsRefresh = false;
        mDataBinding.mainRefresh.setEnableRefresh(false);
        int pageIndex = mMainAdapter.getData().size() / 20;
        mViewModel.initArticlesWithPage(pageIndex);
    }

    /**刷新*/
    private void onRefreshMethod() {
        mIsRefresh = true;
        mDataBinding.mainRefresh.setEnableLoadMore(false);
        // 下拉刷新需要获取置顶数据
        mViewModel.initAllArticles();
    }

    /**文章列表*/
    private void setArticle(ArticleBaseModel articleBaseModel) {
        if (mIsRefresh) {
            mArticleModels.clear();
            mDataBinding.mainRefresh.finishRefresh(true);
            mDataBinding.mainRefresh.setEnableLoadMore(true);
        } else {
            mDataBinding.mainRefresh.finishLoadMore(true);
            mDataBinding.mainRefresh.setEnableRefresh(true);
        }
        mArticleModels.addAll(articleBaseModel.getDatas());

        int currentSize = mArticleModels.size();
        if (currentSize >= articleBaseModel.getTotal()) {
            mDataBinding.mainRefresh.setEnableLoadMore(false);
        }
        mMainAdapter.notifyDataSetChanged();
    }

    /*** 设置 */
    @SuppressLint("CheckResult")
    private void setBanner(List<BannerModel> banners) {
        mBanners.clear();
        mBanners.addAll(banners);
        List<String> bannerPaths = new ArrayList<>();
        List<String> bannerTitles = new ArrayList<>();
        Observable.fromIterable(banners)
                .subscribe(model -> {
                    bannerPaths.add(model.imagePath);
                    bannerTitles.add(model.title);
                });
        mDataBinding.mainBanner.setAutoPlayAble(banners.size() > 1);
        mDataBinding.mainBanner.setData(bannerPaths,bannerTitles);
        mDataBinding.mainBanner.setAdapter((BGABanner.Adapter<ImageView, String>) (banner, itemView, model, position) ->
                ImageLoader.with(mContext).load(model).into(itemView));
    }

    @Override
    protected void initData() {
        mViewModel.initBanners();
        mViewModel.initAllArticles(); // 初始化文章
    }
}
