package com.app.ocyruss.app.auth.login

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.app.ocyrus.network.ServiceCallBack
import com.app.ocyruss.base.BaseNavigator
import com.app.ocyruss.base.BaseResponse
import com.app.ocyruss.base.BaseViewModel

import retrofit2.Response


/**
 * The type Login view model.
 */
class RegisterVerifyRequestViewModel
/**
 * Instantiates a new Login view model.
 *
 * @param application the application
 */
(@NonNull application: Application) : BaseViewModel<BaseNavigator>(application), ServiceCallBack {


    override fun onSuccess(apiTag: Int, baseResponse: Response<BaseResponse<*>>) {

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
