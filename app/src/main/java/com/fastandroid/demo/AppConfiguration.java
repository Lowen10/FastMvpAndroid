package com.fastandroid.demo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.app.framework.configuration.GlobalConfiguration;
import com.app.framework.configuration.RxCacheBuilder;
import com.app.framework.http.handler.HttpClientHandler;
import com.app.framework.lifecycle.ActivityLifecycle;
import com.app.framework.lifecycle.AppLifecycle;
import com.app.framework.lifecycle.FragmentLifecycle;
import com.google.gson.Gson;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.fastandroid.demo.client.HttpClientErrorListener;
import com.fastandroid.demo.client.HttpClientResponseHandler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppConfiguration extends GlobalConfiguration {


    public AppConfiguration() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(6)        // (Optional) Skips some method invokes in stack trace. Default 5
//        .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("My custom tag")   // (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    @Override
    protected void okHttpClientConfiguration(Context context, OkHttpClient.Builder builder) {
        builder.readTimeout(2, TimeUnit.MINUTES);
        builder.writeTimeout(2, TimeUnit.MINUTES);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("HttpClient", message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);
    }

    @Override
    protected void rxCacheConfiguration(Context context, RxCacheBuilder builder) {
        //建议使用GenericGsonSpeaker
    }

    @Override
    protected void retrofitConfiguration(Context context, Retrofit.Builder builder) {
        builder.baseUrl(BuildConfig.SERVER_HOST);
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder.addConverterFactory(GsonConverterFactory.create(new Gson()));
    }

    @Override
    protected void httpErrorHandlerConfiguration(Context context, HttpClientHandler.Builder builder) {
        builder.setOnHttpErrorListener(new HttpClientErrorListener());
        builder.setOnResponseModelHandler(new HttpClientResponseHandler());
    }

    @Override
    protected void appLifecycleConfiguration(Context context, List<AppLifecycle> appLifecycles) {
        super.appLifecycleConfiguration(context, appLifecycles);
        appLifecycles.add(new AppLifecycle() {
            @Override
            public void attachBaseContext(Context base) {
                System.out.println("=====attachBaseContext======");
            }

            @Override
            public void onCreate(Application application) {
                System.out.println("=====onCreate======");
            }

            @Override
            public void onTrimMemory(Application application, int level) {
                System.out.println("=====onTrimMemory======");
            }
        });
    }

    @Override
    protected void activityLifecycleConfiguration(Context context, List<ActivityLifecycle> activityLifecycles) {
        super.activityLifecycleConfiguration(context, activityLifecycles);
        activityLifecycles.add(new ActivityLifecycle() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                super.onActivityCreated(activity, savedInstanceState);
                System.out.println("=====onActivityCreated======" + activity);
            }
        });
    }

    @Override
    protected void fragmentLifecycleConfiguration(Context context, List<FragmentLifecycle> fragmentLifecycles) {
        super.fragmentLifecycleConfiguration(context, fragmentLifecycles);
        fragmentLifecycles.add(new FragmentLifecycle() {
            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                super.onFragmentCreated(fm, f, savedInstanceState);
                System.out.println("=====onFragmentCreated======" + f);
            }
        });
    }
}
