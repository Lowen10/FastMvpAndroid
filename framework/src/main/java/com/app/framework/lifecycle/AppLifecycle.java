package com.app.framework.lifecycle;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by lowen on 18/07/2017 17:43
 */

public interface AppLifecycle {
    /**
     * Application 生命周期 attachBaseContext()
     *
     * @param base
     */
    void attachBaseContext(Context base);

    /**
     * Application生命周期 onCreate()
     *
     * @param application
     */
    void onCreate(Application application);

    /**
     * Application 生命周期,参数请看{@link Application#onTrimMemory(int)}
     *
     * @param application
     * @param level
     */
    void onTrimMemory(Application application, int level);
}
