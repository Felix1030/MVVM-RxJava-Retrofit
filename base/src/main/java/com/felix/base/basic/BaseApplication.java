package com.felix.base.basic;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.felix.base.R;
import com.felix.base.http.NetWorkConfiguration;
import com.felix.base.http.NetWorkManager;
import com.felix.base.utils.LogUtils;
import com.felix.base.utils.loader.ImageLoader;
import com.felix.base.utils.loader.glide.GlideLoaderStrategy;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.felix.base.http.URLS.BASE_URL;

public class BaseApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    static {
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setDisableContentWhenLoading(true);
                layout.setDisableContentWhenRefresh(true);
            }
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                ClassicsHeader header = new ClassicsHeader(context);
//                header.setEnableLastTime(false);
//                header.setArrowDrawable(null);
                header.setAccentColor(context.getResources().getColor(R.color.c_B2051223));
//                header.setDrawableMarginRight(-context.getResources().getDimension(R.dimen.dp2));
                header.setTextSizeTitle(10);
                return header;
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                ClassicsFooter classicsFooter = new ClassicsFooter(context);
//                classicsFooter.setArrowDrawable(null);
//                classicsFooter.setProgressDrawable(null);
                classicsFooter.setTextSizeTitle(10);
                classicsFooter.setAccentColor(ContextCompat.getColor(context,R.color.c_FFC0C7CD));
                return new ClassicsFooter(context);
            }
        });
    }

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
