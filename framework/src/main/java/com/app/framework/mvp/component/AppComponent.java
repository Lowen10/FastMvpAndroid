package com.app.framework.mvp.component;

import android.app.Application;

import com.app.framework.AppDelegate;
import com.app.framework.common.AppManager;
import com.app.framework.http.handler.HttpClientHandler;
import com.app.framework.mvp.RepositoryManager;
import com.app.framework.mvp.module.AppModule;
import com.app.framework.mvp.module.ConfigModule;
import com.app.framework.rxlifecycle.RxLifecycleManager;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by lowen on 14/08/2017 21:42
 */
@Singleton
@Component(modules = {AppModule.class, ConfigModule.class})
public interface AppComponent {

    Application application();

    RepositoryManager repositoryManager();

    RxLifecycleManager rxLifecycleManager();

    HttpClientHandler httpErrorHandler();

    AppManager appManager();

    OkHttpClient provideClient();

    void inject(AppDelegate delegate);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder appDelegate(AppDelegate appDelegate);

        Builder configModule(ConfigModule configModule);

        AppComponent build();
    }
}