package com.fastandroid.demo.di.irepository;


import com.fastandroid.demo.client.ResponseModel;
import com.fastandroid.demo.model.UserBean;

import io.reactivex.Observable;

public interface IAuthRepository {

    Observable<ResponseModel<UserBean>> login(String username, String password);
}
