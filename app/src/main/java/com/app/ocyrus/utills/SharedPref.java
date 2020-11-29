package com.app.ocyrus.utills;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Avinash Sharma on 2/20/2018.
 * at http://www.dotsquares.com/
 */
public class SharedPref {
    /**
     * The constant FCM_TOKEN.
     */
    public static final String REMEMBER = "REMEMBER";
    public static final String FCM_TOKEN = "fcm_token";
    public static final int REQUEST_SEARCH_CODE = 101;

    /**
     * The constant PREF_NAME.
     */
// SharedPreferences name
    private final static String PREF_NAME = "my_application";


    /**
     * The constant DEFAULT_STRING_VALUE.
     */
// default values
    private final static String DEFAULT_STRING_VALUE = "";
    /**
     * The constant DEFAULT_BOOLEAN_VALUE.
     */
    private final static boolean DEFAULT_BOOLEAN_VALUE = false;
    /**
     * The constant DEFAULT_INTEGER_VALUE.
     */
    private final static int DEFAULT_INTEGER_VALUE = 0;


    // SharedPreferences key

    /**
     * The constant IS_LOGIN.
     */
    public static final String IS_LOGIN = "is_login";

    /**
     * The constant ACCOUNT_STATUS.
     */
    public static final String ACCOUNT_STATUS = "account_status";

    /**
     * The constant IS_VERIFIED.
     */
    public static final String IS_VERIFIED = "is_verified";
    /**
     * The constant IS_STRIPE_ADDED.
     */
    public static final String IS_STRIPE_ADDED = "is_stripe_added";
    /**
     * The constant IS_STRIPE_UPDATED.
     */
    public static final String IS_STRIPE_UPDATED = "is_stripe_updated";
    /**
     * The constant IS_FIRST_SERVICE_ADDED.
     */
    public static final String IS_FIRST_SERVICE_ADDED = "IS_FIRST_SERVICE_ADDED";

    /**
     * The constant IS_FIRST_TIME.
     */
    public static final String SHOW_API_LOGGING = "SHOW_API_LOGGING";

    /**
     * The constant IS_FIRST_TIME.
     */
    public static final String IS_FIRST_TIME = "is_first_time";
    /**
     * SHOW_API_LOGGING
     */
    public static final String LOCATIONS = "locations";
    /**
     * The constant CURRENT_DATE.
     */
    public static final String CURRENT_DATE = "current_date";
    /**
     * The constant RIDE.
     */
    public static final String RIDE = "ride";
    /**
     * The constant LOGIN_TYPE.
     */
    public static final String LOGIN_TYPE = "login_type";
    /**
     * The constant USER_ID.
     */
    public static final String USER_ID = "user_id";
    /**
     * The constant AUTH_TOKEN.
     */
    public static final String AUTH_TOKEN = "auth_token";
    /**
     * The constant CRASH_COUNT.
     */
    public static final String CRASH_COUNT = "crash_count";

    /**
     * The constant isAdavanceProfileCompleted.
     */
    public static final String isAdavanceProfileCompleted = "isAdavanceProfileCompleted";
    /**
     * The constant isBasicProfileUpdated.
     */
    public static final String isBasicProfileUpdated = "isBasicProfileUpdated";

    /**
     * The constant CHUCK_ENABLED.
     */
    public static final String CHUCK_ENABLED = "CHUCK_ENABLED";

    /**
     * The constant FILTER.
     */
    public static final String FILTER = "filter";

    /**
     * The constant COUNTRY_LIST.
     */
    public static final String COUNTRY_LIST = "country_list";
    /**
     * The constant STATE_LIST.
     */
    public static final String STATE_LIST = "state_list";
    /**
     * The constant SPECIALITY_LIST.
     */
    public static final String SPECIALITY_LIST = "speciality_list";
    /**
     * The constant DISEASE_LIST.
     */
    public static final String DISEASE_LIST = "disease_list";
    /**
     * The constant MIN_PRICE.
     */
    public static final String MIN_PRICE = "min_price";
    /**
     * The constant MAX_PRICE.
     */
    public static final String MAX_PRICE = "max_price";
    /**
     * The constant SELECTED_COUNTRY.
     */
    public static final String SELECTED_COUNTRY = "SELECTED_COUNTRY";
    /**
     * The constant SELECTED_STATE.
     */
    public static final String SELECTED_STATE = "SELECTED_STATE";
    /**
     * The constant SELECTED_SPECIALITY.
     */
    public static final String SELECTED_SPECIALITY = "SELECTED_SPECIALITY";
    /**
     * The constant SELECTED_DISEASE.
     */
    public static final String SELECTED_DISEASE = "SELECTED_DISEASE";
    /**
     * The constant SELECTED_PRICE.
     */
    public static final String SELECTED_PRICE = "SELECTED_PRICE";

    /**
     * The constant CITY_NAME.
     */
    public static final String CITY_NAME = "city_name";
    /**
     * The constant ADDRESS.
     */
    public static final String ADDRESS = "address";
    public static final String API_VERSION = "api_version";
    public static final String MAP_DOWNLOADED = "MAP_DOWNLOADED";
    /**
     * The constant POST_CODE.
     */
    public static final String POST_CODE = "post_code";
    public static final String ADDRESS2 = "address2";
    /**
     * The constant LAT.
     */
    public static final String LAT = "lat";
    /**
     * The constant LNG.
     */
    public static final String LNG = "lng";


