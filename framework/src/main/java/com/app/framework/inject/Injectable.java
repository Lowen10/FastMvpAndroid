package com.app.framework.inject;

import com.app.framework.mvp.component.AppComponent;

/**
 * Created by lowen on 04/04/2018.
 */

public interface Injectable {

    /**
     * Dagger注入AppComponent， 在此方法中注入其他的Presenter
     *
     * @param appComponent
     */
    void injectComponent(AppComponent appComponent);

    /**
     * Dagger 注入成功
     */
    void daggerInjected();
}
