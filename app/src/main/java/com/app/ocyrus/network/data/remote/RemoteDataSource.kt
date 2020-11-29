package com.app.ocyrus.network

import com.app.ocyrus.base.BaseResponse
import com.app.ocyrus.network.data.remote.DataSource
import com.app.ocyrus.utills.DashbordResponse
import com.app.ocyrus.utills.StaticHtmlPages
import com.app.ocyrus.utills.UpdateDashbordResponse
import com.app.ocyrus.utills.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class RemoteDataSource : DataSource {
    companion object {
        private var INSTANCE: RemoteDataSource? = null

        val instance: RemoteDataSource
            get() {
                if (INSTANCE == null) {
                    INSTANCE = RemoteDataSource()
                }
                return INSTANCE as RemoteDataSource
            }
    }

    override fun login(
        serviceCallBack: ServiceCallBack,
        networkCall: NetworkCall,
        jsonObject: HashMap<String, String>,
        showLoading: Boolean
    ) {
        try {
//            val body = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.toString())
            networkCall.serviceCallBack = serviceCallBack
            networkCall.requestTag = IApi.TAG_LOGIN

            val responceCall = networkCall.getRetrofit(showLoading)!!.login(jsonObject)
            responceCall.enqueue(networkCall.requestCallback() as retrofit2.Callback<BaseResponse<User>>)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getHtmlPages(
        serviceCallBack: ServiceCallBack,
        networkCall: NetworkCall,
        title: String,
        showLoading: Boolean
    ) {
        try {
//            val body = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.toString())
            networkCall.serviceCallBack = serviceCallBack
            networkCall.requestTag = IApi.TAG_LOGIN

            val responceCall = if (title == "TMC") networkCall.getRetrofit(showLoading)!!
                .getTmc() else networkCall.getRetrofit(showLoading)!!.getPrivacy()


            responceCall.enqueue(networkCall.requestCallback() as retrofit2.Callback<BaseResponse<StaticHtmlPages>>)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun logout(
        serviceCallBack: ServiceCallBack,
        networkCall: NetworkCall,
        jsonObject: HashMap<String, String>,
        showLoading: Boolean
    ) {
        try {
//            val body = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.toString())
            networkCall.serviceCallBack = serviceCallBack
            networkCall.requestTag = IApi.TAG_LOGOUT

            val responceCall = networkCall.getRetrofit(showLoading)!!.logout(jsonObject)
            responceCall.enqueue(networkCall.requestCallback() as retrofit2.Callback<BaseResponse<Void>>)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun forgotPassword(
        serviceCallBack: ServiceCallBack,
        networkCall: NetworkCall,
        jsonObject: HashMap<String, String>,
        showLoading: Boolean
    ) {
        try {
//            val body = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.toString())
            networkCall.serviceCallBack = serviceCallBack
            networkCall.requestTag = IApi.TAG_FORGOT_PASSWORD

            val responceCall = networkCall.getRetrofit(showLoading)!!.forgotPassword(jsonObject)
            responceCall.enqueue(networkCall.requestCallback() as retrofit2.Callback<BaseResponse<Void>>)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun forgotVerifyOtpPassword(
        serviceCallBack: ServiceCallBack,
        networkCall: NetworkCall,
        jsonObject: HashMap<String, String>,
        showLoading: Boolean
    ) {
        try {
//            val body = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.toString())
            networkCall.serviceCallBack = serviceCallBack
            networkCall.requestTag = IApi.TAG_VERIFY_OTP

            val responceCall = networkCall.getRetrofit(showLoading)!!.forgotOtpVerify(jsonObject)
            responceCall.enqueue(networkCall.requestCallback() as retrofit2.Callback<BaseResponse<Void>>)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun forgotResetPassword(
        serviceCallBack: ServiceCallBack,
        networkCall: NetworkCall,
        jsonObject: HashMap<String, String>,
        showLoading: Boolean
    ) {

        try {
//            val body = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.toString())
            networkCall.serviceCallBack = serviceCallBack
            networkCall.requestTag = IApi.TAG_RESET_PASSWORD

            val responceCall = networkCall.getRetrofit(showLoading)!!.forgotReset(jsonObject)
            responceCall.enqueue(networkCall.requestCallback() as retrofit2.Callback<BaseResponse<Void>>)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun meetingList(
        serviceCallBack: ServiceCallBack,
        networkCall: NetworkCall,
        userName: String,
        sessionId: String,
        showLoading: Boolean
    ) {
        try {
//            val body = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.toString())
//            networkCall.serviceCallBack = serviceCallBack
//            networkCall.requestTag = IApi.TAG_MEETING_LIST
//
//            val responceCall = networkCall.getRetrofit(showLoading)!!.getMeetingList(userName,sessionId)
//            responceCall.enqueue(networkCall.requestCallback() as retrofit2.Callback<BaseResponse<ArrayList<MeetingItem>>>)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun dashbordData(
        serviceCallBack: ServiceCallBack,
        networkCall: NetworkCall,
        showLoading: Boolean
    ) {
        try {

            networkCall.serviceCallBack = serviceCallBack
            networkCall.requestTag = IApi.TAG_DAHSBORD_DATA

            val responceCall = networkCall.getRetrofit(showLoading)!!.getDashbordData()
            responceCall.enqueue(networkCall.requestCallback() as retrofit2.Callback<BaseResponse<DashbordResponse>>)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun updateDashbordData(
        serviceCallBack: ServiceCallBack,
        networkCall: NetworkCall,
        jsonObject: HashMap<String, String>
    ) {
        try {

            networkCall.serviceCallBack = serviceCallBack
            networkCall.requestTag = IApi.TAG_UPDATE_DASHBORD_DATA

            val responceCall = networkCall.getRetrofit(false)!!.updateDashbordData(jsonObject)
            responceCall.enqueue(networkCall.requestCallback() as retrofit2.Callback<BaseResponse<UpdateDashbordResponse>>)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun register(
        serviceCallBack: ServiceCallBack,
        networkCall: NetworkCall,
        role_id: String,
        first_name: String,
        last_name: String,
        email: String,
        mobile: String,
        password: String,
        device_id: String,
        device_type: String,
        profile_photo_file: File?,
        showLoading: Boolean
    ) {
        try {
            val role_id: RequestBody? =
                role_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val first_name: RequestBody? =
                first_name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val last_name: RequestBody? =
                last_name.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val email: RequestBody? =
                email.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val mobile: RequestBody? =
                mobile.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val password: RequestBody? =
                password.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val device_id: RequestBody? =
                device_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val device_typ: RequestBody? =
                device_type.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            var profile_photo_file_Body: MultipartBody.Part? = null

            if (profile_photo_file != null) {
                val requestImageFile =
                    profile_photo_file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                profile_photo_file_Body = MultipartBody.Part.createFormData(
                    "profile_photo",
                    profile_photo_file.name,
                    requestImageFile
                )

            }



            networkCall.serviceCallBack = serviceCallBack
            networkCall.requestTag = IApi.TAG_REGISTER

            val responceCall = networkCall.getRetrofit(showLoading)!!.registerRequest(
                role_id,
                first_name!!,
                last_name!!,
                email!!,
                mobile!!,
                password!!,
                device_id!!,
                device_typ!!,
                profile_photo_file_Body
            )
            responceCall.enqueue(networkCall.requestCallback() as retrofit2.Callback<BaseResponse<Void>>)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun changePin(
        serviceCallBack: ServiceCallBack,
        networkCall: NetworkCall,
        userName: String,
        sessionId: String,
        showLoading: Boolean
    ) {
        try {
//            val body = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.toString())
//            networkCall.serviceCallBack = serviceCallBack
//            networkCall.requestTag = IApi.TAG_ChangePIN
//
//            val responceCall = networkCall.getRetrofit(showLoading)!!.changePin(userName,sessionId)
//            responceCall.enqueue(networkCall.requestCallback() as retrofit2.Callback<BaseResponse<ChangePin>>)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}