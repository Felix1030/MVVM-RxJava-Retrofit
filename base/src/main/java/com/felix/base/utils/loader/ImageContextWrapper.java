package com.felix.base.utils.loader;

import android.content.Context;

import java.lang.ref.WeakReference;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;

/**
 *
 * @author yiyang
 */
public class ImageContextWrapper {

    private WeakReference<Context> mContext;
    private WeakReference<Fragment> mFragment;

    public ImageContextWrapper(Context context) {
        mContext = new WeakReference<>(context);
    }

    public ImageContextWrapper(Fragment fragment) {
        mFragment = new WeakReference<>(fragment);
    }

    public LoaderOptions load(String path) {
        return new LoaderOptions(this).setUrl(path);
    }

    public LoaderOptions load(@DrawableRes int drawable) {
        return new LoaderOptions(this).setDrawableResId(drawable);
    }

    public WeakReference<Context> getContext() {
        return mContext;
    }

    public WeakReference<Fragment> getFragment() {
        return mFragment;
    }
}
