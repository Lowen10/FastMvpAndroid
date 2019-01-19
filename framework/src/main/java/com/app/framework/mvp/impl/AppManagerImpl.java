package com.app.framework.mvp.impl;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.app.framework.common.AppManager;
import com.app.framework.lifecycle.ActivityLifecycle;
import com.app.framework.rxlifecycle.RxLifecycleManager;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by lowen on 14/08/2017 21:42
 */
public final class AppManagerImpl implements AppManager {

    private List<Activity> mActivityList;
    private RxLifecycleManager mRxLifecycleManager;
    private Application mApplication;

    @Inject
    public AppManagerImpl(Application application, RxLifecycleManager manager) {
        mActivityList = new ArrayList<>();
        this.mRxLifecycleManager = manager;
        this.mApplication = application;
        application.registerActivityLifecycleCallbacks(mActivityLifecycle);
    }

    @Override
    public RxLifecycleManager rxLifecycleManager() {
        return mRxLifecycleManager;
    }

    private ActivityLifecycle mActivityLifecycle = new ActivityLifecycle() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            super.onActivityCreated(activity, savedInstanceState);
            addActivity(activity);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            super.onActivityDestroyed(activity);
            removeActivity(activity);
        }
    };

    private void addActivity(Activity activity) {
        synchronized (AppManagerImpl.class) {
            if (!mActivityList.contains(activity)) {
                mActivityList.add(activity);
            }
        }
    }

    private void removeActivity(Activity activity) {
        synchronized (AppManagerImpl.class) {
            if (mActivityList.contains(activity)) {
                mActivityList.remove(activity);
            }
        }
    }

    @Override
    public void killActivity(Class<? extends Activity> activityClass) {
        for (Activity activity : mActivityList) {
            if (activity.getClass().equals(activityClass)) {
                activity.finish();
            }
        }
    }

    @Override
    public boolean activityInstanceIsLive(Activity activity) {
        return mActivityList.contains(activity);
    }


    @Override
    public boolean activityClassIsLive(Class<? extends Activity> activityClass) {
        for (Activity activity : mActivityList) {
            if (activity.getClass().equals(activityClass)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void killAll() {
        Iterator<Activity> iterator = mActivityList.iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            iterator.remove();
            next.finish();
        }
    }

    @Override
    public void appExit() {
        try {
            killAll();
            ActivityManager activityMgr = (ActivityManager) mApplication.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(mApplication.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            Logger.w(e.getMessage());
        }
    }
}
