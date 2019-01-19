package com.fastandroid.demo.client;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.app.framework.http.ResponseCallback;

public abstract class ResponseProgressCallback<T> extends ResponseCallback<T> {

    private Context context;
    private ProgressDialog progressDialog;

    public ResponseProgressCallback(Context context) {
        this.context = context;
    }

    private void showProgressDialog() {
        dismissProgressDialog();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.setOnDismissListener(onDismissListener);
        progressDialog.show();
    }

    private DialogInterface.OnDismissListener onDismissListener = new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    };

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onStarted() {
        super.onStarted();
        showProgressDialog();
    }

    @Override
    public void onFinished() {
        super.onFinished();
        dismissProgressDialog();
    }
}
