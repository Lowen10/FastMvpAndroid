package com.app.framework.http;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ResponseHandler<T> implements Observer<T>, SchedulersCallback {

    private ResponseCallback callback;
    private T entity;

    public ResponseHandler(ResponseCallback<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onStarted() {
        if (callback != null) {
            callback.onStarted();
        }
    }

    @Override
    public void onFinished() {
        if (callback != null) {
            callback.onFinished();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (callback == null) {
            throw new IllegalStateException("ResponseCallback is null.");
        }
        if (callback != null) {
            callback.setDisposable(d);
        }
    }

    @Override
    public void onNext(T t) {
        this.entity = t;
    }

    @Override
    public void onError(Throwable e) {
        if (callback != null) {
            callback.onFailed(e.getMessage());
        }
    }

    @Override
    public void onComplete() {
        if (callback != null) {
            callback.onSuccess(entity);
        }
    }
}