package com.app.framework.rxlifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import io.reactivex.subjects.Subject;

@Singleton
public class RxActivityLifecycleHandler implements Application.ActivityLifecycleCallbacks {

    private RxLifecycleManager mRxLifecycleManager;
    private Lazy<RxFragmentLifecycleHandler> mRxFragmentLifecycleLazy;

    @Inject
    public RxActivityLifecycleHandler(RxLifecycleManager mRxLifecycleManager,
                                      Lazy<RxFragmentLifecycleHandler> fragmentLifecycleForRxLifecycleLazy) {
        this.mRxLifecycleManager = mRxLifecycleManager;
        this.mRxFragmentLifecycleLazy = fragmentLifecycleForRxLifecycleLazy;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity instanceof RxLifecycleable && activity instanceof FragmentActivity) {
            RxFragmentLifecycleHandler fragmentLifecycleForRxLifecycle = mRxFragmentLifecycleLazy.get();
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycleForRxLifecycle, true);
        }
        obtainSubject(activity).onNext(ActivityEvent.CREATE);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activity instanceof RxLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.START);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity instanceof RxLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.RESUME);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (activity instanceof RxLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.PAUSE);
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity instanceof RxLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.STOP);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity instanceof RxLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.DESTROY);
            mRxLifecycleManager.destroyLifecycleSubject(activity);
        }
    }

    private Subject<ActivityEvent> obtainSubject(Activity activity) {
        return mRxLifecycleManager.provideLifecycleSubject(activity);
    }
}
