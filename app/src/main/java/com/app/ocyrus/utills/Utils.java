package com.app.ocyrus.utills;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.ocyrus.AuthActivity;
import com.app.ocyrus.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Avinash Sharma on 2/20/2018.
 * at http://www.dotsquares.com/
 */
public class Utils {

    /**
     * Gets screen height.
     *
     * @param activity the activity
     * @param percent  the percent
     * @return the screen height
     */
    public static int getScreenHeight(Activity activity, int percent) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        return height / percent;
    }

    /**
     * Gets screen width.
     *
     * @param activity the activity
     * @param percent  the percent
     * @return the screen width
     */
    public static int getScreenWidth(Activity activity, int percent) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        return width / percent;
    }


    /**
     * Is gps enabled boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static Boolean isGpsEnabled(Context context) {
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * The Password regex.
     */
    static String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";


    /**
     * Method to close Hardware keyboard
     *
     * @param context - represent context ( activity / fragment )
     */
    public static void closeKeyBoard(Context context) {
        View view = ((Activity) context).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static MaterialDialog.Builder builder;
    /**
     * The constant materialDialog.
     */
    public static MaterialDialog materialDialog;

    public static void showDialog(Context context, String msg, MaterialDialog.SingleButtonCallback okListener, boolean cancealble) {

        try {

            builder = new MaterialDialog.Builder(context)
                    .content(msg)
                    .onPositive(okListener)
                    .positiveText(R.string.ok)
                    .cancelable(cancealble);

            if (!materialDialog.isShowing())
                materialDialog = builder.show();

        } catch (Exception e) {
            new MaterialDialog.Builder(context)
                    .content(msg)
                    .onPositive(okListener)
                    .positiveText(R.string.ok)
                    .cancelable(cancealble)
                    .show();
            e.printStackTrace();
        }

    }

    public static void showListDialog(Context context, String title, ArrayList<String> arrayList, MaterialDialog.ListCallback callback) {
        if (context != null) {

            new MaterialDialog.Builder(context)
                    .title(title)
                    .items(arrayList)
                    .itemsCallback(callback)
                    .positiveText("")
                    .negativeText("")
                    .negativeColor(context.getResources().getColor(R.color.colorPrimary))
                    .positiveColor(context.getResources().getColor(R.color.colorPrimary))
                    .show();
        }
    }

    public static boolean connectionNet(Context context) {
        if (context == null)
            return false;

        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static void showUpdateDialog(String message, Context context) {

        try {
            androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
            LayoutInflater inflater = ((AuthActivity) (context)).getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.splash_version_update, null);
            dialogBuilder.setView(dialogView);

            androidx.appcompat.app.AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            final CardView cv_positive = dialogView.findViewById(R.id.cv_positive);
            final CardView cv_negative = dialogView.findViewById(R.id.cv_negative);
            final TextView tv_message = dialogView.findViewById(R.id.tv_message);
            tv_message.setText(message);

            cv_positive.setOnClickListener(view -> {
                alertDialog.dismiss();
                Util.goToPlayStore(context);
            });

            cv_negative.setOnClickListener(view -> {
                alertDialog.dismiss();
                ((AuthActivity) (context)).finish();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showTechnicalErrorDialog(final Context context) {

        if (context != null) {

            try {
                new MaterialDialog.Builder(context)
                        //.iconRes(R.drawable.technical_error_icon)
                        .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                        .title(R.string.error)
                        .content(R.string.technical_error_msg, true)
                        .positiveText(R.string.ok)
                        .show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Close key board.
     *
     * @param context the context
     * @param view    the view
     */
    public static void closeKeyBoard(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Method to Convert time from one format to another format.
     *
     * @param oldFormat - represent old format(such as: yyyy-MM-dd HH:mm:ss)
     * @param newFormat - represent new format(such as: yyyy/MM/dd HH:mm:ss)
     * @param time      - represent old formatted time
     * @return new formatted time String
     */
    public static String convertTimeFormat(String oldFormat, String newFormat, String time) {
        SimpleDateFormat oldFmt = new SimpleDateFormat(oldFormat);
        SimpleDateFormat newFmt = new SimpleDateFormat(newFormat);

        try {
            return newFmt.format(oldFmt.parse(time));
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Method to check GPS is on/off
     *
     * @param context - represent context ( Activity / Fragment)
     * @return - return true when GPS is on otherwise false
     */
    public static boolean isGpsOn(Context context) {

        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return statusOfGPS;
    }


    /**
     * Method to show snack bar msg
     *
     * @param context - represent context ( Activity / Fragment)
     * @param msg     - represent a string msg which will be show in snack bar
     */
    public static void showSnackBar(Context context, String msg) {

        Snackbar.make(((Activity) context).findViewById(android.R.id.content), "" + msg, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Method to show a Toast msg
     *
     * @param context - represent context ( Activity / Fragment)
     * @param msg     - represent a string msg which will be show in Toast
     */


    public static void showToast(Context context, String msg) {
        Toast.makeText(context, "" + msg, Toast.LENGTH_LONG).show();

    }


    /**
     * Method to open next activity
     *
     * @param context - represent context ( Activity / Fragment)
     * @param aClass  - represent class file which will be open (on next)
     */
    public static void sendToNextActivity(Context context, Class aClass) {

        Intent intent = new Intent(context, aClass);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);

    }

    /**
     * Send to next activity.
     *
     * @param context        the context
     * @param aClass         the a class
     * @param clearBackstack the clear backstack
     */
    public static void sendToNextActivity(Context context, Class aClass, boolean clearBackstack) {

        Intent intent = new Intent(context, aClass);

        if (clearBackstack)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);

    }


    /**
     * Vibrate.
     *
     * @param context the context
     * @param millis  the millis
     */
    public static void vibrate(Context context, long millis) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(millis, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(millis);
        }
    }


    /**
     * Show error sneaker.
     *
     * @param context the context
     * @param msg     the msg
     */
    public static void showErrorSneaker(Context context, String msg) {

        Sneaker.with((Activity) context)
                .setTitle(context.getString(R.string.app_name))
                .setMessage(msg+"")
                .sneakError();
//        speak(msg);
//        vibrate(context, 500);
    }

    /**
     * Show error sneaker.
     *
     * @param context the context
     * @param msg     the msg
     * @param view    the view
     */
    public static void showErrorSneaker(Context context, String msg, View view) {

        Sneaker.with((Activity) context)
                .setTitle(context.getString(R.string.app_name))
                .setMessage(msg+".")
                .sneakError();
//        vibrate(context, 500);
//        speak(msg);
//        YoYo.with(Techniques.Shake).duration(500).playOn(view);
    }

    public static void showErrorExtendSneaker(Context context, String msg, View view) {

        Sneaker.with((Activity) context)
                .setTitle(context.getString(R.string.app_name))
                .setMessage(msg+".")
                .sneakError();
//        vibrate(context, 2000);
//        speak(msg);
//        YoYo.with(Techniques.Shake).duration(2000).playOn(view);
    }


    /**
     * Method to show snack bar on top
     *
     * @param context - represent context ( Activity / Fragment)
     * @param msg     - msg to display
     */
    public static void showSuccessSneaker(Context context, String msg) {

        if(!msg.endsWith(".")){
            msg=msg+".";
        }

        try {
            Sneaker.with((Activity) context)
                    .setTitle(context.getString(R.string.app_name))
                    .setMessage(msg)
                    .sneakSuccess();
        }catch (Exception e){e.printStackTrace();}
    }

    /**
     * Show success sneaker title.
     *
     * @param context the context
     * @param msg     the msg
     */
    public static void showSuccessSneakerTitle(Context context, String msg) {
        Sneaker.with((Activity) context)
                .setTitle(msg)
                .sneakSuccess();
    }


    /**
     * Show warning sneaker.
     *
     * @param context the context
     * @param msg     the msg
     */
    public static void showWarningSneaker(Context context, String msg) {

        Sneaker.with((Activity) context)
                .setTitle(context.getString(R.string.app_name))
                .setMessage(msg)
                .sneakWarning();
//        speak(msg);
//        vibrate(context, 2000);
    }

    /**
     * Show warning sneaker.
     *
     * @param context the context
     * @param msg     the msg
     * @param view    the view
     */
    public static void showWarningSneaker(Context context, String msg, View view) {

        Sneaker.with((Activity) context)
                .setTitle(context.getString(R.string.app_name))
                .setMessage(msg, R.color.white)
             //   .setIcon(R.drawable.ic_warning)

                .sneakError();
//        YoYo.with(Techniques.Shake).duration(500).playOn(view);
//        vibrate(context, 200);
//        speak(msg);
    }

    /**
     * Shake.
     *
     * @param context the context
     * @param view    the view
     */
    public static void shake(Context context, View view) {
//        YoYo.with(Techniques.Shake).duration(500).playOn(view);
//        vibrate(context, 200);
    }

    /**
     * Method to show snack bar on top
     *
     * @param context - represent context ( Activity / Fragment)
     * @param title   the title
     * @param msg     - msg to display
     */


    public static void showSneaker(Context context, String title, String msg) {

        Sneaker.with((Activity) context)
                .setTitle(title)
                .setMessage(msg)
//                .setTypeface(Typeface.createFromAsset(this.getAssets(), "font/" + "Slabo27px-Regular.otf"))
                .sneakWarning();
    }


    /**
     * Method to get string value from edit text
     *
     * @param editText - edit text
     * @return - string value
     */
    public static String getString(EditText editText) {

        if (!TextUtils.isEmpty(editText.getText().toString())) {

            return editText.getText().toString().trim();
        } else {

            return "";
        }

    }

    /**
     * Method to get string value from textView
     *
     * @param textView - textView
     * @return - string value
     */
    public static String getString(TextView textView) {

        return textView.getText().toString().trim();
    }


    // "^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$"

    /**
     * Is valid password boolean.
     *
     * @param target the target
     * @return the boolean
     */
    public static boolean isValidPassword(CharSequence target) {

        return (!TextUtils.isEmpty(target) && ((String) target).matches(passwordRegex));
    }


    /**
     * Method to check email is valid or not
     *
     * @param target - email string
     * @return - when email is valid return true otherwise false
     */
    public static boolean isValidEmail(CharSequence target) {

        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());

    }


    /**
     * Method to get RoundOfDouble
     *
     * @param target - email string
     * @return - when email is valid return true otherwise false
     */
    public static String getRounfOF(Float target) {

        return new DecimalFormat("0.00").format(target);

    }


    /**
     * Method to check internet connection and show not internet dialog
     *
     * @param context - context
     * @return - true/false
     */
    /*public static boolean checkInternet(Context context) {

        if (connectionNet(context)) {

            return true;
        } else {

            //noInternetDialog(context);
            return false;
        }
    }*/

    /**
     * Method to check internet is ON/OFF
     *
     * @param context - fragment/activity context
     * @return - when internet is on return true otherwise else
     */
    /*public static boolean connectionNet(Context context) {
        if (context == null)
            return false;

        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }*/


}
