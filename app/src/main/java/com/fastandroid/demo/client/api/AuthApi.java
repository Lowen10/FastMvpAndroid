package com.fastandroid.demo.client.api;


import com.fastandroid.demo.client.ResponseModel;
import com.fastandroid.demo.model.UserBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("/server-api/auth/login")
    Observable<ResponseModel<UserBean>> login(@Body RequestBody requestBody);

    @POST("/server-api/auth/login")
    Observable<ResponseModel<UserBean>> login(@Field("username") String username, @Field("password") String password);

}
