package com.felix.base.utils.loader;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Pair;
import android.widget.ImageView;

import java.io.File;

/**
 *
 * @author yiyang
 */
public class LoaderOptions {
    private final ImageContextWrapper mImageContextWrapper;
    private int placeholderResId;
    private ImageView targetView;//targetView展示图片
    private String url;
    private int drawableResId;
    /**true标识为圆形*/
    private boolean isCircle;
    private ImageView.ScaleType scaleType;
    private File file;
    private Uri uri;
    /**四边圆角，单位px*/
    private int roundedCorners;
    /**限定宽高,左宽右高*/
    private Pair<Integer, Integer> override;

    public LoaderOptions(ImageContextWrapper imageContextWrapper) {
        mImageContextWrapper = imageContextWrapper;
    }

    public int getPlaceholderResId() {
        return placeholderResId;
    }

    public ImageView getTargetView() {
        return targetView;
    }

    public String getUrl() {
        return url;
    }

    public int getDrawableResId() {
        return drawableResId;
    }

    public ImageContextWrapper getImageContextWrapper() {
        return mImageContextWrapper;
    }

    public LoaderOptions setPlaceholderResId(int placeholderResId) {
        this.placeholderResId = placeholderResId;
        return this;
    }

    public LoaderOptions setTargetView(ImageView targetView) {
        this.targetView = targetView;
        return this;
    }

    public LoaderOptions setUrl(String url) {
        this.url = url;
        return this;
    }

    public LoaderOptions setDrawableResId(int drawableResId) {
        this.drawableResId = drawableResId;
        return this;
    }

    public boolean isCircle() {
        return isCircle;
    }

    public LoaderOptions setCircle(boolean circle) {
        isCircle = circle;
        return this;
    }

    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public LoaderOptions setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    public File getFile() {
        return file;
    }

    public LoaderOptions setFile(File file) {
        this.file = file;
        return this;
    }

    public Uri getUri() {
        return uri;
    }

    public LoaderOptions setUri(Uri uri) {
        this.uri = uri;
        return this;
    }

    public int getRoundedCorners() {
        return roundedCorners;
    }

    public LoaderOptions setRoundedCorners(int roundedCorners) {
        this.roundedCorners = roundedCorners;
        return this;
    }

    public LoaderOptions override(int width, int height) {
        this.override = new Pair<>(width, height);
        return this;
    }

    public Pair<Integer, Integer> getOverride() {
        return override;
    }

    public void into(ImageView targetView) {
        this.targetView = targetView;
        ImageLoader.get().loadOptions(this);
    }

    public static interface RequestListener{
        /**
         * 加载失败
         * @return 返回true将不再处理
         */
        boolean onLoadFaild();

        /**
         * 加载成功
         * @param drawable
         * @return 返回true将不再处理
         */
        boolean onSuccess(Drawable drawable);
    }
}
