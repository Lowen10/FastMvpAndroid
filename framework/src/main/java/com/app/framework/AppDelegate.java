package com.app.framework;

import android.app.Application;
import android.content.Context;

import com.app.framework.common.AppManager;
import com.app.framework.configuration.GlobalConfiguration;
import com.app.framework.lifecycle.AppLifecycle;
import com.app.framework.mvp.component.AppComponent;
import com.app.framework.mvp.component.DaggerAppComponent;
import com.app.framework.mvp.module.ConfigModule;
import com.app.framework.utils.ManifestParser;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by lowen on 14/08/2017 21:42
 */
public class AppDelegate implements AppLifecycle {

    private AppComponent mAppComponent;

    @Inject
    @Named("RxActivityLifecycle")
    Application.ActivityLifecycleCallbacks mRxActivityLifecycle;
    @Inject
    @Named("ActivityInjectHandler")
    Application.ActivityLifecycleCallbacks mFragmentInjectHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    @Named("LifecycleManager")
    AppLifecycle mAppLifecycle;

    private ConfigModule mConfigModule;

    public AppDelegate(Context context) {
        GlobalConfiguration configuration = getGlobalConfiguration(context);
        mConfigModule = configuration.buildConfigModule(context);
    }

    private GlobalConfiguration getGlobalConfiguration(Context context) {
        return new ManifestParser<GlobalConfiguration>(context).parse(GlobalConfiguration.class.getSimpleName());
    }

    @Override
    public void attachBaseContext(Context base) {
        List<AppLifecycle> appLifecycles = mConfigModule.provideAppLifecycles();
        for (AppLifecycle appLifecycle : appLifecycles) {
            appLifecycle.attachBaseContext(base);
        }
    }

    @Override
    public void onCreate(Application application) {
        mAppComponent = DaggerAppComponent
                .builder()
                .appDelegate(this)
                .application(application)
                .configModule(mConfigModule)
                .build();
        mAppComponent.inject(this);

        //Register RxLifecycle
        application.registerActivityLifecycleCallbacks(mRxActivityLifecycle);
        //init dagger inject handler
        application.registerActivityLifecycleCallbacks(mFragmentInjectHandler);
        //Register app lifecycle
        mAppLifecycle.onCreate(application);
    }

    @Override
    public void onTrimMemory(Application application, int level) {
        mAppLifecycle.onTrimMemory(application, level);
    }

    public AppManager getAppManager() {
        return mAppManager;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}