package com.app.ocyrus.utills;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.app.ocyrus.AppConstants;
import com.app.ocyrus.AuthActivity;
import com.google.gson.Gson;
import com.app.ocyrus.utills.User;

/**
 * The type App manager.
 */
public class AppManager {

    /**
     * The constant REMOTE_DATA_BASE_URL.
     */
    private static final String REMOTE_DATA_BASE_URL = "https://dsecommerce.24livehost.com/";
    /**
     * The constant isChatActVisible.
     */
    private static boolean isChatActVisible = false;
    /**
     * The constant instance.
     */
    private static AppManager instance;
    /**
     * The constant sPreferences.
     */
    private static SharedPreferences sPreferences;
    /**
     * The constant mFcmRegId.
     */
    private static String mFcmRegId;
    /**
     * The constant sUser.
     */
    private static User sUser;

    private static  Context appContext;


    public static int cartCount = 0;

    public static Context getAppContext() {
        return appContext;
    }

    /**
     * Instantiates a new App manager.
     *
     * @param context the context
     */
    public AppManager(Context context) {
        appContext = context;
        if (instance == null)
            sPreferences = (context.getApplicationContext()).getSharedPreferences(PrefKeys.PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static AppManager getInstance() {
        return instance;
    }

    /**
     * Sets instance.
     *
     * @param instance the instance
     */
    public synchronized static void setInstance(AppManager instance) {
        if (AppManager.instance == null)
            AppManager.instance = instance;
    }

    /**
     * Gets preferences.
     *
     * @param context the context
     * @return the preferences
     */
    public static SharedPreferences getPreferences(Context context) {
        if (sPreferences == null)
            sPreferences = (context.getApplicationContext()).getSharedPreferences(PrefKeys.PREF_NAME, Context.MODE_PRIVATE);
        return sPreferences;
    }

    /**
     * Gets preferences.
     *
     * @return the preferences
     */
    public static SharedPreferences getPreferences() {
        return sPreferences;
    }

    /**
     * Gets fcm reg id.
     *
     * @return the fcm reg id
     */
    public static String getFcmRegId() {
        if (mFcmRegId == null)
            mFcmRegId = sPreferences.getString(PrefKeys.PREF_FCM_ID, null);
        return mFcmRegId;
    }

    /**
     * Sets fcm reg id.
     *
     * @param fcmRegId the fcm reg id
     */
    public static void setFcmRegId(String fcmRegId) {
        sPreferences.edit().putString(PrefKeys.PREF_FCM_ID, fcmRegId).apply();
        mFcmRegId = fcmRegId;
    }

    public static void setIsSocial(String isSocial) {
        sPreferences.edit().putString(PrefKeys.IS_SOCIAL, isSocial).apply();
    }

    public static String getIsSocial() {
        return sPreferences.getString(PrefKeys.IS_SOCIAL, "");
    }

    public static void setIsGuest(String isGuest) {
        sPreferences.edit().putString(PrefKeys.IS_GUEST, isGuest).apply();
    }

    public static String getIsGuest() {
        return sPreferences.getString(PrefKeys.IS_GUEST, "");
    }

    public static void setGuestFlag(String guestFlag) {
        sPreferences.edit().putString(PrefKeys.GUEST_FLAG, guestFlag).apply();
    }

    public static String getGuestFlag() {
        return sPreferences.getString(PrefKeys.GUEST_FLAG, "");
    }



    /**
     * Has fcm reg id boolean.
     *
     * @return the boolean
     */
    public static boolean hasFcmRegId() {
        return !TextUtils.isEmpty(getFcmRegId());
    }

    /**
     * Gets device id.
     *
     * @return the device id
     */
    public static String getDeviceID() {
        return sPreferences.getString(PrefKeys.PREF_DEVICE_ID, null);
    }

    /**
     * Put device id.
     *
     * @param deviceId the device id
     */
    public static void putDeviceID(String deviceId) {
        sPreferences.edit().putString(PrefKeys.PREF_DEVICE_ID, deviceId).apply();
    }

    /**
     * Gets remote data base url.
     *
     * @return the remote data base url
     */
    public static String getRemoteDataBaseUrl() {
        return REMOTE_DATA_BASE_URL;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
// region User related
    public static User getUser() {
        if (sUser == null && sPreferences.contains(PrefKeys.PREF_USER))
            try {
                String s = sPreferences.getString(PrefKeys.PREF_USER, null);
                sUser = (new Gson()).fromJson(s, User.class);
            } catch (Exception e) {
                sUser = null;
            }
        return sUser;
    }


    /**
     * Sets user.
     *
     * @param user the user
     */
    public static void setUser(User user) {
        if (sPreferences == null) return;
        sPreferences.edit().putString(PrefKeys.PREF_USER, (new Gson()).toJson(user)).apply();
        sUser = user;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public static String getUserName() {
        if (getUser() == null) return null;
//        if (getUser().getSurname() == null && getUser().getSurname() == null) return null;
        String name = "";
        if (getUser().getName() != null) name = name.concat(getUser().getName());

        return name;
    }

    /**
     * Is user logged in boolean.
     *
     * @return the boolean
     */
    public static boolean isUserLoggedIn() {
        return getUser() != null && getUser().getToken() != null;
//        return getUser() != null;
    }

    /**
     * Logout user.
     *
     * @param context the context
     */
    public static void logoutUser(Context context) {
        String fcmToken = SharedPref.getInstance(context).readString(SharedPref.FCM_TOKEN);
        boolean rememmber = SharedPref.getInstance(context).readBoolean(SharedPref.REMEMBER);
        String EMAIL = SharedPref.getInstance(context).readString(SharedPref.EMAIL);
        String PASSWORD = SharedPref.getInstance(context).readString(SharedPref.PASSWORD);

        //LoginManager.getInstance().logOut();

        sPreferences.edit().remove(PrefKeys.PREF_USER).apply();
        sPreferences.edit().putString(PrefKeys.IS_SOCIAL, "").apply();
        sPreferences.edit().putString(PrefKeys.IS_GUEST, "").apply();
        sPreferences.edit().putString(PrefKeys.GUEST_FLAG, "").apply();
        sUser = null;

        SharedPref.getInstance(context).clearAll();

        SharedPref.getInstance(context).writeString(SharedPref.FCM_TOKEN, fcmToken);
        SharedPref.getInstance(context).writeBoolean(SharedPref.REMEMBER, rememmber);
        SharedPref.getInstance(context).writeString(SharedPref.EMAIL, EMAIL);
        SharedPref.getInstance(context).writeString(SharedPref.PASSWORD, PASSWORD);
        context.startActivity(new Intent(context, AuthActivity.class).putExtra(AppConstants.IS_LOGOUT,true).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    /**
     * The interface Pref keys.
     */
    private interface PrefKeys {
        /**
         * The constant PREF_NAME.
         */
        String PREF_NAME = "dia";
        /**
         * The constant PREF_FCM_ID.
         */
        String PREF_FCM_ID = "fcmii";

        String IS_SOCIAL = "isSocial";

        String IS_GUEST = "isGuest";

        String GUEST_FLAG = "guestFlag";

        /**
         * The constant PREF_DEVICE_ID.
         */
        String PREF_DEVICE_ID = "dev_ice_iD";
        /**
         * The constant PREF_USER.
         */
        String PREF_USER = "Er_us:)";
    }

    /**
     * Is is chat act visible boolean.
     *
     * @return the boolean
     */
    public static boolean isIsChatActVisible() {
        return isChatActVisible;
    }

    /**
     * Sets is chat act visible.
     *
     * @param isChatActVisible the is chat act visible
     */
    public static void setIsChatActVisible(boolean isChatActVisible) {
        AppManager.isChatActVisible = isChatActVisible;
    }
}