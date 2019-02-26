package com.felix.mvvm_rxjava_retrofit;

import android.os.Bundle;
import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.felix.base.utils.ToastUtil;
import com.felix.mvvm_rxjava_retrofit.fragments.home.HomeFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends QMUIFragmentActivity {

    private long mExitTime = 0;

    @Override
    protected int getContextViewId() {
        return R.id.main_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ScreenUtils.isPortrait()) {
            AdaptScreenUtils.adaptHeight(this.getResources(), 375);
        } else {
            AdaptScreenUtils.adaptWidth(this.getResources(), 375);
        }
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Fragment fragment = HomeFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment instanceof HomeFragment) {
            exitApp();
        } else {
            super.onBackPressed();
        }
    }

    public void exitApp() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            ToastUtil.show(getString(R.string.str_press_again_to_exit));
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
