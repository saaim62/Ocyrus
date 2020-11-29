package com.app.ocyrus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppConstants {

    /* *****************************    Constant Fields    *******************************/

    public static final int CODE_ACCOUNT_NOT_VERIFIED = 449;
    /**
     * The constant CODE_ACCOUNT_UNAUTHORIZED.
     */
    public static final int CODE_ACCOUNT_UNAUTHORIZED = 401;

    public static final int CODE_VERSION_MISMATCH = 503;
    /**
     * The constant CODE_INTERNAL_SERVER_ERROR.
     */
    public static final int CODE_INTERNAL_SERVER_ERROR = 500;

    public static final String IS_ORDER_PLACED = "isOrderPlaced";
    public static final String IS_LOGOUT = "isLogout";

    public static final String ERROR_FILED_CANNOT_NULL = "Field cannot be blank";

    public static final String PRODUCT_DETAILS_FLAG = "PRODUCT_DETAILS";
    public static final String PRODUCT_LIST_FLAG = "PRODUCT_LIST";
    public static final String WISH_LIST_FLAG = "WISH_LIST";
    public static final String SETTING_FLAG = "PRODUCT_LIST";

    /**
     * The constant MIN_PRICE.
     */
    public static final String MIN_PRICE = "1";
    /**
     * The constant MAX_PRICE.
     */
    public static final String MAX_PRICE = "500";

    /**
     * The constant ERROR_ENTER_NAME.
     */
    public static final String ERROR_ENTER_FULL_NAME = "Please enter full name";
    public static final String ERROR_ENTER_USER_NAME = "Enter user name";
    /**
     * The constant ERROR_ENTER_VALID_EMAIL.
     */
    public static final String ERROR_ENTER_VALID_subj = "Please enter subject";
    public static final String ERROR_ENTER_VALID_message = "Please enter message";
    public static final String ERROR_ENTER_VALID_FNAME = "Please enter your first name";
    public static final String ERROR_ENTER_VALID_LNAME = "Please enter your last name";
    public static final String ERROR_ENTER_VALID_MOBILE = "Please enter your mobile number";
    public static final String ERROR_ENTER_VALID_EMAIL = "Please enter your email";
    public static final String ERROR_ENTER_OTP = "Please enter otp which received on your email";
    public static final String ERROR_ENTER_CORRECT_OTP = "Invalid OTP";
    public static final String ERROR_INVALID_EMAIL = "Please enter valid email";
    public static final String ERROR_ENTER_YOUR_USERNAME = "Please enter your username";
    /**
     * The constant ERROR_ENTER_VALID_PHONE_NUMBER.
     */
    public static final String ERROR_ENTER_VALID_PHONE_NUMBER = "Mobile number should be in range of 10 to 12 digits";
    /**
     * The constant ERROR_ENTER_PHONE_NUMBER.
     */
    public static final String ERROR_ENTER_PHONE_NUMBER = "Enter mobile number";

    public static final String ERROR_ENTER_ABOUT_ME = "Enter about me";

    /**
     * The constant ERROR_ENTER_PASSWORD.
     */
    public static final String ERROR_ENTER_PASSWORD = "Please enter your password";
    public static final String ERROR_ENTER_NEW_PASSWORD = "Please enter new password";
    public static final String ERROR_ENTER_OLD_PASSWORD = "Enter old password";
    public static final String ENTER_CONFIRM_PASSWORD = "Enter confirm password";
    /**
     * The constant ERROR_PASSWORD_LENGTH.
     */
    public static final String ERROR_PASSWORD_LENGTH = "Password length must be at least 5 digit";
    public static final String ERROR_Image = "Please select image";
    public static final String ERROR_PASSWORD_CHARACTER_VALIDATION1 = "Entered password should be min 8 Char long & includes 1 capital letter, 1 special character, 1 numeric";

    public static final String ERROR_PASSWORD_CHARACTER_VALIDATION = "Password must be a combination of Uppercase, Lowercase, Number, and Special Char";
    public static final String ERROR_CONFIRM_PASSWORD_LENGTH = "Confirm password length must be at least 5 digit";
    public static final String ERROR_ENTER_CONFIRM_PASSWORD = "Please insert the confirm password";
    public static final String ERROR_PASSWORD_NOT_MATCHED = "Confirm password must be same";

    public static final String ERROR_TMC_UNCHECK = "Please accept the terms and conditions";
    public static final String ERROR_PRP_UNCHECK = "Please accept the privacy policy";

    public static final String ERROR_PASSWORD_AND_CONFIRM_PASSWORD_SHOULD_BE_SAME = "Confirm password must be same";

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }


    //public static final String KEY="idqn2uydxeugrzdddgo7g0pwybsq4ruammdvosc2szywpoq1ej"; // Demo key
//    public static final String BASE_URL = "https://ocyrus.24livehost.com/public/api/";
    public static final String BASE_URL = "http://ocyrus.24livehost.com/public/api/";



    public static final String LOGIN_METHOD = "LoginRequest";
    public static final String GET_PRIVACY_METHOD = "GetTextPrivacy";
    public static final String GET_TMC_METHOD = "GetTextTerms";
    public static final String LOGOUT = "LogOut";
    public static final String RESET_PASSWORD = "ResetPassword";
    public static final String MEETING_LIST = "MeetingsList";

    public static final String RoomChangePIN = "RoomChangePIN";
    public static final String REGISTER_REQUEST = "RegisterRequest";

    public static final String IS_DEFAULT_VIDEO_OFF = "isVideoOff";
    public static final String IS_DEFAULT_AUDIO_OFF = "isAudioOff";
}


