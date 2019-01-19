package com.app.framework.mvp.module;

import com.app.framework.configuration.RxCacheBuilder;
import com.app.framework.http.handler.HttpClientHandler;
import com.app.framework.lifecycle.ActivityLifecycle;
import com.app.framework.lifecycle.AppLifecycle;
import com.app.framework.lifecycle.FragmentLifecycle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by lowen on 14/08/2017 21:42
 */
@Module
public class ConfigModule {

    private Builder builder;

    private ConfigModule(Builder builder) {
        this.builder = builder;
    }

    @Singleton
    @Provides
    HttpClientHandler provideErrorHandler() {
        if (builder.errorHandlerBuilder != null) {
            return builder.errorHandlerBuilder.build();
        } else {
            return null;
        }
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient httpClient) {
        Retrofit.Builder rBuilder = builder.retrofitBuilder;
        rBuilder.client(httpClient);
        return rBuilder.build();
    }

    @Singleton
    @Provides
    RxCache provideRxCache() {
        if (builder.rxCacheBuilder != null) {
            return builder.rxCacheBuilder.build();
        }
        return null;
    }

    @Singleton
    @Provides
    CookieJar provideCookieJar(OkHttpClient client) {
        return client.cookieJar();
    }

    @Singleton
    @Provides
    OkHttpClient provideClient() {
        return builder.okHttpClientBuilder.build();
    }

    @Singleton
    @Provides
    List<ActivityLifecycle> provideActivityLifecycles() {
        return builder.activityLifecycles;
    }

    @Singleton
    @Provides
    List<FragmentLifecycle> provideFragmentLifecycles() {
        return builder.fragmentLifecycles;
    }

    @Singleton
    @Provides
    public List<AppLifecycle> provideAppLifecycles() {
        return builder.appLifecycles;
    }


    public static class Builder {
        private Retrofit.Builder retrofitBuilder;
        private OkHttpClient.Builder okHttpClientBuilder;
        private HttpClientHandler.Builder errorHandlerBuilder;
        private RxCacheBuilder rxCacheBuilder;
        private ArrayList<ActivityLifecycle> activityLifecycles;
        private ArrayList<FragmentLifecycle> fragmentLifecycles;
        private ArrayList<AppLifecycle> appLifecycles;

        public Builder setActivityLifecycles(ArrayList<ActivityLifecycle> activityLifecycles) {
            this.activityLifecycles = activityLifecycles;
            return this;
        }

        public Builder setFragmentLifecycles(ArrayList<FragmentLifecycle> fragmentLifecycles) {
            this.fragmentLifecycles = fragmentLifecycles;
            return this;
        }

        public Builder setAppLifecycles(ArrayList<AppLifecycle> appLifecycles) {
            this.appLifecycles = appLifecycles;
            return this;
        }

        public Builder setRetrofitBuilder(Retrofit.Builder retrofitBuilder) {
            this.retrofitBuilder = retrofitBuilder;
            return this;
        }

        public Builder setOkHttpClientBuilder(OkHttpClient.Builder okHttpClientBuilder) {
            this.okHttpClientBuilder = okHttpClientBuilder;
            return this;
        }

        public Builder setErrorHandlerBuilder(HttpClientHandler.Builder errorHandlerBuilder) {
            this.errorHandlerBuilder = errorHandlerBuilder;
            return this;
        }

        public Builder setRxCacheBuilder(RxCacheBuilder rxCacheBuilder) {
            this.rxCacheBuilder = rxCacheBuilder;
            return this;
        }

        public ConfigModule build() {
            if (okHttpClientBuilder == null) {
                throw new IllegalArgumentException("OkHttpClientBuilder is null.");
            }
            if (retrofitBuilder == null) {
                throw new IllegalArgumentException("retrofitBuilder is null.");
            }
            return new ConfigModule(this);
        }
    }
}
