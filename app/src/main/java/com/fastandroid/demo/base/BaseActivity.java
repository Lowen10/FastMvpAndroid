package com.fastandroid.demo.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnBinder;

    protected boolean isFullScreen() {
        return false;
    }

    protected boolean hasActionBar() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                View decorView = getWindow().getDecorView();
//                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//                decorView.setSystemUiVisibility(option);
//                getWindow().setStatusBarColor(Color.TRANSPARENT);
//            }
            hideActionBar();
        } else if (!hasActionBar()) {
            hideActionBar();
        }

        int layoutResId = getContentView(savedInstanceState);
        if (layoutResId == 0) {
            throw new IllegalArgumentException("Content view id is null.");
        }
        setContentView(layoutResId);
        mUnBinder = ButterKnife.bind(this);
        initialize(savedInstanceState);
    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    protected abstract int getContentView(Bundle savedInstanceState);

    protected abstract void initialize(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }
}
