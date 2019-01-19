package com.fastandroid.demo.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.app.framework.mvp.component.AppComponent;
import com.fastandroid.demo.R;
import com.fastandroid.demo.base.BaseMvpActivity;
import com.fastandroid.demo.client.ResponseProgressCallback;
import com.fastandroid.demo.di.component.DaggerAuthComponent;
import com.fastandroid.demo.di.presenter.AuthPresenter;
import com.fastandroid.demo.model.UserBean;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<AuthPresenter> {

    @BindView(R.id.usernameET)
    EditText usernameET;
    @BindView(R.id.passwordET)
    EditText passwordET;

    @Override
    public void injectPresenter(AppComponent appComponent) {
        DaggerAuthComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void daggerInjected() {

    }

    @Override
    protected int getContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }


    @Override
    protected void initialize(Bundle savedInstanceState) {
    }

    private void login() {
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            return;
        }
        presenter.login(username, password, new ResponseProgressCallback<UserBean>(this) {
            @Override
            public void onSuccess(UserBean userBean) {
                finish();
            }

            @Override
            public void onFailed(String msg) {
            }
        });
    }

    @OnClick({R.id.loginBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                login();
                break;
        }
    }
}
