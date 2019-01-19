package com.fastandroid.demo.di.module;

import com.app.framework.http.handler.HttpClientHandler;
import com.app.framework.inject.Injectable;
import com.app.framework.mvp.scope.ActivityScope;
import com.app.framework.rxlifecycle.RxLifecycleManager;
import com.fastandroid.demo.di.irepository.IAuthRepository;
import com.fastandroid.demo.di.presenter.AuthPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @ActivityScope
    @Provides
    public AuthPresenter provideAuthPresenter(IAuthRepository authRepository,
                                              HttpClientHandler errorHandler,
                                              RxLifecycleManager subjectManager) {
        return new AuthPresenter(authRepository, errorHandler, subjectManager);
    }
}
