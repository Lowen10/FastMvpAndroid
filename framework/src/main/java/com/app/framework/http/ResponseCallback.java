package com.app.framework.http;

import io.reactivex.disposables.Disposable;

public abstract class ResponseCallback<T> implements SchedulersCallback {

    protected Disposable disposable;

    /**
     * 获取请求Disposable
     *
     * @return
     */
    public Disposable getDisposable() {
        return disposable;
    }

    public void setDisposable(Disposable disposable) {
        this.disposable = disposable;
    }

    /**
     * 请求成功回调
     *
     * @param t 返回对应对象
     */
    public abstract void onSuccess(T t);

    /**
     * 请求失败
     *
     * @param msg 错误信息，除系统返回的信息外还可以根据服务器返回的信息自定义。
     *            自定义位置 {@link com.app.framework.http.handler.OnResponseModelHandler#modelHandler(Object)}
     *            <p>
     *            例：
     * @Override public T modelHandler(ResponseModel<T> tResponseModel) {
     * if (tResponseModel.getCode() == 200) {
     * return tResponseModel.getResult();
     * } else {
     * throw new BusinessException(tResponseModel.getCode(), tResponseModel.getMessage());
     * }
     * }
     */
    public abstract void onFailed(String msg);

    /**
     * 请求开始
     */
    @Override
    public void onStarted() {
    }

    /**
     * 请求结束
     */
    @Override
    public void onFinished() {
    }
}
