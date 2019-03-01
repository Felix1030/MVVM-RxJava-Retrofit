package com.felix.know.adapter;

import com.felix.know.fragments.KnowListFragment;
import com.felix.know.model.KnowBaseModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class KnowPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();
    private List<KnowBaseModel> mModels;

    public KnowPagerAdapter(@NonNull FragmentManager fm, List<KnowBaseModel> models) {
        super(fm);
        mFragments.clear();
        mModels = models;
        for (KnowBaseModel model : models) {
            mFragments.add(KnowListFragment.newInstance(String.valueOf(model.getId())));
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mModels.get(position).getName();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
