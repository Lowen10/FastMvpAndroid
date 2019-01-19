package com.app.framework.mvp;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.app.framework.http.ResponseFunc;
import com.app.framework.http.ResponseHandler;
import com.app.framework.http.SchedulersTransformer;
import com.app.framework.http.handler.HttpClientHandler;
import com.app.framework.inject.Injectable;
import com.app.framework.rxlifecycle.RxLifecycleManager;
import com.app.framework.rxlifecycle.RxLifecycleable;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;

/**
 * Created by lowen on 14/08/2017 21:42
 */
public class BasePresenter {
    protected HttpClientHandler clientHandler;
    protected Injectable injectable;
    protected RxLifecycleManager subjectManager;

    public BasePresenter(HttpClientHandler clientHandler, RxLifecycleManager subjectManager) {
        this.clientHandler = clientHandler;
        this.subjectManager = subjectManager;
    }

    /**
     * 设置Injectable，在Dagger inject后调用，保证之后请求能成功匹配RxLifecycleable
     * @param injectable
     */
    public void setInjectable(Injectable injectable) {
        this.injectable = injectable;
    }

    /**
     * 执行Http请求
     *
     * @param observable
     * @param responseHandler
     * @param <R>
     * @param <T>
     */
    protected <R, T> void execute(Observable<R> observable, ResponseHandler<T> responseHandler) {
        LifecycleTransformer lifecycleTransformer = null;
        if (injectable instanceof RxLifecycleable) {
            if (injectable instanceof Fragment) {
                lifecycleTransformer = subjectManager.bindUntilEvent((Fragment) injectable, FragmentEvent.DESTROY);
            } else if (injectable instanceof Activity) {
                lifecycleTransformer = subjectManager.bindUntilEvent((Activity) injectable, ActivityEvent.DESTROY);
            } else {
                throw new IllegalArgumentException("RxLifecycleable just can used Activity or Fragment.");
            }
        }
        Observable<T> resultObservable = observable.flatMap(new ResponseFunc<R, T>(clientHandler))
                .compose(new SchedulersTransformer<>(responseHandler, clientHandler));
        if (lifecycleTransformer != null) {
            resultObservable = resultObservable.compose(lifecycleTransformer);
        }
        resultObservable.subscribe(responseHandler);
    }
}
