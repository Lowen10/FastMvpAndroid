package com.app.framework.inject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.app.framework.AppDelegate;
import com.app.framework.lifecycle.FragmentLifecycle;

import javax.inject.Inject;

public class FragmentInjectHandler extends FragmentLifecycle {

    private AppDelegate mAppDelegate;

    @Inject
    public FragmentInjectHandler(AppDelegate appDelegate) {
        this.mAppDelegate = appDelegate;
    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        if (f instanceof Injectable) {
            Injectable injectable = (Injectable) f;
            injectable.injectComponent(mAppDelegate.getAppComponent());
            injectable.daggerInjected();
        }
        super.onFragmentActivityCreated(fm, f, savedInstanceState);
    }
}