    /**
     * The constant OFFLINE_ROOM_ID.
     */
    public static final String OFFLINE_ROOM_ID = "OFFLINE_ROOM_ID";
    /**
     * The constant CATEGORIES.
     */
    public static final String CATEGORIES = "CATEGORIES";
    /**
     * The constant TOPICS.
     */
    public static final String TOPICS = "topics";
    /**
     * The constant IS_PATIENT_PROFILE_UPDATED.
     */
    public static final String IS_PATIENT_PROFILE_UPDATED = "IS_PATIENT_PROFILE_UPDATED";
    /**
     * The constant IS_EXPIRED.
     */
    public static final String IS_EXPIRED = "IS_EXPIRED";
    /**
     * The constant IS_SERVICE_PROVIDER.
     */
    public static final String IS_SERVICE_PROVIDER = "IS_SERVICE_PROVIDER";
    /**
     * The constant USER_TYPE.
     */
    public static final String USER_TYPE = "USER_TYPE";
    /**
     * The constant SERVICE_PROVIDER.
     */
    public static final String SERVICE_PROVIDER = "SERVICE_PROVIDER";
    /**
     * The constant USER.
     */
    public static final String USER = "USER";
    /**
     * The constant FIRST_NAME.
     */
    public static final String FIRST_NAME = "first_name";
    /**
     * The constant LAST_NAME.
     */
    public static final String LAST_NAME = "last_name";
    /**
     * The constant EMAIL.
     */
    public static final String EMAIL = "email";

    public static final String PASSWORD = "password";
    /**
     * The constant MOBILE.
     */
    public static final String MOBILE = "mobile";
    /**
     * The constant STRIPE_URL.
     */
    public static final String STRIPE_URL = "stripe_url";
    /**
     * The constant STRIPE_CONNECT.
     */
    public static final String STRIPE_CONNECT = "stripe_connect";
    /**
     * The constant LATITUDE.
     */
    public static final String LATITUDE = "latitude";
    /**
     * The constant LONGITUDE.
     */
    public static final String LONGITUDE = "longitude";


    /**
     * The Shared preferences.
     */
//SharedPreferences reference
    private SharedPreferences sharedPreferences;

    /**
     * The constant sharedPref.
     */
//SharedPref reference
    private static SharedPref sharedPref;

    /**
     * Instantiates a new Shared pref.
     */
// default constructor
    private SharedPref() {
    }


    /**
     * Method to initialize SharedPreferences
     *
     * @param context - represent application context
     */
    public static void init(Context context) {
        if (sharedPref == null) {
            sharedPref = new SharedPref();
            sharedPref.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    /**
     * Method to get Instance of SharedPref
     *
     * @return - return SharedPref instance
     */
    public static SharedPref getInstance(Context context) {
        if (sharedPref == null) {
            init(context);
        }
        return sharedPref;
    }


    /**
     * Method to save a string value in SharedPreferences
     *
     * @param key   - represent key string
     * @param value - represent value ( string )  Here data stored in key value pair in SharedPreferences
     */
    public void writeString(String key, String value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(key, value);
        editor.commit();
        editor.apply();
    }

    /**
     * Method to read a string value from sharedPreferences using key
     *
     * @param key - represent key string
     * @return - return string value corresponding to key
     */
    public String readString(String key) {
        return sharedPreferences.getString(key,DEFAULT_STRING_VALUE);
    }

    /**
     * Method to save a boolean value in SharedPreferences
     *
     * @param key   - represent key string
     * @param value - represent value ( boolean )  Here data stored in key value pair in SharedPreferences
     */
    public void writeBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(key, value);
        editor.commit();
        editor.apply();
    }

    /**
     * Method to read a boolean value from sharedPreferences using key
     *
     * @param key - represent key string
     * @return - return  boolean value  corresponding to key
     */
    public boolean readBoolean(String key) {
        return sharedPreferences.getBoolean(key, DEFAULT_BOOLEAN_VALUE);
    }

    /**
     * Method to save a Integer value in SharedPreferences
     *
     * @param key   - represent key string
     * @param value - represent value ( Integer )  Here data stored in key value pair in SharedPreferences
     */
    public void writeInteger(String key, int value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * Method to read a Integer value from sharedPreferences using key
     *
     * @param key - represent key string
     * @return - return Integer value  corresponding to key
     */
    public int readInteger(String key) {
        return sharedPreferences.getInt(key, DEFAULT_INTEGER_VALUE);
    }

    /**
     * Method to remove any value from sharedPreferences using key
     *
     * @param key - represent key string
     */
    public void removeValue(String key) {
        SharedPreferences.Editor editor = getEditor();
        editor.remove(key);
        editor.commit();
    }

    /**
     * Method to get editor
     *
     * @return - return SharedPreferences.Editor
     */
    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

    /**
     * Method to clear all sharedPreferences Data
     */
    public void clearAll() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
