package com.felix.base.http;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;

public final class NetWorkConfiguration {

    /**
     * 默认缓存
     */
    private boolean isCache;
    //    是否进行磁盘缓存
    private boolean isDiskCache;
    //        是否进行内存缓存
    private boolean isMemoryCache;
    //    内存缓存时间单位S （默认为60s）
    private int memoryCacheTime;

    //    本地缓存时间单位S (默认为4周)
    private int diskCacheTime;

    //    缓存本地大小 单位字节（默认为30M）
    private int maxDiskCacheSize;
    //      缓存路径
    private Cache diskCache;

    //     设置超时时间
    private int connectTimeout;
    //    设置网络最大连接数
    private ConnectionPool connectionPool;
    //设置HttpS客户端带证书访问
    private InputStream[] certificates;
    public Context context;

    //设置网络BaseUrl地址
    private String baseUrl;

    //读取网络数据的超时时间
    private int readTimeout;
    private int writeTimeOut;

    private boolean isLoadDiskCache;
    private boolean isLoadMemoryCache;

    public NetWorkConfiguration(Context content) {
        this.isCache = false;
        this.isDiskCache = false;
        this.isMemoryCache = false;
        this.memoryCacheTime = 60;
        this.diskCacheTime = 60 * 60 * 24 * 28;
        this.maxDiskCacheSize = 30 * 1024 * 1024;
        this.context = content;//content.getApplicationContext();
        this.diskCache = new Cache(new File(this.context.getCacheDir(), "network"), maxDiskCacheSize);
        this.connectTimeout = 25;
        this.writeTimeOut = 25;
        this.readTimeout = 25;
        this.connectionPool = new ConnectionPool(50, 60, TimeUnit.SECONDS);
        certificates = null;
        baseUrl = null;
        isLoadDiskCache = true;
        isLoadMemoryCache = false;
    }

    public NetWorkConfiguration setLoadDiskCache(boolean loadDiskCache) {
        isLoadDiskCache = loadDiskCache;
        return this;
    }

    public NetWorkConfiguration setLoadMemoryCache(boolean loadMemoryCache) {
        isLoadMemoryCache = loadMemoryCache;
        return this;
    }

    public boolean isLoadDiskCache() {
        return isLoadDiskCache;
    }

    public boolean isLoadMemoryCache() {
        return isLoadMemoryCache;
    }

    /**
     * 设置是否进行缓存
     *
     * @param iscache
     * @return
     */
    public NetWorkConfiguration isCache(boolean iscache) {
        this.isCache = iscache;
        return this;
    }

    public boolean getIsCache() {
        return this.isCache;
    }

    /**
     * 是否进行磁盘缓存
     *
     * @param diskcache
     * @return
     */
    public NetWorkConfiguration isDiskCache(boolean diskcache) {
        this.isDiskCache = diskcache;
        return this;
    }

    public boolean getIsDiskCache() {
        return this.isDiskCache;
    }

    /**
     * 是否进行内存缓存
     *
     * @param memorycache
     * @return
     */
    public NetWorkConfiguration isMemoryCache(boolean memorycache) {
        this.isMemoryCache = memorycache;
        return this;
    }

    public boolean getIsMemoryCache() {
        return this.isMemoryCache;
    }

    /**
     * 设置内存缓存时间
     *
     * @param memorycachetime
     * @return
     */
    public NetWorkConfiguration memoryCacheTime(int memorycachetime) {
        if (memorycachetime <= 0) {

            Log.e("NetWorkConfiguration", " configure memoryCacheTime  exception!");
            return this;
        }
        this.memoryCacheTime = memorycachetime;
        return this;
    }

    public int getmemoryCacheTime() {
        return this.memoryCacheTime;
    }

