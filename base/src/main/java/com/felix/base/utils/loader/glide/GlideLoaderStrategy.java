package com.felix.base.utils.loader.glide;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.felix.base.utils.LogUtils;
import com.felix.base.utils.loader.ILoaderStrategy;
import com.felix.base.utils.loader.ImageContextWrapper;
import com.felix.base.utils.loader.LoaderOptions;

import androidx.annotation.Nullable;


public class GlideLoaderStrategy implements ILoaderStrategy {
    public static final String TAG = "GlideLoaderStrategy";
    @Override
    public void loadImage(LoaderOptions options) {
        ImageContextWrapper imageContextWrapper = options.getImageContextWrapper();
        GlideRequests glideRequests;
        GlideRequest<Drawable> glideRequest;


        if(imageContextWrapper.getContext() !=null){
            glideRequests = GlideApp.with(imageContextWrapper.getContext().get());
        }else if(imageContextWrapper.getFragment() != null){
            glideRequests = GlideApp.with(imageContextWrapper.getFragment().get());
        }else {
            LogUtils.e(TAG, "imageContextWrapper.getContext() == null || imageContextWrapper.getFragment() == null");
            return;
        }

        if(options.getDrawableResId()>0){
            glideRequest = glideRequests.load(options.getDrawableResId());
        }else if(options.getFile() != null){
            glideRequest = glideRequests.load(options.getFile());
        }else if(options.getUri() != null){
            glideRequest = glideRequests.load(options.getUri());
        }else {
            glideRequest = glideRequests.load(options.getUrl());
        }

        if(options.getPlaceholderResId()>0){
            glideRequest.placeholder(options.getPlaceholderResId());
        }

        if(options.getScaleType() != null){
            ImageView.ScaleType scaleType = options.getScaleType();
            switch (scaleType){
                case CENTER:
                case FIT_XY:
                case MATRIX:
                case FIT_END:
                case FIT_START:
                case FIT_CENTER:
                    glideRequest.fitCenter();
                    break;
                case CENTER_CROP:
                    glideRequest.centerCrop();
                    break;
                case CENTER_INSIDE:
                    glideRequest.centerInside();
                    break;
            }
        }
        if(options.isCircle()){
            glideRequest.transform(new CircleCrop());
        }else if(options.getRoundedCorners() > 0) {
            if(options.getScaleType() != null && options.getScaleType() == ImageView.ScaleType.CENTER_CROP) {
                //glide v4 centercrop与圆角冲突，必须这样处理
                glideRequest.transforms(new CenterCrop(), new RoundedCorners(options.getRoundedCorners()));
            }else {
                glideRequest.transform(new RoundedCorners(options.getRoundedCorners()));
            }
        }

        if(options.getOverride() != null){
            glideRequest.override(options.getOverride().first, options.getOverride().second);
        }
        if(options.getTargetView() == null) {
            LogUtils.e(TAG, "options.getTargetView() == null");
            return;
        }
        glideRequest.listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                e.printStackTrace();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        });
        glideRequest.into(options.getTargetView());
    }

    @Override
    public void clearMemoryCache() {

    }

    @Override
    public void clearDiskCache() {

    }
}
