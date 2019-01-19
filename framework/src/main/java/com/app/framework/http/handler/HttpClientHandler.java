package com.app.framework.http.handler;

import android.content.Context;

public class HttpClientHandler {

    private Context context;
    private OnHttpErrorHandler listener;
    private OnResponseModelHandler modelHandler;

    private HttpClientHandler(Context context, OnHttpErrorHandler listener, OnResponseModelHandler modelHandler) {
        this.context = context;
        this.listener = listener;
        this.modelHandler = modelHandler;
    }

    public void onHttpError(Throwable e) {
        listener.onHttpError(context, e);
    }

    /**
     * Response Model 转换
     *
     * @param t
     * @param <R>
     * @param <T>
     * @return 如果返回Null，那么Observable将发送empty;
     * @throws Exception
     */
    public <R, T> R modelHandler(T t) throws Exception {
        if (modelHandler != null) {
            return (R) modelHandler.modelHandler(t);
        }
        return (R) t;
    }

    public static class Builder {
        private Context context;
        private OnHttpErrorHandler listener;
        private OnResponseModelHandler modelHandler;

        public Builder with(Context context) {
            this.context = context;
            return this;
        }

        /**
         * 设置Http错误处理
         *
         * @param listener
         * @return
         */
        public Builder setOnHttpErrorListener(OnHttpErrorHandler listener) {
            this.listener = listener;
            return this;
        }

        /**
         * 配置HTTPResponse Model处理
         *
         * @param handler
         * @return
         */
        public Builder setOnResponseModelHandler(OnResponseModelHandler handler) {
            this.modelHandler = handler;
            return this;
        }

        public HttpClientHandler build() {
            if (context == null) {
                throw new IllegalStateException("Context is required");
            }
            if (listener == null) {
                throw new IllegalStateException("OnHttpErrorListener is required");
            }
            return new HttpClientHandler(context, listener, modelHandler);
        }
    }
}
