package com.app.ocyrus


import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.app.ocyrus.network.NetworkCall
import com.app.ocyrus.network.ServiceCallBack
import com.app.ocyruss.base.BaseNavigator
import com.app.ocyruss.base.BaseResponse
import com.app.ocyruss.base.BaseViewModel
import com.app.ocyruss.network.IApi
import com.app.ocyruss.network.RemoteDataSource
import com.app.ocyruss.utills.DashbordResponse
import com.app.ocyruss.utills.UpdateDashbordResponse
import com.app.ocyruss.utills.User
import org.json.JSONObject
import retrofit2.Response
import java.io.File


/**
 * The type Home view model.
 */
class P2PViewModel(application: Application) : BaseViewModel<BaseNavigator>(application),
    ServiceCallBack {


    private val authDataSource = RemoteDataSource.instance
    private var dashbordliveData: MutableLiveData<BaseResponse<DashbordResponse>>? = null
    private var updateDashbordLiveData: MutableLiveData<BaseResponse<UpdateDashbordResponse>>? = null

    fun getDashbordData(networkCall: NetworkCall): MutableLiveData<BaseResponse<DashbordResponse>> {
    if (dashbordliveData == null) {
    dashbordliveData = MutableLiveData()
    }
    authDataSource.dashbordData(this, networkCall,    true)
    //finally we will return the list
    return dashbordliveData as MutableLiveData<BaseResponse<DashbordResponse>>
    }

    fun getUpdateInfoData(networkCall: NetworkCall,jsonObject: HashMap<String,String>): MutableLiveData<BaseResponse<UpdateDashbordResponse>> {
        if (updateDashbordLiveData == null) {
            updateDashbordLiveData = MutableLiveData()
        }
        authDataSource.updateDashbordData(this, networkCall,    jsonObject)
        //finally we will return the list
        return updateDashbordLiveData as MutableLiveData<BaseResponse<UpdateDashbordResponse>>
    }


    /**
     * On success.
     *
     * @param apiTag       the api tag
     * @param baseResponse the base response
     */
    override fun onSuccess(apiTag: Int, baseResponse: Response<BaseResponse<*>>) {
        try {
            val response = baseResponse.body()

                if(apiTag == IApi.TAG_DAHSBORD_DATA){
                    if(response != null && response.status){
                        dashbordliveData!!.setValue(response as BaseResponse<DashbordResponse>?)
                    }
                }
            else if(apiTag == IApi.TAG_UPDATE_DASHBORD_DATA){
                if(response != null && response.status){
                    updateDashbordLiveData!!.setValue(response as BaseResponse<UpdateDashbordResponse>?)
                }
            }

        } catch (e: Exception) {
            getNavigator()?.handleError("Something went wrong", apiTag, baseResponse.code())
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
