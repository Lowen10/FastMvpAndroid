package com.app.framework.http;

import com.app.framework.http.handler.HttpClientHandler;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * HTTP请求处理类
 *
 * @param <R>
 * @param <T>
 */
public class ResponseFunc<R, T> implements Function<R, ObservableSource<T>> {

    private HttpClientHandler clientHandler;

    public ResponseFunc(HttpClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public ObservableSource<T> apply(R r) throws Exception {
        T t = clientHandler.modelHandler(r);
        if (t == null) {
            return Observable.empty();
        } else {
            return Observable.just(t);
        }
    }
}
