package com.felix.mvvm_rxjava_retrofit.fragments.home;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import com.felix.base.basic.BaseFragment;
import com.felix.know.fragments.KnowFragment;
import com.felix.main.fragments.MainFragment;
import com.felix.mine.fragments.MineFragment;
import com.felix.mvvm_rxjava_retrofit.R;
import com.felix.mvvm_rxjava_retrofit.databinding.FragmentHomeBinding;
import com.felix.nav.fragments.NavFragment;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIPagerAdapter;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel>
//        implements QMUITabSegment.OnTabSelectedListener
{

    private static final int CONTAINER_ID = R.id.home_fragment_container;

    private QMUITabSegment mHomeTabs;
    private Fragment mCurrentFragment;

    private MainFragment mMainFragment;
    private KnowFragment mKnowFragment;
    private NavFragment mNavFragment;
    private MineFragment mMineFragment;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        initTabs();
//        initTabsNew();
        initPagers();
//        setDefaultFragment();
    }

    private void initTabsNew() {
        int normalColor = QMUIResHelper.getAttrColor(getActivity(), R.attr.qmui_config_color_gray_6);
        int selectColor = QMUIResHelper.getAttrColor(getActivity(), R.attr.qmui_config_color_blue);
        mDataBinding.homeFragmentTabs.setDefaultNormalColor(normalColor);
        mDataBinding.homeFragmentTabs.setDefaultSelectedColor(selectColor);
    }

    private void initPagers() {
        QMUIPagerAdapter pagerAdapter = new QMUIPagerAdapter() {
            private FragmentTransaction mCurrentTransaction;
            private Fragment mCurrentPrimaryItem = null;

            @Override
            protected Object hydrate(ViewGroup container, int position) {
                String name = makeFragmentName(container.getId(), position);
                if (mCurrentTransaction == null) {
                    mCurrentTransaction = getChildFragmentManager()
                            .beginTransaction();
                }
                Fragment fragment = getChildFragmentManager().findFragmentByTag(name);
                if (fragment != null) {
                    return fragment;
                }
                switch (position) {
                    case 0:
                        return MainFragment.newInstance();
                    case 1:
                        return KnowFragment.newInstance();
                    case 2:
                        return NavFragment.newInstance();
                    case 3:
                    default:
                        return MineFragment.newInstance();
                }
            }

//            @Override
//            public CharSequence getPageTitle(int position) {
//                switch (position) {
//                    case 0:
//                        return "TabSegment";
//                    case 1:
//                        return "CollapsingTopBar";
//                    case 2:
//                        return "About";
//                    case 3:
//                    default:
//                        return "ViewPager";
//                }
//            }

            @Override
            protected void populate(ViewGroup container, Object item, int position) {
                String name = makeFragmentName(container.getId(), position);
                if (mCurrentTransaction == null) {
                    mCurrentTransaction = getChildFragmentManager()
                            .beginTransaction();
                }
                Fragment fragment = getChildFragmentManager().findFragmentByTag(name);
                if (fragment != null) {
                    mCurrentTransaction.attach(fragment);
                    if (fragment.getView() != null && fragment.getView().getWidth() == 0) {
                        fragment.getView().requestLayout();
                    }
                } else {
                    fragment = (Fragment) item;
                    mCurrentTransaction.add(container.getId(), fragment, name);
                }
                if (fragment != mCurrentPrimaryItem) {
                    fragment.setMenuVisibility(false);
                    fragment.setUserVisibleHint(false);
                }
            }

            @Override
            protected void destroy(ViewGroup container, int position, Object object) {
                if (mCurrentTransaction == null) {
                    mCurrentTransaction = getChildFragmentManager()
                            .beginTransaction();
                }
                mCurrentTransaction.detach((Fragment) object);
            }

            @Override
            public void startUpdate(@NonNull ViewGroup container) {
                super.startUpdate(container);
                if (container.getId() == View.NO_ID) {
                    throw new IllegalStateException("ViewPager with adapter " + this
                            + " requires a view id");
                }
            }

            @Override
            public void finishUpdate(@NonNull ViewGroup container) {
                super.finishUpdate(container);
                if (mCurrentTransaction != null) {
                    mCurrentTransaction.commitNowAllowingStateLoss();
                    mCurrentTransaction = null;
                }
            }

            @Override
            public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                super.setPrimaryItem(container, position, object);
                Fragment fragment = (Fragment) object;
                if (fragment != mCurrentPrimaryItem) {
                    if (mCurrentPrimaryItem != null) {
                        mCurrentPrimaryItem.setMenuVisibility(false);
                        mCurrentPrimaryItem.setUserVisibleHint(false);
                    }
                    if (fragment != null) {
                        fragment.setMenuVisibility(true);
                        fragment.setUserVisibleHint(true);
                    }
                    mCurrentPrimaryItem = fragment;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == ((Fragment) object).getView();
            }
        };
        mDataBinding.homeFragmentPager.setAdapter(pagerAdapter);
        mDataBinding.homeFragmentTabs.setupWithViewPager(mDataBinding.homeFragmentPager, false);
    }

    private String makeFragmentName(int id, int position) {
        return "QDFitSystemWindowViewPagerFragment:" + id + ":" + position;
    }

    private void initTabs() {
        mHomeTabs = mDataBinding.homeFragmentTabs;
//        mHomeTabs.addOnTabSelectedListener(this);

        int normalColor = QMUIResHelper.getAttrColor(mContext, R.attr.qmui_config_color_gray_6);
        int selectColor = QMUIResHelper.getAttrColor(mContext, R.attr.qmui_config_color_blue);
        mHomeTabs.setDefaultNormalColor(normalColor);
        mHomeTabs.setDefaultSelectedColor(selectColor);

        QMUITabSegment.Tab main = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(mContext, R.drawable.icon_tabbar_main),
                ContextCompat.getDrawable(mContext, R.drawable.icon_tabbar_main_selected),
                getString(R.string.str_tabbar_main), false
        );

        QMUITabSegment.Tab know = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(mContext, R.drawable.icon_tabbar_know),
                ContextCompat.getDrawable(mContext, R.drawable.icon_tabbar_know_selected),
                getString(R.string.str_tabbar_know), false
        );

        QMUITabSegment.Tab nav = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(mContext, R.drawable.icon_tabbar_nav),
                ContextCompat.getDrawable(mContext, R.drawable.icon_tabbar_nav_selected),
                getString(R.string.str_tabbar_nav), false
        );

        QMUITabSegment.Tab mine = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(mContext, R.drawable.icon_tabbar_mine),
                ContextCompat.getDrawable(mContext, R.drawable.icon_tabbar_mine_selected),
                getString(R.string.str_tabbar_mine), false
        );

        mHomeTabs.addTab(main)
                .addTab(know)
                .addTab(nav)
                .addTab(mine);
    }

    /**
     * 加载默认的Fragment
     */
    private void setDefaultFragment() {
        if (null == mMainFragment) {
            mMainFragment = MainFragment.newInstance();
        }
        if (!mMainFragment.isAdded()) {
            getChildFragmentManager()
                    .beginTransaction()
                    .add(CONTAINER_ID, mMainFragment, mMainFragment.getClass().getSimpleName())
                    .commit();
            mCurrentFragment = mMainFragment;
        }
        mHomeTabs.selectTab(0);
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }

