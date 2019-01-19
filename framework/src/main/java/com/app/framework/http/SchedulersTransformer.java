package com.app.framework.http;

import com.app.framework.http.handler.HttpClientHandler;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 异步请求former
 * 保证请求在新开线程中，回调在主线程中
 *
 * @param <T>
 */
public class SchedulersTransformer<T> implements ObservableTransformer<T, T> {

    private SchedulersCallback onLoadingStateCallback;
    private HttpClientHandler httpErrorHandler;

    public SchedulersTransformer(SchedulersCallback onLoadingStateCallback,
                                 HttpClientHandler httpErrorHandler) {
        this.onLoadingStateCallback = onLoadingStateCallback;
        this.httpErrorHandler = httpErrorHandler;
    }

    @Override
    public ObservableSource<T> apply(Observable<T> observable) {
        Observable<T> resultObservable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        if (onLoadingStateCallback != null) {
                            onLoadingStateCallback.onStarted();
                        }
                    }
                }).doAfterTerminate(new Action() {
                    @Override
                    public void run() {
                        if (onLoadingStateCallback != null) {
                            onLoadingStateCallback.onFinished();
                        }
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (httpErrorHandler != null) {
                            httpErrorHandler.onHttpError(throwable);
                        }
                    }
                });
        return resultObservable;
    }
}
