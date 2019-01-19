package com.app.framework.mvp.module;

import android.app.Application;
import android.support.v4.app.FragmentManager;

import com.app.framework.common.AppManager;
import com.app.framework.inject.ActivityInjectHandler;
import com.app.framework.inject.FragmentInjectHandler;
import com.app.framework.lifecycle.AppLifecycle;
import com.app.framework.lifecycle.LifecycleManager;
import com.app.framework.mvp.impl.AppManagerImpl;
import com.app.framework.mvp.RepositoryManager;
import com.app.framework.mvp.impl.RepositoryManagerImpl;
import com.app.framework.rxlifecycle.RxActivityLifecycleHandler;
import com.app.framework.rxlifecycle.RxFragmentLifecycleHandler;
import com.app.framework.rxlifecycle.RxLifecycleManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by lowen on 14/08/2017 21:42
 */
@Module
public abstract class AppModule {

    @Singleton
    @Provides
    static RxLifecycleManager provideRxLifecycleManager() {
        return new RxLifecycleManager();
    }

    @Singleton
    @Binds
    abstract AppManager appManager(AppManagerImpl appManagerImpl);

    @Singleton
    @Binds
    @Named("LifecycleManager")
    abstract AppLifecycle provideAppLifecycle(LifecycleManager lifecycleManager);

    @Singleton
    @Binds
    abstract RepositoryManager provideRepositoryManager(RepositoryManagerImpl repositoryManager);

    @Singleton
    @Binds
    @Named("RxActivityLifecycle")
    abstract Application.ActivityLifecycleCallbacks bindRxActivityLifecycle(RxActivityLifecycleHandler rxActivityLifecycle);

    @Singleton
    @Binds
    @Named("RxFragmentLifecycle")
    abstract FragmentManager.FragmentLifecycleCallbacks bindRxFragmentLifecycle(RxFragmentLifecycleHandler rxFragmentLifecycle);

    @Singleton
    @Binds
    @Named("ActivityInjectHandler")
    abstract Application.ActivityLifecycleCallbacks bindActivityInjectHandler(ActivityInjectHandler activityInjectHandler);

    @Singleton
    @Binds
    @Named("FragmentInjectHandler")
    abstract FragmentManager.FragmentLifecycleCallbacks bindFragmentInjectHandler(FragmentInjectHandler fragmentInjectHandler);
}