//    @Override
//    public void onTabSelected(int index) {
////        changeFragmentByIndex(index);
//    }

    /**
     * 根据下标切换
     */
    private void changeFragmentByIndex(int index) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        Fragment fragment = null;
        switch (index) {
            case 0:
                if (null == mMainFragment) mMainFragment = MainFragment.newInstance();
                fragment = mMainFragment;
                break;
            case 1:
                if (null == mKnowFragment) mKnowFragment = KnowFragment.newInstance();
                fragment = mKnowFragment;
                break;
            case 2:
                if (null == mNavFragment) mNavFragment = NavFragment.newInstance();
                fragment = mNavFragment;
                break;
            case 3:
                if (null == mMineFragment) mMineFragment = MineFragment.newInstance();
                fragment = mMineFragment;
                break;
        }
        if (null == fragment) return;
        showOrHideFragment(transaction, fragment);
    }

    /**
     * 显示隐藏Fragment
     */
    private void showOrHideFragment(FragmentTransaction transaction, Fragment fragment) {
        if (mCurrentFragment != fragment) {
            if (!fragment.isAdded()) {
                transaction.hide(mCurrentFragment)
                        .add(CONTAINER_ID, fragment)
                        .commit();
            } else {
                transaction.hide(mCurrentFragment)
                        .show(fragment)
                        .commit();
            }
            mCurrentFragment = fragment;
        }
    }

//    @Override
//    public void onTabUnselected(int index) {
//
//    }
//
//    @Override
//    public void onTabReselected(int index) {
//
//    }
//
//    @Override
//    public void onDoubleTap(int index) {
//
//    }
}
