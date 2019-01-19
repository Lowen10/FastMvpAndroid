package com.app.framework.inject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.app.framework.AppDelegate;
import com.app.framework.lifecycle.ActivityLifecycle;

import javax.inject.Inject;

public class ActivityInjectHandler extends ActivityLifecycle {

    private FragmentInjectHandler mFragmentInjectHandler;
    private AppDelegate mAppDelegate;

    @Inject
    public ActivityInjectHandler(FragmentInjectHandler fragmentInjectHandler, AppDelegate appDelegate) {
        this.mFragmentInjectHandler = fragmentInjectHandler;
        this.mAppDelegate = appDelegate;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            fragmentManager.registerFragmentLifecycleCallbacks(mFragmentInjectHandler, true);
        }
        if (activity instanceof Injectable) {
            Injectable injectable = (Injectable) activity;
            injectable.injectComponent(mAppDelegate.getAppComponent());
            injectable.daggerInjected();
        }
        super.onActivityCreated(activity, savedInstanceState);
    }
}
