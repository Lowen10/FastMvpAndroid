package com.app.framework.mvp.impl;

import com.app.framework.mvp.RepositoryManager;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 * Created by lowen on 14/08/2017 21:42
 */
@Singleton
public class RepositoryManagerImpl implements RepositoryManager {
    private Lazy<Retrofit> mRetrofit;
    private Lazy<RxCache> mRxCache;
    private final Map<String, Object> mRetrofitServiceCache = new HashMap<>();
    private final Map<String, Object> mCacheServiceCache = new HashMap<>();

    @Inject
    public RepositoryManagerImpl(Lazy<Retrofit> retrofit, Lazy<RxCache> rxCache) {
        this.mRetrofit = retrofit;
        this.mRxCache = rxCache;
    }

    @Override
    public <T> T obtainRetrofitService(Class<T> service) {
        T retrofitService;
        synchronized (mRetrofitServiceCache) {
            retrofitService = (T) mRetrofitServiceCache.get(service.getName());
            if (retrofitService == null) {
                retrofitService = mRetrofit.get().create(service);
                mRetrofitServiceCache.put(service.getName(), retrofitService);
            }
        }
        return retrofitService;
    }

    @Override
    public <T> T obtainRxCacheService(Class<T> cache) {
        T cacheService;
        synchronized (mCacheServiceCache) {
            cacheService = (T) mCacheServiceCache.get(cache.getName());
            if (cacheService == null) {
                cacheService = mRxCache.get().using(cache);
                mCacheServiceCache.put(cache.getName(), cacheService);
            }
        }
        return cacheService;
    }
}
