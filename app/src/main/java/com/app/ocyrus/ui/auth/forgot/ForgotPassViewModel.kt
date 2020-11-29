package com.app.ocyrus.auth.forgot

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.app.ocyrus.network.NetworkCall
import com.app.ocyrus.network.ServiceCallBack
import com.app.ocyrus.base.BaseNavigator
import com.app.ocyrus.base.BaseResponse
import com.app.ocyrus.base.BaseViewModel
import com.app.ocyrus.network.IApi
import com.app.ocyrus.network.RemoteDataSource

import retrofit2.Response


/**
 * The type Login view model.
 */
class ForgotPassViewModel
/**
 * Instantiates a new Login view model.
 *
 * @param application the application
 */
(@NonNull application: Application) : BaseViewModel<BaseNavigator>(application), ServiceCallBack {


    /**
     * The Auth data source.
     */
    private val authDataSource = RemoteDataSource.instance


    private var forgotLiveData: MutableLiveData<BaseResponse<Void>>? = null

    /**
     * Gets login live data.
     *
     * @param networkCall the network call
     * @param jsonObject  the json object
     * @return the login live data
     */
    //we will call this method to get the data
    fun getForgotPasswordLiveData(networkCall: NetworkCall, jsonObject:HashMap<String,String>): MutableLiveData<BaseResponse<Void>> {
        if (forgotLiveData == null) {
            forgotLiveData = MutableLiveData()
        }
        authDataSource.forgotPassword(this, networkCall,jsonObject,true)
        //finally we will return the list
        return forgotLiveData as MutableLiveData<BaseResponse<Void>>
    }


    override fun onSuccess(apiTag: Int, baseResponse: Response<BaseResponse<*>>) {
        try {
            if (apiTag == IApi.TAG_FORGOT_PASSWORD) {
                val response = baseResponse.body()
                if (response != null && response.status) {
                    try {
                        forgotLiveData!!.value = response as BaseResponse<Void>?
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                } else {
                    getNavigator()?.handleError(response!!.message, apiTag, response.code)
                }

            }
        } catch (e: Exception) {
        }


    }


    /**
     * On failed.
     *
     * @param apiTag the api tag
     * @param error  the error
     */
    override fun onFailed(apiTag: Int, error: String) {
        try {
            getNavigator()?.handleError(error, apiTag, 0)
        } catch (e: Exception) {
        }

    }

}
