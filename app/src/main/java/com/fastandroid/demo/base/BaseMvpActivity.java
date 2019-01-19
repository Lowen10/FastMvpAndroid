package com.fastandroid.demo.base;

import com.app.framework.mvp.BasePresenter;
import com.app.framework.inject.Injectable;
import com.app.framework.mvp.component.AppComponent;
import com.app.framework.rxlifecycle.RxLifecycleable;

import javax.inject.Inject;

public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity implements Injectable, RxLifecycleable {
    @Inject
    protected T presenter;

    @Override
    public void injectComponent(AppComponent appComponent) {
        injectPresenter(appComponent);
        if (presenter != null) {
            presenter.setInjectable(this);
        }
        daggerInjected();
    }

    public abstract void injectPresenter(AppComponent appComponent);
}
