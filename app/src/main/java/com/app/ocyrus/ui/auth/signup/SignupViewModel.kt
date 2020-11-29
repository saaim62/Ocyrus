package com.app.ocyrus.auth.signup


import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.app.ocyrus.base.BaseNavigator
import com.app.ocyrus.base.BaseResponse
import com.app.ocyrus.base.BaseViewModel
import com.app.ocyrus.network.IApi
import com.app.ocyrus.network.NetworkCall
import com.app.ocyrus.network.RemoteDataSource
import com.app.ocyrus.network.ServiceCallBack
import com.app.ocyrus.utills.User
import com.app.ocyrus.utills.Util
import retrofit2.Response
import java.io.File

class SignupViewModel
    (@NonNull application: Application) : BaseViewModel<BaseNavigator>(application),
    ServiceCallBack {

    private var socialLiveData: MutableLiveData<BaseResponse<User>>? = null

    /**
     * The Auth data source.
     */
    //    private Context context;

    private val authDataSource = RemoteDataSource.instance


    /**
     * The Register live data.
     */
    private var registerLiveData: MutableLiveData<BaseResponse<Void>>? = null


    /**
     * Do register mutable live data.
     *
     * @param networkCall the network call
     * @param email       the email
     * @param password    the password
     * @return the mutable live data
     */
    //we will call this method to get the data

    fun doRegister(
        networkCall: NetworkCall,
        role_id: String,
        first_name: String,
        last_name: String,
        email: String,
        mobile: String,
        password: String,
        device_id: String,
        device_type: String,
        file: File?
    ): MutableLiveData<BaseResponse<Void>> {
        if (registerLiveData == null) {
            registerLiveData = MutableLiveData()
        }
        authDataSource.register(
            this, networkCall,
            role_id,
            first_name,
            last_name,
            email,
            mobile,
            password,
            device_id,
            device_type,
            file,
            true
        )
        //finally we will return the list
        return registerLiveData as MutableLiveData<BaseResponse<Void>>
    }


    override fun onSuccess(apiTag: Int, baseResponse: Response<BaseResponse<*>>) {

        try {
            if (apiTag == IApi.TAG_REGISTER) {
                val response = baseResponse.body()
                if (response != null && response.status) {
                    registerLiveData!!.setValue(response as BaseResponse<Void>?)
                } else {
                    getNavigator()?.handleError(response!!.message + "", apiTag, response.code)
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
            getNavigator()?.handleError(Util.nullCheck(error) + "", apiTag, 0)
        } catch (e: Exception) {
        }

    }


}
