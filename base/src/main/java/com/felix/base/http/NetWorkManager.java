package com.felix.base.http;

import com.felix.base.utils.LogUtils;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * BaseApplication中初始化
 * // 1 设置网络请求相关参数
 * NetWorkManager.initConfiguration(configuration); // 初始化网络配置
 *
 * // 2 设置默认的URL 以及需要动态配置的URL 有新的URL 则直接在初始化的时候putDomain
 * ProxyUrlConstants.setGlobalDomain(NetWorkApi.baseUrl);
 * ProxyUrlConstants.putDomain(C2C_DOMAIN_NAME,NetWorkApi.C2cUrl);
 * ProxyUrlConstants.putDomain(IDCS_DOMAIN_NAME,NetWorkApi.IDCS_URL);
 * ProxyUrlConstants.putDomain(ALLIANCE_DOMAIN_NAME,NetWorkApi.ALLIANCE_URL);
 *
 * // 3 使用方式  在跟主BaseUrl不同的接口上面配置相应的header就可以了  如下 配置C2C的
 * @Headers({ProxyUrlConstants.DOMAIN_NAME_HEADER + ProxyUrlConstants.C2C_DOMAIN_NAME})
 * @Multipart
 * @POST("/api/Appeal/BuyerUploadPayCertificate")
 * Flowable<HttpResponse < Object>> uploadPayCertificate(@Part List<MultipartBody.Part> parts);
 */
public class NetWorkManager {

    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;
    private static NetWorkConfiguration mNetWorkConfiguration;

    private static class SingletonHolder {
        private static final NetWorkManager INSTANCE = new NetWorkManager();
    }

    public static NetWorkManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private NetWorkManager() {
        if (null == mNetWorkConfiguration) {
            throw new IllegalStateException("需要先initConfiguration");
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        boolean isHasCache = mNetWorkConfiguration.getIsCache();
        builder.connectTimeout(mNetWorkConfiguration.getConnectTimeOut(), TimeUnit.SECONDS)
                .writeTimeout(mNetWorkConfiguration.getWriteTimeOut(),TimeUnit.SECONDS)
                .readTimeout(mNetWorkConfiguration.getReadTimeOut(), TimeUnit.SECONDS)
                .connectionPool(mNetWorkConfiguration.getConnectionPool())
                .retryOnConnectionFailure(true);
        if (isHasCache) { // 缓存
            builder.cache(mNetWorkConfiguration.getDiskCache());
        }
        if (mNetWorkConfiguration.getCertificates() != null) {
            builder.sslSocketFactory(HttpsUtils.getSslSocketFactory(mNetWorkConfiguration.getCertificates(), null, null));
        }

        for (Interceptor interceptor : mNetWorkConfiguration.getInterceptors()) {
            builder.addInterceptor(interceptor);
        }

        mOkHttpClient = builder.build();

        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(mNetWorkConfiguration.getBaseUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 获取Services
     * @param service
     * @param <T>
     * @return
     */
    public  <T> T  builder(Class<T> service) {
        if (null == service) {
            throw new RuntimeException("service can not be null!");
        }
        T t = this.mRetrofit.create(service);
        return t;
    }

    /**
     * 设置配置参数
     * @param netWorkConfiguration
     */
    public static void initConfiguration(@NonNull NetWorkConfiguration netWorkConfiguration) {
        if (mNetWorkConfiguration != null){
            LogUtils.e("init多次无效");
        }
        mNetWorkConfiguration = netWorkConfiguration;
    }

    public void updateTimeout(int second){
        setDynamicTimeout(second*1000);
    }

    public void resetTimeout(){
        setDynamicTimeout(mNetWorkConfiguration.getConnectTimeOut()*1000);
    }

    /**
     * 动态设置超时时间
     */
    private void setDynamicTimeout(int timeout) {
        try {
            //1、private final okhttp3.Call.Factory callFactory;   Retrofit 的源码 构造方法中
            Field callFactoryField = mRetrofit.getClass().getDeclaredField("callFactory");
            callFactoryField.setAccessible(true);
            //2、callFactory = new OkHttpClient();   Retrofit 的源码 build()中
            OkHttpClient client = (OkHttpClient) callFactoryField.get(mRetrofit);
            //3、OkHttpClient(Builder builder)     OkHttpClient 的源码 构造方法中
            Field connectTimeoutField = client.getClass().getDeclaredField("connectTimeout");
            connectTimeoutField.setAccessible(true);
            connectTimeoutField.setInt(client, timeout);
            Field readTimeoutField = client.getClass().getDeclaredField("readTimeout");
            readTimeoutField.setAccessible(true);
            readTimeoutField.setInt(client, timeout);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