    /**
     * 设置本地缓存时间
     *
     * @param diskcahetime
     * @return
     */
    public NetWorkConfiguration diskCacheTime(int diskcahetime) {
        if (diskcahetime <= 0) {
            Log.e("NetWorkConfiguration", " configure diskCacheTime  exception!");
            return this;
        }
        this.diskCacheTime = diskcahetime;
        return this;
    }

    public int getDiskCacheTime() {
        return this.diskCacheTime;
    }

    /**
     * 设置本地缓存路径以及 缓存大小
     *
     * @param saveFile         本地路径
     * @param maxDiskCacheSize 大小
     * @return
     */
    public NetWorkConfiguration diskCaChe(File saveFile, int maxDiskCacheSize) {
        if (!saveFile.exists() && maxDiskCacheSize <= 0) {
            Log.e("NetWorkConfiguration", " configure connectTimeout  exception!");
            return this;
        }
        diskCache = new Cache(saveFile, maxDiskCacheSize);
        return this;
    }

    public Cache getDiskCache() {
        return this.diskCache;
    }

    /**
     * 设置网络超时时间
     *
     * @param timeout
     * @return
     */
    public NetWorkConfiguration connectTimeOut(int timeout) {
        if (connectTimeout <= 0) {
            Log.e("NetWorkConfiguration", " configure connectTimeout  exception!");
            return this;
        }
        this.connectTimeout = timeout;
        return this;
    }

    public int getConnectTimeOut() {
        return this.connectTimeout;
    }

    public int getReadTimeOut() {
        return this.readTimeout;
    }

    public int getWriteTimeOut() {
        return this.writeTimeOut;
    }

    /**
     * 设置网络线程池
     *
     * @param connectionCount 线程个数
     * @param connectionTime  连接时间
     * @param unit            时间单位
     * @return
     */
    public NetWorkConfiguration connectionPool(int connectionCount, int connectionTime, TimeUnit unit) {
        /**
         *    线程池 线程个数和连接时间设置过小
         */
        if (connectionCount <= 0 && connectionTime <= 0) {
            Log.e("NetWorkConfiguration", " configure connectionPool  exception!");
            return this;
        }
        this.connectionPool = new ConnectionPool(connectionCount, connectionTime, unit);
        return this;
    }

    public ConnectionPool getConnectionPool() {
        return this.connectionPool;
    }

    /**
     * 设置Https客户端带证书访问
     *
     * @param certificates
     * @return
     */
    public NetWorkConfiguration certificates(InputStream... certificates) {
        if (certificates != null) {
            this.certificates = certificates;
        } else {

            Log.e("NetWorkConfiguration", "no  certificates");
        }
        return this;
    }

    public InputStream[] getCertificates() {
        return this.certificates;
    }

    /**
     * 设置网络BaseUrl地址
     *
     * @param url
     * @return
     */
    public NetWorkConfiguration baseUrl(String url) {
        if (url != null) {
            this.baseUrl = url;
        } else {
            Log.e("NetWorkConfiguration", "NetWorkConfiguration no  baseUrl");
        }
        return this;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    @Override
    public String toString() {
        return "NetWorkConfiguration{" +
                "isCache=" + isCache +
                ", isDiskCache=" + isDiskCache +
                ", isMemoryCache=" + isMemoryCache +
                ", memoryCacheTime=" + memoryCacheTime +
                ", diskCacheTime=" + diskCacheTime +
                ", maxDiskCacheSize=" + maxDiskCacheSize +
                ", diskCache=" + diskCache +
                ", connectTimeout=" + connectTimeout +
                ", connectionPool=" + connectionPool +
                ", certificates=" + Arrays.toString(certificates) +
                ", context=" + context +
                ", baseUrl='" + baseUrl + '\'' +
                '}';
    }

    private List<Interceptor> mInterceptors = new ArrayList<>();

    public NetWorkConfiguration addInterceptor(Interceptor interceptor) {
        mInterceptors.add(interceptor);
        return this;
    }

    public List<Interceptor> getInterceptors() {
        return mInterceptors;
    }
}
