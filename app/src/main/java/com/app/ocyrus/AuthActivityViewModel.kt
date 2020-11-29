package com.app.ocyrus


import android.app.Application
import com.app.ocyrus.network.ServiceCallBack
import com.app.ocyruss.base.BaseNavigator
import com.app.ocyruss.base.BaseResponse
import com.app.ocyruss.base.BaseViewModel
import com.app.ocyruss.network.RemoteDataSource
import retrofit2.Response


/**
 * The type Home view model.
 */
class AuthActivityViewModel(application: Application) : BaseViewModel<BaseNavigator>(application),
    ServiceCallBack {


    private val authDataSource = RemoteDataSource.instance


    /**
     * On success.
     *
     * @param apiTag       the api tag
     * @param baseResponse the base response
     */
    override fun onSuccess(apiTag: Int, baseResponse: Response<BaseResponse<*>>) {
        try {
            val response = baseResponse.body()

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
        getNavigator()!!.handleError(error, apiTag, 0)
    }

}
