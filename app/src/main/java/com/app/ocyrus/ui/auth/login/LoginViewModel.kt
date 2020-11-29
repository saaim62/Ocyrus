package com.app.ocyrus.auth.login

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.app.ocyrus.network.NetworkCall
import com.app.ocyrus.network.ServiceCallBack
import com.app.ocyrus.base.BaseNavigator
import com.app.ocyrus.base.BaseResponse
import com.app.ocyrus.base.BaseViewModel
import com.app.ocyrus.network.IApi
import com.app.ocyrus.network.RemoteDataSource
import com.app.ocyrus.utills.User

import retrofit2.Response


/**
 * The type Login view model.
 */
class LoginViewModel
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

    /**
     * The Login live data.
     */
    private var loginLiveData: MutableLiveData<BaseResponse<User>>? = null
    /**
     * The Social live data.
     */
    private var socialLiveData: MutableLiveData<BaseResponse<User>>? = null
    private var forgotLiveData: MutableLiveData<BaseResponse<Void>>? = null
    private var verifyLiveData: MutableLiveData<BaseResponse<Void>>? = null
    private var resetLiveData: MutableLiveData<BaseResponse<Void>>? = null

    /**
     * Gets login live data.
     *
     * @param networkCall the network call
     * @param jsonObject  the json object
     * @return the login live data
     */
    //we will call this method to get the data
    fun getLoginLiveData(networkCall: NetworkCall,jsonObject: HashMap<String, String>): MutableLiveData<BaseResponse<User>> {
        if (loginLiveData == null) {
            loginLiveData = MutableLiveData()
        }
        authDataSource.login(this, networkCall,jsonObject,true)
        //finally we will return the list
        return loginLiveData as MutableLiveData<BaseResponse<User>>
    }
    fun getForgotPasswordLiveData(networkCall: NetworkCall, jsonObject:HashMap<String,String>): MutableLiveData<BaseResponse<Void>> {
        if (forgotLiveData == null) {
            forgotLiveData = MutableLiveData()
        }
        authDataSource.forgotPassword(this, networkCall,jsonObject,true)
        //finally we will return the list
        return forgotLiveData as MutableLiveData<BaseResponse<Void>>
    }
    fun getForgotOtpVerifyPasswordLiveData(networkCall: NetworkCall, jsonObject:HashMap<String,String>): MutableLiveData<BaseResponse<Void>> {
        if (verifyLiveData == null) {
            verifyLiveData = MutableLiveData()
        }
        authDataSource.forgotVerifyOtpPassword(this, networkCall,jsonObject,true)
        //finally we will return the list
        return verifyLiveData as MutableLiveData<BaseResponse<Void>>
    }
    fun getForgotResetPasswordLiveData(networkCall: NetworkCall, jsonObject:HashMap<String,String>): MutableLiveData<BaseResponse<Void>> {
        if (resetLiveData == null) {
            resetLiveData = MutableLiveData()
        }
        authDataSource.forgotResetPassword(this, networkCall,jsonObject,true)
        //finally we will return the list
        return resetLiveData as MutableLiveData<BaseResponse<Void>>
    }
    override fun onSuccess(apiTag: Int, baseResponse: Response<BaseResponse<*>>) {
        try {
            if (apiTag == IApi.TAG_LOGIN) {
                val response = baseResponse.body()

                if (response != null && response!!.status) {
                    try {
                        Log.d("responseSuccess",response.toString())

                        loginLiveData!!.setValue(response as BaseResponse<User>?)

                        getNavigator()?.handleError(response.message, apiTag, response!!.code)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                } else {
                    getNavigator()?.handleError(response!!.message, apiTag, response!!.code)
                }

            }

            else if (apiTag == IApi.TAG_FORGOT_PASSWORD) {
                val response = baseResponse.body()
                if (response != null && response!!.status) {
                    try {
                        forgotLiveData!!.setValue(response as BaseResponse<Void>?)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                } else {
                    getNavigator()?.handleError(response!!.message, apiTag, response!!.code)
                }

            }
            else if (apiTag == IApi.TAG_VERIFY_OTP) {
                val response = baseResponse.body()
                if (response != null && response!!.status) {
                    try {
                        verifyLiveData!!.setValue(response as BaseResponse<Void>?)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                } else {
                    getNavigator()?.handleError(response!!.message, apiTag, response!!.code)
                }

            }
            else if (apiTag == IApi.TAG_RESET_PASSWORD) {
                val response = baseResponse.body()
                if (response != null && response!!.status) {
                    try {
                        resetLiveData!!.setValue(response as BaseResponse<Void>?)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                } else {
                    getNavigator()?.handleError(response!!.message, apiTag, response!!.code)
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
