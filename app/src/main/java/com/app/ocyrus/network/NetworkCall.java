package com.app.ocyrus.network;

import android.content.Context;


import androidx.viewbinding.BuildConfig;

import com.app.ocyrus.AppConstants;
import com.app.ocyrus.R;
import com.app.ocyrus.utills.AppManager;
import com.app.ocyrus.utills.SharedPref;
import com.app.ocyrus.utills.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.app.ocyrus.base.BaseResponse;
import com.app.ocyrus.network.IApi;

import java.io.IOException;

import java.util.concurrent.TimeUnit;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.app.ocyrus.utills.Utils.connectionNet;


/**
 * Created by Piyush Prajapati on 1/29/2019.
 * at http://www.dotsquares.com/
 */
public class NetworkCall {


    /**
     * The Context.
     */
    private final Context context;
    /**
     * The Request tag.
     */
    private int requestTag;
    /**
     * The Service call back.
     */
    private ServiceCallBack serviceCallBack;

    /**
     * Instantiates a new Network call.
     *
     * @param context the context
     */
    public NetworkCall(Context context) {
        this.context = context;
    }


    /**
     * The Callback.
     */
    private final Callback<BaseResponse> callback = new Callback<BaseResponse>() {

        @Override
        public void onResponse(Call<BaseResponse> call, retrofit2.Response<BaseResponse> response) {
            dismissLoading();

//            if ((response != null && response.body() != null  || response.code() == AppConstants.CODE_ACCOUNT_UNAUTHORIZED) {
//                try {
//                    Utils.showDialog(context, context.getString(R.string.msg_session_expired), (dialog, which) -> {
//                        logout();
//
//                    }, false);
//                } catch (Exception e) {
//                }
//            } else if ((response != null && response.body() != null && response.body().getCode() == AppConstants.CODE_VERSION_MISMATCH) || response.code() == AppConstants.CODE_VERSION_MISMATCH) {
//                try {
//                    Utils.showUpdateDialog(Util.isEmpty(response.body().getMessage()) ? "Your are using outdated version of the app, Please update the app" : response.body().getMessage(), context);
//                } catch (Exception e) {
//                }
//            } else if (response == null || response.code() == AppConstants.CODE_INTERNAL_SERVER_ERROR)
//                Utils.showTechnicalErrorDialog(context);
//
//
//            else
                serviceCallBack.onSuccess(requestTag, response);
        }


        @Override
        public void onFailure(Call call, Throwable error) {
            dismissLoading();
            //Log.d("APIFail", error.getMessage());
            try {
                serviceCallBack.onFailed(requestTag, connectionNet(context) ? error.toString() : context.getString(R.string.please_check_your_internet_connection));


//                if (((Activity) context).hasWindowFocus())
//                    Utils.showTechnicalErrorDialog(context);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    /**
     * Logout.
     */
    private void logout() {
        String fcmToken = SharedPref.getInstance(context).readString(SharedPref.FCM_TOKEN);
        String categories = SharedPref.getInstance(context).readString(SharedPref.CATEGORIES);

        SharedPref.getInstance(context).clearAll();
        AppManager.logoutUser(context);

        SharedPref.getInstance(context).writeString(SharedPref.FCM_TOKEN, fcmToken);
        SharedPref.getInstance(context).writeString(SharedPref.CATEGORIES, categories);
       // new android.os.Handler().postDelayed(() -> context.startActivity(new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).putExtra("from", "logout")), 1000);

    }

    /**
     * Gets service call back.
     *
     * @return the service call back
     */
    public ServiceCallBack getServiceCallBack() {
        return serviceCallBack;
    }

    /**
     * Sets service call back.
     *
     * @param serviceCallBack the service call back
     */
    public void setServiceCallBack(ServiceCallBack serviceCallBack) {
        this.serviceCallBack = serviceCallBack;
    }

    /**
     * Gets request tag.
     *
     * @return the request tag
     */
    public int getRequestTag() {
        return requestTag;
    }

    /**
     * Request callback callback.
     *
     * @return the callback
     */
    public Callback requestCallback() {

        return callback;
    }

    /**
     * Sets request tag.
     *
     * @param requestType the request type
     */
    public void setRequestTag(int requestType) {
        this.requestTag = requestType;
    }


    /**
     * Gets retrofit.
     *
     * @param isShowLoading the is show loading
     * @return the retrofit
     */
    public IApi getRetrofit(boolean isShowLoading) {
        //Umesh Change : 29 Nov 2019 (Bypass SSL Certificate Error)
        // OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        OkHttpClient.Builder httpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.readTimeout(60, TimeUnit.SECONDS);


//        try {
////            if(App.Companion.getInstance().showApiLog)
////                httpClient.addInterceptor(App.Companion.getInstance().chuckInterceptor);
//
//            if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("release"))
//                App.Companion.getInstance().getChuckInterceptor().showNotification(false);
//            else
//                App.Companion.getInstance().getChuckInterceptor().showNotification(true);

//            httpClient.addInterceptor(App.Companion.getInstance().getChuckInterceptor());
//        } catch (Exception e) {
//        }

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                builder.addHeader("Accept", "application/json");
//                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Content-Type", "multipart/form-data");
                builder.addHeader("Device-Type", "android");
                builder.addHeader("Version-Code", "1");


                if (AppManager.isUserLoggedIn())
                    builder.header("Authorization", "Bearer " + AppManager.getUser().getToken());
                //builder.header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2luc3RhcGV0LjI0bGl2ZWhvc3QuY29tL2FwaS92MS9hdXRoL2xvZ2luIiwiaWF0IjoxNTc5MjU4NTM1LCJuYmYiOjE1NzkyNTg1MzUsImp0aSI6Im9HS212dVpEMzhHbE45UlIiLCJzdWIiOjIwMSwicHJ2IjoiOTRkYmQ5NjFhYWVmMGUzY2U2NmFkN2Q1MGU2NDc3MTc2MDlkZGEyNCIsImlkIjoyMDEsImZpcnN0X25hbWUiOiJLdW1hciIsImxhc3RfbmFtZSI6IiIsImVtYWlsIjoiYXZpQHlvcG1haWwuY29tIiwicGljdHVyZSI6Imh0dHBzOi8vd3d3LmdyYXZhdGFyLmNvbS9hdmF0YXIvOWFmZDg0MmI1NWE3ZDg0YzgyODhmMjY4MTkxYmM3ODIuanBnP3M9ODAmZD1tbSZyPWciLCJjb25maXJtZWQiOjEsInVzZXJfdHlwZSI6bnVsbCwicm9sZSI6IlVzZXIiLCJwZXJtaXNzaW9ucyI6W3siaWQiOjIsIm5hbWUiOiJ2aWV3LWZyb250ZW5kIiwiZGlzcGxheV9uYW1lIjoiVmlldyBGcm9udGVuZCIsInNvcnQiOjIsInN0YXR1cyI6MSwiY3JlYXRlZF9ieSI6MSwidXBkYXRlZF9ieSI6bnVsbCwiY3JlYXRlZF9hdCI6IjIwMTktMDItMjggMDc6MTg6NTgiLCJ1cGRhdGVkX2F0IjoiMjAxOS0wMi0yOCAwNzoxODo1OCIsImRlbGV0ZWRfYXQiOm51bGwsInBpdm90Ijp7InVzZXJfaWQiOjIwMSwicGVybWlzc2lvbl9pZCI6Mn19XSwic3RhdHVzIjoxLCJjcmVhdGVkX2F0IjoiMjAxOS0xMS0xOFQxNjoyNjo1MyswNTozMCIsInVwZGF0ZWRfYXQiOiIyMDIwLTAxLTE3VDEwOjUxOjAwKzA1OjMwIn0.2V6C7MIZPc67qZ0T8NmeazWwxaJBHA8tn33jO0aaIWk");

                Request request = builder.method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(httpClient.build())
                .baseUrl(AppConstants.BASE_URL)
                .build();

        if (isShowLoading)
            showLoading();

        IApi api = retrofit.create(IApi.class);
        return api;
    }

    /**
     * Show loading.
     */
    private void showLoading() {
        Util.showProgress(context);
    }

    /**
     * Dismiss loading.
     */
    public void dismissLoading() {
        Util.hideProgress();
    }


}
