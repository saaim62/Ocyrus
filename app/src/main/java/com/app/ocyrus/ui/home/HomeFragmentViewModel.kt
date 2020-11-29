package com.app.ocyrus.ui.home

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
import com.app.ocyrus.utills.User


import java.util.HashMap

import retrofit2.Response


/**
 * The type Login view model.
 */
class HomeFragmentViewModel
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
    private var homeLiveData: MutableLiveData<BaseResponse<Void>>? = null



    fun getLoginLiveData(networkCall: NetworkCall, param: HashMap<String,String>): MutableLiveData<BaseResponse<Void>> {
        if (homeLiveData == null) {
            homeLiveData = MutableLiveData()
        }
//        authDataSource.login(this, networkCall, param, true)
        //finally we will return the list
        return homeLiveData as MutableLiveData<BaseResponse<Void>>
    }


    override fun onSuccess(apiTag: Int, baseResponse: Response<BaseResponse<*>>) {
        try {
            if (apiTag == IApi.TAG_LOGIN) {
                val response = baseResponse.body()
                if (response != null && response!!.status) {
                    try {
                        homeLiveData!!.setValue(response as BaseResponse<Void>?)
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
