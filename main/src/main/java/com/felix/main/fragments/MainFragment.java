package com.felix.main.fragments;

import android.annotation.SuppressLint;
import android.widget.ImageView;
import com.felix.base.basic.BaseFragment;
import com.felix.base.utils.loader.ImageLoader;
import com.felix.main.R;
import com.felix.main.databinding.FragmentMainBinding;
import com.felix.main.model.BannerModel;
import com.felix.main.viewmodel.MainViewModel;
import java.util.ArrayList;
import java.util.List;
import cn.bingoogolapple.bgabanner.BGABanner;
import io.reactivex.Observable;

public class MainFragment extends BaseFragment<FragmentMainBinding, MainViewModel> {

    // 轮播图
    private List<BannerModel> mBanners = new ArrayList<>();

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
    }
}
