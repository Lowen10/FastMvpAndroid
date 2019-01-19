package com.fastandroid.demo.di.presenter;

import com.app.framework.http.ResponseCallback;
import com.app.framework.http.ResponseHandler;
import com.app.framework.http.handler.HttpClientHandler;
import com.app.framework.inject.Injectable;
import com.app.framework.mvp.BasePresenter;
import com.app.framework.rxlifecycle.RxLifecycleManager;
import com.fastandroid.demo.di.irepository.IAuthRepository;
import com.fastandroid.demo.model.UserBean;

public class AuthPresenter extends BasePresenter {

    private IAuthRepository authRepository;

    public AuthPresenter(IAuthRepository authRepository,
                         HttpClientHandler errorHandler,
                         RxLifecycleManager subjectManager) {
        super(errorHandler, subjectManager);
        this.authRepository = authRepository;
    }

    public void login(String username, String password, ResponseCallback<UserBean> callback) {
        execute(authRepository.login(username, password), new ResponseHandler<>(callback));
    }

}
