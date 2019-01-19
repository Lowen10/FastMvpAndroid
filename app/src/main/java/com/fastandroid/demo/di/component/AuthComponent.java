package com.fastandroid.demo.di.component;

import com.app.framework.mvp.component.AppComponent;
import com.app.framework.mvp.scope.ActivityScope;
import com.fastandroid.demo.auth.LoginActivity;
import com.fastandroid.demo.di.module.PresenterModule;
import com.fastandroid.demo.di.module.RepositoryModule;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {RepositoryModule.class, PresenterModule.class})
public interface AuthComponent {
    void inject(LoginActivity activity);
}
