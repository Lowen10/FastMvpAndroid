package com.fastandroid.demo.client;

import android.content.Context;
import android.widget.Toast;

import com.app.framework.http.handler.OnHttpErrorHandler;

public class HttpClientErrorListener implements OnHttpErrorHandler {
    @Override
    public void onHttpError(Context context, Throwable t) {
        Toast.makeText(context, "HttpErrorHandler Error", Toast.LENGTH_LONG).show();
        t.printStackTrace();
    }
}
