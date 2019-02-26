package com.felix.base.basic;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.felix.base.http.NetWorkConfiguration;
import com.felix.base.http.NetWorkManager;
import com.felix.base.utils.LogUtils;
import com.felix.base.utils.loader.ImageLoader;
import com.felix.base.utils.loader.glide.GlideLoaderStrategy;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

import okhttp3.logging.HttpLoggingInterceptor;

import static com.felix.base.http.URLS.BASE_URL;

public class BaseApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        QMUISwipeBackActivityManager.init(this);

        // 初始化网络请求相关
        NetWorkConfiguration configuration = new NetWorkConfiguration(sContext)
                .baseUrl(BASE_URL)
                .addInterceptor(new HttpLoggingInterceptor(message -> LogUtils.i("OkHttp", message)).setLevel(HttpLoggingInterceptor.Level.BODY));
        NetWorkManager.initConfiguration(configuration);

        // 图片加载
        ImageLoader.get().setLoaderStrategy(new GlideLoaderStrategy());
    }
    public static Context getContext() {
        return sContext;
    }
}
