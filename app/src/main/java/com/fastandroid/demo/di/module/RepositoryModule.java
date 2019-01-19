package com.fastandroid.demo.di.module;

import com.app.framework.mvp.RepositoryManager;
import com.app.framework.mvp.scope.ActivityScope;
import com.app.framework.mvp.scope.AppScope;
import com.fastandroid.demo.di.irepository.IAuthRepository;
import com.fastandroid.demo.di.respository.AuthRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lowen on 30/03/2018.
 */

@Module
public class RepositoryModule {

    @ActivityScope
    @Provides
    IAuthRepository provideUserRepository(RepositoryManager manager) {
        return new AuthRepository(manager);
    }

}
