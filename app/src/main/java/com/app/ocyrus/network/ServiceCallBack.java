package com.app.ocyrus.network;

import com.app.ocyruss.base.BaseResponse;

import retrofit2.Response;

/**
 * Created by Piyush Prajapati on 1/29/2019.
 * at http://www.dotsquares.com/
 */
public interface ServiceCallBack {

    /**
     * On success.
     *
     * @param apiTag       the api tag
     * @param baseResponse the base response
     */
    void onSuccess(int apiTag, Response<BaseResponse> baseResponse);

    /**
     * On failed.
     *
     * @param apiTag the api tag
     * @param error  the error
     */
    void onFailed(int apiTag, String error);
}
