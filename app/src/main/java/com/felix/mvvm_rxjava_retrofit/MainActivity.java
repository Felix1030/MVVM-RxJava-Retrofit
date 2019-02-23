package com.felix.mvvm_rxjava_retrofit;

import android.os.Bundle;

import com.felix.mvvm_rxjava_retrofit.home.HomeFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;

import androidx.fragment.app.Fragment;

public class MainActivity extends QMUIFragmentActivity {

    @Override
    protected int getContextViewId() {
        return R.id.main_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Fragment fragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }
}
