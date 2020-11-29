package com.app.ocyrus.network.data.remote

import com.app.ocyrus.network.NetworkCall
import com.app.ocyrus.network.ServiceCallBack
import java.io.File


/**
 * Created by admin on 2/7/2018.
 */

interface DataSource {
    fun login(serviceCallBack: ServiceCallBack, networkCall: NetworkCall,
              jsonObject: HashMap<String, String>,
              showLoading: Boolean)
    fun getHtmlPages(serviceCallBack: ServiceCallBack, networkCall: NetworkCall,title:String, showLoading: Boolean)
    fun logout(serviceCallBack: ServiceCallBack, networkCall: NetworkCall,  jsonObject: HashMap<String, String>, showLoading: Boolean)
    fun changePin(serviceCallBack: ServiceCallBack, networkCall: NetworkCall, userName: String,sessionId :String, showLoading: Boolean)
    fun meetingList(serviceCallBack: ServiceCallBack, networkCall: NetworkCall, userName: String,sessionId :String, showLoading: Boolean)
    fun register(serviceCallBack: ServiceCallBack,
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
                 showLoading: Boolean)

    fun forgotPassword(serviceCallBack: ServiceCallBack, networkCall: NetworkCall,  jsonObject: HashMap<String, String>, showLoading: Boolean)

    fun forgotVerifyOtpPassword(serviceCallBack: ServiceCallBack, networkCall: NetworkCall,  jsonObject: HashMap<String, String>, showLoading: Boolean)
    fun forgotResetPassword(serviceCallBack: ServiceCallBack, networkCall: NetworkCall,  jsonObject: HashMap<String, String>, showLoading: Boolean)
    fun dashbordData(serviceCallBack: ServiceCallBack, networkCall: NetworkCall, showLoading: Boolean)
    fun updateDashbordData(serviceCallBack: ServiceCallBack, networkCall: NetworkCall, jsonObject: HashMap<String, String>)

}