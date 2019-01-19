package com.fastandroid.demo.client;

import com.app.framework.http.BusinessException;
import com.app.framework.http.handler.OnResponseModelHandler;

public class HttpClientResponseHandler<T> implements OnResponseModelHandler<ResponseModel<T>, T> {
    @Override
    public T modelHandler(ResponseModel<T> tResponseModel) {
        if (tResponseModel.getCode() == 200) {
            return tResponseModel.getResult();
        } else {
            throw new BusinessException(tResponseModel.getCode(), tResponseModel.getMessage());
        }
    }
}
