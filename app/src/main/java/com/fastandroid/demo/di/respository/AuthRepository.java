package com.fastandroid.demo.di.respository;


import com.app.framework.mvp.BaseRepository;
import com.app.framework.mvp.RepositoryManager;
import com.fastandroid.demo.client.ResponseModel;
import com.fastandroid.demo.client.api.AuthApi;
import com.fastandroid.demo.di.irepository.IAuthRepository;
import com.fastandroid.demo.model.UserBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AuthRepository extends BaseRepository implements IAuthRepository {

    public AuthRepository(RepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<ResponseModel<UserBean>> login(String username, String password) {

        ResponseModel<UserBean> responseModel = new ResponseModel<>();
        responseModel.setCode(200);
        UserBean userBean = new UserBean();
        userBean.setUsername("mackesy");
        responseModel.setResult(userBean);
        return Observable.just(responseModel).delay(2, TimeUnit.SECONDS);

//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("username", username);
//            jsonObject.put("password", password);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
//        return obtainRetrofitService(AuthApi.class).login(requestBody);
    }
}
