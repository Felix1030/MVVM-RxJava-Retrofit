package com.felix.base.utils;

import android.widget.ImageView;

import com.felix.base.basic.BaseApplication;
import com.felix.base.utils.loader.ImageLoader;

import androidx.databinding.BindingAdapter;

public class LocalDataBindingUtils {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView,String url) {
        ImageLoader.with(BaseApplication.getContext())
                .load(url)
                .into(imageView);
    }
}
