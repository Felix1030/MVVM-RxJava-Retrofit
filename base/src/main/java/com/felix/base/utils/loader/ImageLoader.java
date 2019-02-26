package com.felix.base.utils.loader;

import android.content.Context;

import androidx.fragment.app.Fragment;

/**
 *
 * @author yiyang
 */
public class ImageLoader {

    private ILoaderStrategy mStrategy;

    private static final class SigletonHolder{
        private static final ImageLoader sInstance = new ImageLoader();
    }

    public static ImageLoader get(){
        return SigletonHolder.sInstance;
    }

    public void setLoaderStrategy(ILoaderStrategy strategy){
        mStrategy = strategy;
    }

    public ILoaderStrategy getStrategy() {
        return mStrategy;
    }

    public static ImageContextWrapper with(Context context){
        return new ImageContextWrapper(context);
    }

    public static ImageContextWrapper with(Fragment fragment){
        return new ImageContextWrapper(fragment);
    }

    public void clearMemoryCache() {
        mStrategy.clearMemoryCache();
    }

    public void clearDiskCache() {
        mStrategy.clearDiskCache();
    }

    public void loadOptions(LoaderOptions options) {
        mStrategy.loadImage(options);
    }
}
