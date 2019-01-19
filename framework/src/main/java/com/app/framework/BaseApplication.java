package com.app.framework;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

import com.app.framework.common.AppManager;
import com.app.framework.mvp.impl.AppManagerImpl;
import com.app.framework.rxlifecycle.RxLifecycleManager;
import com.squareup.leakcanary.LeakCanary;


/**
 * Created by lowen on 14/08/2017 21:42
 */
public abstract class BaseApplication extends Application {
    private AppDelegate mAppDelegate;
    private static BaseApplication application;

    private AppManager mAppManager;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        this.mAppDelegate = new AppDelegate(base);
        this.mAppDelegate.attachBaseContext(base);
        application = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        this.mAppDelegate.onCreate(this);
        mAppManager = mAppDelegate.getAppManager();
    }

    public AppManager getAppManager() {
        return mAppManager;
    }

    public RxLifecycleManager getRxLifecycleManager() {
        return mAppManager.rxLifecycleManager();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    public static BaseApplication getInstance() {
        return application;
    }

}
