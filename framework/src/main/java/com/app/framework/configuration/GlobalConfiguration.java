package com.app.framework.configuration;

import android.content.Context;

import com.app.framework.http.handler.HttpClientHandler;
import com.app.framework.lifecycle.ActivityLifecycle;
import com.app.framework.lifecycle.AppLifecycle;
import com.app.framework.lifecycle.FragmentLifecycle;
import com.app.framework.mvp.module.ConfigModule;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public abstract class GlobalConfiguration {

    public GlobalConfiguration() {
    }

    /**
     * OkHttpClient 配置
     *
     * @param context
     * @param builder OkHTTPClient 构造器
     */
    protected abstract void okHttpClientConfiguration(Context context, OkHttpClient.Builder builder);

    /**
     * RxCache 配置
     *
     * @param context
     * @param builder RxCache 构造器
     */
    protected abstract void rxCacheConfiguration(Context context, RxCacheBuilder builder);

    /**
     * Retrofit 配置 <br/>
     * 可以不用配置OKHttpClient 可以通过{@link #okHttpClientConfiguration(Context, OkHttpClient.Builder)}进行配置
     *
     * @param context
     * @param builder Retrofit 构造器
     */
    protected abstract void retrofitConfiguration(Context context, Retrofit.Builder builder);

    /**
     * HttpClientHandler 配置 <br/>
     * 可以通过方法配置Http请求处理Handler<br/>
     *
     * @param context
     * @param builder HttpClientHandler 构造器
     */
    protected abstract void httpErrorHandlerConfiguration(Context context, HttpClientHandler.Builder builder);

    /**
     * App lifecycle 配置
     * <p>
     * 调用方式 appLifecycles.add(new AppLifecycle());
     *
     * @param context
     * @param appLifecycles App 生命周期回调列表
     */
    protected void appLifecycleConfiguration(Context context, List<AppLifecycle> appLifecycles) {
    }

    /**
     * Activity lifecycle  配置
     * <p>
     * 调用方式 activityLifecycles.add(new ActivityLifecycle());
     *
     * @param context
     * @param activityLifecycles Activity 生命周期回调列表
     */
    protected void activityLifecycleConfiguration(Context context, List<ActivityLifecycle> activityLifecycles) {
    }

    /**
     * Fragment lifecycle  配置
     * <p>
     * 调用方式 fragmentLifecycles.add(new FragmentLifecycle());
     */
    protected void fragmentLifecycleConfiguration(Context context, List<FragmentLifecycle> fragmentLifecycles) {
    }

    private OkHttpClient.Builder defaultOkHttpClientBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return builder;
    }

    private RxCacheBuilder defaultRxCacheBuilder() {
        RxCacheBuilder builder = new RxCacheBuilder();
        return builder;
    }

    private Retrofit.Builder defaultRetrofitBuilder() {
        Retrofit.Builder builder = new Retrofit.Builder();
        return builder;
    }


    private HttpClientHandler.Builder defaultHttpErrorHandlerBuilder(Context context) {
        HttpClientHandler.Builder builder = new HttpClientHandler.Builder();
        builder.with(context);
        return builder;
    }

    public final ConfigModule buildConfigModule(Context context) {
        ConfigModule.Builder builder = new ConfigModule.Builder();

        HttpClientHandler.Builder httpErrorHandlerBuilder = defaultHttpErrorHandlerBuilder(context);
        httpErrorHandlerConfiguration(context, httpErrorHandlerBuilder);
        builder.setErrorHandlerBuilder(httpErrorHandlerBuilder);

        OkHttpClient.Builder okHttpClientBuilder = defaultOkHttpClientBuilder();
        okHttpClientConfiguration(context, okHttpClientBuilder);
        builder.setOkHttpClientBuilder(okHttpClientBuilder);

        Retrofit.Builder retrofitBuilder = defaultRetrofitBuilder();
        retrofitConfiguration(context, retrofitBuilder);
        builder.setRetrofitBuilder(retrofitBuilder);

        RxCacheBuilder rxCacheBuilder = defaultRxCacheBuilder();
        rxCacheConfiguration(context, rxCacheBuilder);
        builder.setRxCacheBuilder(rxCacheBuilder);

        ArrayList<AppLifecycle> appLifecycles = new ArrayList<>();
        appLifecycleConfiguration(context, appLifecycles);
        builder.setAppLifecycles(appLifecycles);

        ArrayList<ActivityLifecycle> activityLifecycles = new ArrayList<>();
        activityLifecycleConfiguration(context, activityLifecycles);
        builder.setActivityLifecycles(activityLifecycles);

        ArrayList<FragmentLifecycle> fragmentLifecycles = new ArrayList<>();
        fragmentLifecycleConfiguration(context, fragmentLifecycles);
        builder.setFragmentLifecycles(fragmentLifecycles);

        return builder.build();
    }
}
