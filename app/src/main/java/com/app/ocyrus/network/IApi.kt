package com.app.ocyrus.network

import com.app.ocyrus.utills.User
import com.app.ocyrus.base.BaseResponse
import com.app.ocyrus.utills.DashbordResponse
import com.app.ocyrus.utills.StaticHtmlPages
import com.app.ocyrus.utills.UpdateDashbordResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface IApi {


    @FormUrlEncoded
    @POST("users/login")
    fun login(
        @FieldMap params :  Map<String, String>
    ): Call<BaseResponse<User>>

    @GET("/tmc")
    fun getTmc(): Call<BaseResponse<StaticHtmlPages>>

    @GET("/pc")
    fun getPrivacy(): Call<BaseResponse<StaticHtmlPages>>

    @POST("users/logout")
    fun logout(
        @FieldMap params :  Map<String, String>): Call<BaseResponse<Void>>


    @FormUrlEncoded
    @POST("users/forgot-password")
    fun forgotPassword(
        @FieldMap params :  Map<String, String>
       ): Call<BaseResponse<Void>>

    @FormUrlEncoded
    @POST("users/verify-otp")
    fun forgotOtpVerify(
        @FieldMap params :  Map<String, String>
    ): Call<BaseResponse<Void>>

 @FormUrlEncoded
    @POST("users/reset-password")
    fun forgotReset(
        @FieldMap params :  Map<String, String>
    ): Call<BaseResponse<Void>>


    @Multipart
 @POST("users/register")
    fun registerRequest(
        @Part("role_id") role_id: RequestBody?,
        @Part("first_name") first_name:RequestBody?,
        @Part("last_name") last_name:RequestBody?,
        @Part("email") email:RequestBody?,
        @Part("mobile") mobile:RequestBody?,
        @Part("password") password:RequestBody?,
        @Part("device_id") device_id:RequestBody?,
        @Part("device_type") device_type:RequestBody?,
        @Part image:MultipartBody.Part?
    ): Call<BaseResponse<Void>>


    @GET("users/dashboard")
    fun getDashbordData(): Call<BaseResponse<DashbordResponse>>

    @FormUrlEncoded
    @POST("users/update-user-info")
    fun updateDashbordData(
        @FieldMap params :  Map<String, String>
    ): Call<BaseResponse<UpdateDashbordResponse>>

    companion object {
        val TAG_LOGIN = 1
        val TAG_REGISTER = 2
        val TAG_VERIFY_ACCOUNT = 3

        val TAG_RESEND_OTP = 4
        val TAG_FORGOT_PASSWORD = 5
        val TAG_CHANGE_PASSWORD = 6
        val TAG_STATIC = 7
        val TAG_LOGOUT = 8
        val TAG_DAHSBORD_DATA = 9
        val TAG_UPDATE_DASHBORD_DATA = 10

        val TAG_VERIFY_OTP = 11
        val TAG_RESET_PASSWORD = 12
    }


}