package com.app.framework.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.List;

import javax.inject.Inject;

public class LifecycleManager implements AppLifecycle {
    private Application mApplication;
    private List<ActivityLifecycle> mActivityLifecycles;
    private List<FragmentLifecycle> mFragmentLifecycles;
    private List<AppLifecycle> mAppLifecycles;

    @Inject
    public LifecycleManager(Application application,
                            List<ActivityLifecycle> activityLifecycles,
                            List<FragmentLifecycle> fragmentLifecycles,
                            List<AppLifecycle> appLifecycles) {
        this.mApplication = application;
        this.mActivityLifecycles = activityLifecycles;
        this.mFragmentLifecycles = fragmentLifecycles;
        this.mAppLifecycles = appLifecycles;
        initActivityLifecycles();
        initFragmentLifecycles();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            initAppLifecycles();
        }
    }

    private void initAppLifecycles() {
        mApplication.registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
            }

            @Override
            public void onLowMemory() {
                for (AppLifecycle appLifecycle : mAppLifecycles) {
                    appLifecycle.onTrimMemory(mApplication, ComponentCallbacks2.TRIM_MEMORY_COMPLETE);
                }
            }
        });
    }


    private void initActivityLifecycles() {
        for (ActivityLifecycle activityLifecycle : mActivityLifecycles) {
            mApplication.registerActivityLifecycleCallbacks(activityLifecycle);
        }
    }

    private void initFragmentLifecycles() {
        mApplication.registerActivityLifecycleCallbacks(new ActivityLifecycle() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                super.onActivityCreated(activity, savedInstanceState);
                if (activity instanceof FragmentActivity) {
                    FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                    for (FragmentLifecycle fragmentLifecycle : mFragmentLifecycles) {
                        fragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycle, true);
                    }
                }
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                super.onActivityDestroyed(activity);
                if (activity instanceof FragmentActivity) {
                    FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                    for (FragmentLifecycle fragmentLifecycle : mFragmentLifecycles) {
                        fragmentManager.unregisterFragmentLifecycleCallbacks(fragmentLifecycle);
                    }
                }
            }
        });
    }

    @Override
    public void attachBaseContext(Context base) {
        for (AppLifecycle appLifecycle : mAppLifecycles) {
            appLifecycle.attachBaseContext(base);
        }
    }

    @Override
    public void onCreate(Application application) {
        for (AppLifecycle appLifecycle : mAppLifecycles) {
            appLifecycle.onCreate(application);
        }
    }

    @Override
    public void onTrimMemory(Application application, int level) {
        for (AppLifecycle appLifecycle : mAppLifecycles) {
            appLifecycle.onTrimMemory(mApplication, level);
        }
    }
}
