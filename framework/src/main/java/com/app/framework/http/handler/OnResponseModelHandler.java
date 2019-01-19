package com.app.framework.http.handler;

import android.content.Context;

public interface OnResponseModelHandler<T, R> {

    /**
     * 请求数据模型处理，能更灵活的处理服务器返回的各种数据结构
     *
     * @param t 服务直接数据原型
     * @return 需要的数据类型
     * @throws Exception 直接抛出异常，由{@link OnHttpErrorHandler#onHttpError(Context, Throwable)} 处理
     */
    R modelHandler(T t) throws Exception;

}
