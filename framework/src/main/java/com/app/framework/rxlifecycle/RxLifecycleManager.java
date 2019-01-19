package com.app.framework.rxlifecycle;

import android.app.Activity;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class RxLifecycleManager {

    private HashMap<Object, BehaviorSubject> mBehaviorSubject;

    public RxLifecycleManager() {
        mBehaviorSubject = new HashMap<>();
    }

    private BehaviorSubject newLifecycleSubject(Activity activity) {
        BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();
        mBehaviorSubject.put(activity, lifecycleSubject);
        return lifecycleSubject;
    }

    private BehaviorSubject newLifecycleSubject(Fragment fragment) {
        BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();
        mBehaviorSubject.put(fragment, lifecycleSubject);
        return lifecycleSubject;
    }

    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle(Activity activity) {
        return provideLifecycleSubject(activity).hide();
    }

    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(Activity activity, @NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(provideLifecycleSubject(activity), event);
    }

    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle(Activity activity) {
        return RxLifecycleAndroid.bindActivity(provideLifecycleSubject(activity));
    }

    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle(Fragment fragment) {
        return provideLifecycleSubject(fragment).hide();
    }

    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(Fragment fragment, @NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(provideLifecycleSubject(fragment), event);
    }

    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle(Fragment fragment) {
        return RxLifecycleAndroid.bindActivity(provideLifecycleSubject(fragment));
    }

    public void destroyLifecycleSubject(Activity activity) {
        mBehaviorSubject.remove(activity);
    }

    public void destroyLifecycleSubject(Fragment fragment) {
        mBehaviorSubject.remove(fragment);
    }

    public BehaviorSubject provideLifecycleSubject(Activity activity) {
        BehaviorSubject behaviorSubject = mBehaviorSubject.get(activity);
        if (behaviorSubject == null) {
            behaviorSubject = newLifecycleSubject(activity);
        }
        return behaviorSubject;
    }

    public BehaviorSubject provideLifecycleSubject(Fragment fragment) {
        BehaviorSubject behaviorSubject = mBehaviorSubject.get(fragment);
        if (behaviorSubject == null) {
            behaviorSubject = newLifecycleSubject(fragment);
        }
        return behaviorSubject;
    }
}