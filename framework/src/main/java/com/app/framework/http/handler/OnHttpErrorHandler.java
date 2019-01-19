package com.app.framework.http.handler;

import android.content.Context;

/**
 * Created by lowen on 14/08/2017 21:42
 */
public interface OnHttpErrorHandler {
    /**
     * 网络请求错误处理
     *
     * @param context
     * @param t       错误Throwable
     */
    void onHttpError(Context context, Throwable t);
}
