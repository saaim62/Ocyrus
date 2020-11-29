package com.app.ocyrus.utills;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.app.ocyrus.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.Buffer;

public class Util {

   /* @BindingAdapter("bind:imageUrl")
    public static void setImageUrl(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }*/
    /**
     * The M progress dialog.
     */
    static Dialog mProgressDialog = null;
    static MediaPlayer mp;
    /**
     * The constant IMAGE_MAX_SIZE.
     */
    private static final int IMAGE_MAX_SIZE = 1024;
    /**
     * The Pattern.
     */
    private Pattern pattern;
    /**
     * The Matcher.
     */
    private Matcher matcher;

    /**
     * The constant EMAIL_ADDRESS_PATTERN.
     */
//    private static ProgressDialog mProgressDialog;
    // public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");

    // "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"

    /**
     * The constant PASSWORD_PATTERN.
     */
    private static final String PASSWORD_PATTERN =
            "(^.*(?=.{6,10})(?=.*[a-zA-Z])(?=.*\\\\d)[a-zA-Z0-9!@#$%]+$)";

    public static final String MIME_TYPE_PDF = "application/pdf";


    public static String getFileExtension(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }


    public static boolean canDisplayPdf(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType(MIME_TYPE_PDF);
        return packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }

    /**
     * Is empty boolean.
     *
     * @param charSequence the char sequence
     * @return the boolean
     */
    public static boolean isEmpty(CharSequence charSequence) {
        return TextUtils.isEmpty(charSequence);
    }

    public static String capitalize(@NonNull String input) {
        if (TextUtils.isEmpty(input))
            return "";

        String[] words = input.toLowerCase().split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            if (i > 0 && word.length() > 0) {
                builder.append(" ");
            }

            String cap = word.substring(0, 1).toUpperCase() + word.substring(1);
            builder.append(cap);
        }
        return builder.toString();
    }

    public static void goToPlayStore(Context context) {
        /*try {
            final String appPackageName = ((MainActivity) (context)).getPackageName(); // getPackageName() from Context or Activity object
            try {
                ((MainActivity) (context)).startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                ((MainActivity) (context)).finish();
            } catch (android.content.ActivityNotFoundException anfe) {
                ((MainActivity) (context)).startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                ((MainActivity) (context)).finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    /**
     * Show keyboard.
     *
     * @param context the context
     */
    public static void showKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Gets app version.
     *
     * @param context the context
     * @return the app version
     */
    public static String getAppVersion(Context context) {
        String result = "";

        try {
            result = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .versionName;
            result = result.replaceAll("[a-zA-Z]|-", "");
        } catch (PackageManager.NameNotFoundException e) {
            // Log.e(TAG, e.getMessage());
        }

        return result;
    }


    /**
     * Remove last char string.
     *
     * @param str the str
     * @return the string
     */
    public static String removeLastChar(String str) {
        return str.length() > 0 ? str.substring(0, str.length() - 1) : str;
    }

    /**
     * Logout.
     *
     * @param context the context
     */
    public static void logout(Context context) {
      /*  showMaterialDialogWithSingleButton(context, "Session Expired", "Please login again", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                ShortcutBadger.removeCount(context);

                String fcmToken = SharedPref.getFcmToken(context);
                SharedPref.clearAll();//clear all data
                SharedPref.setFcmToken(context, fcmToken);
                SharedPref.setAuthToken(context, "");
                Intent intent = new Intent(context, ActivitySliderAfterSplash.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });*/

        /*Util.showAlertBox(context, "Your session has expired, Please login", (dialog, which) -> {


        });*/

    }

    /**
     * Gets country code.
     *
     * @param context the context
     * @return the country code
     */
    public static String getCountryCode(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCodeValue = tm.getSimCountryIso();
        return countryCodeValue;
    }

    /**
     * Gets current activity name.
     *
     * @param context the context
     * @return the current activity name
     */
    public static String getCurrentActivityName(Context context) {
        ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        return cn.getClassName();
    }

    /**
     * To lower case string.
     *
     * @param str the str
     * @return the string
     */
    public static String toLowerCase(String str) {
        try {
            String space = "";


            if (TextUtils.isEmpty(str))
                return "";

            if (str.length() == 0)
                return "";

            if (str.length() >= 1 && str.substring(0, 1).equals(" "))
                space = " ";

            if (str.length() >= 2 && str.substring(0, 2).equals("  "))
                space = "  ";


            return TextUtils.isEmpty(str) ? "" : space + str.trim().substring(0, 1).toUpperCase() + str.trim().substring(1).toLowerCase();
        } catch (Exception e) {
            return "" + str;
        }

    }


    /**
     * Is phone locked boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isPhoneLocked(Context context) {
        KeyguardManager myKM = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        //it is not locked
        return myKM.inKeyguardRestrictedInputMode(); // it is locked
    }

    /**
     * Gets greeting message.
     *
     * @return the greeting message
     */
    public static String getGreetingMessage() {

        String greeting = "Good Morning";

       /* Date dt = new Date();
        int hours = dt.getHours();
        int min = dt.getMinutes();

        if (hours >= 1 && hours <= 12) {
            greeting = "Good Morning";
        } else if (hours >= 12 && hours <= 16) {
            greeting = "Good Afternoon";
        } else if (hours >= 16 && hours <= 21) {
            greeting = "Good Evening";
        } else if (hours >= 21 && hours <= 24) {
            greeting = "Good Evening";
        }

*/
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            greeting = "Good Morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            greeting = "Good Afternoon";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            greeting = "Good Evening";
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            greeting = "Good Evening";
        }


        return greeting;
    }


    /**
     * Gets body.
     *
     * @param str the str
     * @return the body
     */
    public static RequestBody getBody(String str) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), str);
    }

    /**
     * Gets week day from date.
     *
     * @param date the date
     * @return the week day from date
     */
    public static String getWeekDayFromDate(String date) {


        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy hh:mm a");
        String weekDay = "";
        try {
            Date d = format.parse(date);
            System.out.println(date);

            Calendar c = Calendar.getInstance();
            c.setTime(d); // yourdate is an object of type Date

            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            switch (dayOfWeek) {
                case 1:
                    weekDay = "SUN";
                    break;
                case 2:
                    weekDay = "MON";
                    break;
                case 3:
                    weekDay = "TUE";
                    break;
                case 4:
                    weekDay = "WED";
                    break;
                case 5:
                    weekDay = "THU";
                    break;
                case 6:
                    weekDay = "FRI";
                    break;
                case 7:
                    weekDay = "SAT";
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return weekDay;
    }

    /**
     * Formate date fromstring string.
     *
     * @param inputFormat  the input format
     * @param outputFormat the output format
     * @param inputDate    the input date
     * @return the string
     */
    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            //LOGE(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }

    /**
     * Gets device id.
     *
     * @param context the context
     * @return the device id
     */
    public static String getDeviceId(Context context) {
        //  TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        //  return telephonyManager.getDeviceId();
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * Show alert dialog.
     *
     * @param context the context
     * @param title   the title
     * @param message the message
     */
    public static void showAlertDialog(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(
                context).create();

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);


        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                dialog.dismiss();

            }
        });

        // Showing Alert Message
        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

    }


    /**
     * Show alert box.
     *
     * @param context    the context
     * @param msg        the msg
     * @param okListener the ok listener
     */
    public static void showAlertBox(Context context, String msg, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context).setMessage(msg).setPositiveButton("OK", okListener).show().setCancelable(false);


    }

    /**
     * Show alert box.
     *
     * @param context        the context
     * @param msg            the msg
     * @param okListener     the ok listener
     * @param cancelListener the cancel listener
     */
    public static void showAlertBox(Context context,
                                    String msg,
                                    DialogInterface.OnClickListener okListener,
                                    DialogInterface.OnClickListener cancelListener) {

        new AlertDialog.Builder(context).setTitle(null).setMessage(msg).setPositiveButton("Yes", okListener).setNegativeButton("No", cancelListener).show();
    }

    /**
     * Gets pixels.
     *
     * @param unit the unit
     * @param size the size
     * @return the pixels
     */
    public static int getPixels(int unit, float size) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) TypedValue.applyDimension(unit, size, metrics);
    }

    /**
     * Compare date 2 string.
     *
     * @param d the d
     * @return the string
     */
    public static String compareDate2(String d) {
        Date date1 = null, date2 = null;
        String time = "Today";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd MMM, yy");
        SimpleDateFormat originalTimeFormat = new SimpleDateFormat("hh:mm a");
        try {
            date1 = simpleDateFormat.parse(d);
            String d2 = simpleDateFormat.format(Calendar.getInstance().getTime());
            date2 = simpleDateFormat.parse(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        long difference = date2.getTime() - date1.getTime();
        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        hours = (hours < 0 ? -hours : hours);
        min = (min < 0 ? -min : min);
        //Log.i("======= Hours"," :: "+hours);
        if (days == 0) {
            time = "Today " + originalTimeFormat.format(date1);
        } else if (days == 1) {
            time = "Yesterday " + originalTimeFormat.format(date1);
        } else {
            time = originalFormat.format(date1) + " " + originalTimeFormat.format(date1);
        }
        return time;
    }


    /**
     * Is today or after today boolean.
     *
     * @param currentDate the current date
     * @param myDate      the my date
     * @return the boolean
     */
    public static boolean isTodayOrAfterToday(String currentDate, String myDate) {

        SimpleDateFormat dfDate = new SimpleDateFormat("dd-MM-yyyy");

        boolean isTodayOrAfterToday = false;

        try {
            if (dfDate.parse(currentDate).before(dfDate.parse(myDate))) {
                isTodayOrAfterToday = true;  // If currentDate is before myDate.
            }
            if (dfDate.parse(currentDate).equals(dfDate.parse(myDate))) {
                isTodayOrAfterToday = true;  // If two dates are equal.
            }
            if (dfDate.parse(currentDate).after(dfDate.parse(myDate))) {
                isTodayOrAfterToday = false; // If currentDate is after the myDate.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isTodayOrAfterToday;
    }

    /**
     * Is today or after today boolean.
     *
     * @param currentDate the current date
     * @param myDate      the my date
     * @param formate     the formate
     * @return the boolean
     */
    public static boolean isTodayOrAfterToday(String currentDate, String myDate, String formate) {

        SimpleDateFormat dfDate = new SimpleDateFormat(formate);

        boolean isTodayOrAfterToday = false;

        try {
            if (dfDate.parse(currentDate).before(dfDate.parse(myDate))) {
                isTodayOrAfterToday = true;  // If currentDate is before myDate.
            }
            if (dfDate.parse(currentDate).equals(dfDate.parse(myDate))) {
                isTodayOrAfterToday = true;  // If two dates are equal.
            }
            if (dfDate.parse(currentDate).after(dfDate.parse(myDate))) {
                isTodayOrAfterToday = false; // If currentDate is after the myDate.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isTodayOrAfterToday;
    }

    /**
     * Is today or after today with formate boolean.
     *
     * @param myDate  the my date
     * @param formate the formate
     * @return the boolean
     */
    public static boolean isTodayOrAfterTodayWithFormate(String myDate, String formate) {

        String currentDate = new SimpleDateFormat(formate, Locale.getDefault()).format(new Date());

        SimpleDateFormat dfDate = new SimpleDateFormat(formate);

        boolean isTodayOrAfterToday = false;

        try {
            if (dfDate.parse(currentDate).before(dfDate.parse(myDate))) {
                isTodayOrAfterToday = true;  // If currentDate is before myDate.
            }
            if (dfDate.parse(currentDate).equals(dfDate.parse(myDate))) {
                isTodayOrAfterToday = true;  // If two dates are equal.
            }
            if (dfDate.parse(currentDate).after(dfDate.parse(myDate))) {
                isTodayOrAfterToday = false; // If currentDate is after the myDate.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isTodayOrAfterToday;
    }

    /**
     * Is previous from today boolean.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the boolean
     */
    public static boolean isPreviousFromToday(String startDate, String endDate) {

        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");

        boolean b = false;

        try {
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                b = true;  // If start date is before end date.
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                b = false;  // If two dates are equal.
            } else if (dfDate.parse(startDate).after(dfDate.parse(endDate))) {
                b = false; // If start date is after the end date.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }

    /**
     * Gets only digits.
     *
     * @param s the s
     * @return the only digits
     */
    public static String getOnlyDigits(String s) {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(s);
        String number = matcher.replaceAll("");
        return number;
    }

    /**
     * Is before boolean.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the boolean
     */
    public static boolean isBefore(String startDate, String endDate) {

        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        boolean b = false;

        try {
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                b = true;  // If start date is before end date.
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                b = false;  // If two dates are equal.
            } else if (dfDate.parse(startDate).after(dfDate.parse(endDate))) {
                b = false; // If start date is after the end date.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }

    /**
     * Is before time boolean.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the boolean
     */
    public static boolean isBeforeTime(String startDate, String endDate) {

        SimpleDateFormat dfDate = new SimpleDateFormat("hh:mm a");

        boolean b = false;

        try {
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                b = true;  // If start date is before end date.
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                b = false;  // If two dates are equal.
            } else if (dfDate.parse(startDate).after(dfDate.parse(endDate))) {
                b = false; // If start date is after the end date.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }

    /**
     * Is after boolean.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the boolean
     */
    public static boolean isAfter(String startDate, String endDate) {

        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");

        boolean b = false;

        try {
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                b = false;  // If start date is before end date.
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                b = false;  // If two dates are equal.
            } else if (dfDate.parse(startDate).after(dfDate.parse(endDate))) {
                b = true; // If start date is after the end date.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }

    /**
     * Is equal boolean.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the boolean
     */
    public static boolean isEqual(String startDate, String endDate) {

        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");

        boolean b = false;

        try {
            b = dfDate.parse(startDate).equals(dfDate.parse(endDate));  // If two dates are equal.
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }

    /**
     * Is after time boolean.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the boolean
     */
    public static boolean isAfterTime(String startDate, String endDate) {

        SimpleDateFormat dfDate = new SimpleDateFormat("hh:mm a");

        boolean b = false;

        try {
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                b = false;  // If start date is before end date.
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                b = false;  // If two dates are equal.
            } else if (dfDate.parse(startDate).after(dfDate.parse(endDate))) {
                b = true; // If start date is after the end date.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }


    /**
     * Gets date from string.
     *
     * @param strDate   the str date
     * @param strFormat the str format
     * @return the date from string
     */
    public static Date getDateFromString(String strDate, String strFormat) {
        SimpleDateFormat format = new SimpleDateFormat(strFormat);
        Date date = null;
        try {
            date = format.parse(strDate);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Gets day of month.
     *
     * @param aDate the a date
     * @return the day of month
     */
    public static int getDayOfMonth(Date aDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        return cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    /**
     * Check deadline boolean.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the boolean
     */
    public static boolean CheckDeadline(String startDate, String endDate) {

        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");

        boolean b = false;

        try {
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                b = true;  // If start date is before end date.
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                b = true;  // If two dates are equal.
            } else if (dfDate.parse(startDate).after(dfDate.parse(endDate))) {
                b = false; // If start date is after the end date.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }

    /**
     * Compare date 1 string.
     *
     * @param d the d
     * @return the string
     */
    public static String compareDate1(String d) {
        Date date1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        try {
            date1 = simpleDateFormat.parse(d);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long smsTimeInMilis = date1.getTime();
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "hh:mm aa";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return "Today " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return "Yesterday " + DateFormat.format(timeFormatString, smsTime);
        } else {
            return DateFormat.format("MMMM dd, yyyy hh:mm aa", smsTime).toString();
        }
    }

    /**
     * Gets date time.
     *
     * @param date the date
     * @return the date time
     */
    public static String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    /**
     * Load bitmap bitmap.
     *
     * @param path        the path
     * @param orientation the orientation
     * @return the bitmap
     */
    private static Bitmap loadBitmap(String path, int orientation) {

        Bitmap bitmap = null;

        try {

            // First decode with inJustDecodeBounds=true to check dimensions
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFile(path);

            long size = bitmap.getRowBytes() * bitmap.getHeight();

            if (size < 1000000) {
                options.inSampleSize = 1;
            } else if (size < 3000000) {
                options.inSampleSize = 2;
            } else if (size < 5000000) {
                options.inSampleSize = 3;
            } else if (size < 7000000) {
                options.inSampleSize = 4;
            } else if (size < 9000000) {
                options.inSampleSize = 5;
            } else if (size < 1200000) {
                options.inSampleSize = 6;
            } else if (size > 1500000) {
                options.inSampleSize = 8;
            } else if (size > 3000000) {
                options.inSampleSize = 16;
            }

            bitmap = BitmapFactory.decodeFile(path, options);

            // Rotate the bitmap if required
            if (orientation > 0) {

                Matrix matrix = new Matrix();
                matrix.postRotate(orientation);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("In LoadBitmap ", e.toString());
        }
        return bitmap;
    }

    /**
     * Gets bitmap.
     *
     * @param uri      the uri
     * @param mContext the m context
     * @return the bitmap
     */
    public static Bitmap getBitmap(Uri uri, Context mContext) {
        InputStream in = null;
        Bitmap returnedBitmap = null;
        ContentResolver mContentResolver;
        try {
            mContentResolver = mContext.getContentResolver();
//            file:///storage/emulated/0/Pictures/aviary-image-1465806238409.jpeg
            /* if(uri.getPath().contains("file:///")) {*/
            in = mContentResolver.openInputStream(uri);
           /* }
            else{
                String file_append="file:///";
                in = mContentResolver.openInputStream(Uri.parse(file_append+uri));
            }*/
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();
            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            /*if(uri.getPath().contains("file:///")) {*/
            in = mContentResolver.openInputStream(uri);
           /* }
            else{
                String file_append="file:///";
                in = mContentResolver.openInputStream(Uri.parse(file_append+uri));
//                in = mContentResolver.openInputStream(Uri.parse(file_append+uri));
            }
*/
            Bitmap bitmap = BitmapFactory.decodeStream(in, null, o2);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            in.close();

            //First check
            ExifInterface ei = new ExifInterface(uri.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    returnedBitmap = rotateImage(bitmap, 90);
                    //Free up the memory
                    bitmap.recycle();
                    bitmap = null;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    returnedBitmap = rotateImage(bitmap, 180);
                    //Free up the memory
                    bitmap.recycle();
                    bitmap = null;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    returnedBitmap = rotateImage(bitmap, 270);
                    //Free up the memory
                    bitmap.recycle();
                    bitmap = null;
                    break;
                default:
                    returnedBitmap = bitmap;
            }
            return returnedBitmap;
        } catch (IOException e) {
            //L.e(e);
        }
        return null;
    }

    /**
     * Gets image uri.
     *
     * @param path the path
     * @return the image uri
     */
    public static Uri getImageUri(String path) {
        try {
            return Uri.fromFile(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * Gets image uri from bitmap.
     *
     * @param inContext the in context
     * @param inImage   the in image
     * @return the image uri from bitmap
     */
    public static Uri getImageUriFromBitmap(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    /**
     * Gets image content uri.
     *
     * @param context the context
     * @param absPath the abs path
     * @return the image content uri
     */
    public static Uri getImageContentUri(Context context, String absPath) {


        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                , new String[]{MediaStore.Video.Media._ID}
                , MediaStore.Video.Media.DATA + "=? "
                , new String[]{absPath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            return Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, Integer.toString(id));

        } else if (!absPath.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Video.Media.DATA, absPath);
            return context.getContentResolver().insert(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            return null;
        }
    }

    /**
     * Rotate image bitmap.
     *
     * @param source the source
     * @param angle  the angle
     * @return the bitmap
     */
    private static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    /**
     * Drawable to bitmap bitmap.
     *
     * @param drawable the drawable
     * @return the bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Show two alert dialog.
     *
     * @param context the context
     * @param title   the title
     * @param message the message
     */
    public static void showTwoAlertDialog(Context context, String title, String message) {
        // Creating alert Dialog with one Button
//    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        // Setting Dialog Title
//    alertDialog.setTitle("PASSWORD");

        // Setting Dialog Message
//    alertDialog.setMessage("Enter Password");
        final EditText edittext = new EditText(context);
        alert.setMessage("");
        alert.setTitle("Enter Your Title");

        alert.setView(edittext);

        alert.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                /*Editable YouEditTextValue = edittext.getText();
                //OR*/
                String YouEditTextValue = edittext.getText().toString();
            }
        });

        alert.setNegativeButton("No Option", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });


        // closed

        // Showing Alert Message
        alert.show();
    }


    /**
     * Close key board.
     *
     * @param context the context
     */
    /*
     * for activity
     * */
    public static void closeKeyBoard(Context context) {
        if (context instanceof Activity) {
            View view = ((Activity) context).getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /**
     * Close key board.
     *
     * @param context the context
     * @param view    the view
     */
    /*
     * for fragment
     * */
    public static void closeKeyBoard(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Hide soft keyboard.
     *
     * @param context the context
     */
    public static void hideSoftKeyboard(Activity context) {
        if (context instanceof Activity) {
            View view = ((Activity) context).getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    /**
     * Gets image uri.
     *
     * @param inContext the in context
     * @param inImage   the in image
     * @return the image uri
     */
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    /**
     * Rotate image to correct bitmap.
     *
     * @param main_image    the main image
     * @param selectedImage the selected image
     * @return the bitmap
     */
//////image capture orientation
    public static Bitmap rotateImageToCorrect(Bitmap main_image, String selectedImage) {

        Bitmap bitmap = null;

        try {
            ExifInterface ei = new ExifInterface(selectedImage);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    //bitmapProfilePic = Bitmap.createBitmap(bitmapProfilePic, 0, 0, bitmapProfilePic.getWidth(), bitmapProfilePic.getHeight(), matrix, true);
                    bitmap = Bitmap.createBitmap(main_image, 0, 0, main_image.getWidth(), main_image.getHeight(), matrix, true);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    bitmap = Bitmap.createBitmap(main_image, 0, 0, main_image.getWidth(), main_image.getHeight(), matrix, true);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    bitmap = Bitmap.createBitmap(main_image, 0, 0, main_image.getWidth(), main_image.getHeight(), matrix, true);
                    break;
                default:
                    //bitmapProfilePic = Bitmap.createBitmap(bitmapProfilePic, 0, 0, bitmapProfilePic.getWidth(), bitmapProfilePic.getHeight(), matrix, true);
                    bitmap = Bitmap.createBitmap(main_image, 0, 0, main_image.getWidth(), main_image.getHeight(), matrix, true);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * Gets bitmap from path.
     *
     * @param mActivity the m activity
     * @param filepath  the filepath
     * @return the bitmap from path
     */
    public static Bitmap getBitmapFromPath(Activity mActivity, String filepath) {

        Bitmap bitmap = loadBitmap(filepath, getCameraPhotoOrientation(mActivity.getApplicationContext(), filepath));
        return bitmap;
    }

    /**
     * Gets camera photo orientation.
     *
     * @param context   the context
     * @param imagePath the image path
     * @return the camera photo orientation
     */
    private static int getCameraPhotoOrientation(Context context, String imagePath) {

        int rotate = 0;

        try {
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            Log.v("ORIENTATION", "Exif orientation: " + orientation);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    /**
     * Data decode string.
     *
     * @param textEncoded the text encoded
     * @return the string
     */
    public static String dataDecode(String textEncoded) {
        String text = "";
        try {
            byte[] data = Base64.decode(textEncoded, Base64.DEFAULT);
            text = new String(data, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return text;
    }

    /**
     * Data encode string.
     *
     * @param textDecode the text decode
     * @return the string
     */
    public static String dataEncode(String textDecode) {
        String base64 = "";
        try {
            byte[] data = textDecode.getBytes(StandardCharsets.UTF_8);
            base64 = Base64.encodeToString(data, Base64.DEFAULT);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return base64;
    }

    /**
     * String to date date.
     *
     * @param dateTimeStr the date time str
     * @param formate     the formate
     * @return the date
     */
    public static Date stringToDate(String dateTimeStr, String formate) {

        SimpleDateFormat format = new SimpleDateFormat(formate);
        Date date = null;
        try {
            date = format.parse(dateTimeStr);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * String to utc string.
     *
     * @param dateTime the date time
     * @param formate  the formate
     * @return the string
     */
    public static String stringToUTC(String dateTime, String formate) {

        final SimpleDateFormat sdf = new SimpleDateFormat(formate);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        final String utcTime = sdf.format(stringToDate(dateTime, formate));

        return utcTime;
    }

    /**
     * Gets current time.
     *
     * @return the current time
     */
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * Gets current day.
     *
     * @return the current day
     */
    public static String getCurrentDay() {
        Calendar sCalendar = Calendar.getInstance();
        String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        return dayLongName;
    }

    /**
     * Is valid uk number boolean.
     *
     * @param number the number
     * @return the boolean
     */
    public static Boolean isValidUkNumber(String number) {
        String regex = "^(\\+44\\s?7\\d{3}|\\(?07\\d{3}\\)?)\\s?\\d{3}\\s?\\d{3}$";
        //  String regex =  "^(?:0|\\+?44)(?:\\d\\s?){9,10}$";
        //     String regex = "^\\s*\\(?(020[7,8]{1}\\)?[ ]?[1-9]{1}[0-9]{2}[ ]?[0-9]{4})|(0[1-8]{1}[0-9]{3}\\)?[ ]?[1-9]{1}[0-9]{2}[ ]?[0-9]{3})\\s*$";
//        String regex = "^(?:(?:\\(?(?:0(?:0|11)\\)?[\\s-]?\\(?|\\+)44\\)?[\\s-]?(?:\\(?0\\)?[\\s-]?)?)|(?:\\(?0))(?:(?:\\d{5}\\)?[\\s-]?\\d{4,5})|(?:\\d{4}\\)?[\\s-]?(?:\\d{5}|\\d{3}[\\s-]?\\d{3}))|(?:\\d{3}\\)?[\\s-]?\\d{3}[\\s-]?\\d{3,4})|(?:\\d{2}\\)?[\\s-]?\\d{4}[\\s-]?\\d{4}))(?:[\\s-]?(?:x|ext\\.?|\\#)\\d{3,4})?$";
        return Pattern.matches(regex, number);
    }

    /**
     * Encode string.
     *
     * @param uriString the uri string
     * @return the string
     */
    public static String encode(@NonNull String uriString) {
        if (TextUtils.isEmpty(uriString)) {
            //Assert.fail("Uri string cannot be empty!");
            return uriString;
        }
        // getQueryParameterNames is not exist then cannot iterate on queries
        if (Build.VERSION.SDK_INT < 11) {
            return uriString;
        }

        // Check if uri has valid characters
        // See https://tools.ietf.org/html/rfc3986
        Pattern allowedUrlCharacters = Pattern.compile("([A-Za-z0-9_.~:/?\\#\\[\\]@!$&'()*+,;" +
                "=-]|%[0-9a-fA-F]{2})+");
        Matcher matcher = allowedUrlCharacters.matcher(uriString);
        String validUri = null;
        if (matcher.find()) {
            validUri = matcher.group();
        }
        if (TextUtils.isEmpty(validUri) || uriString.length() == validUri.length()) {
            return uriString;
        }

        // The uriString is not encoded. Then recreate the uri and encode it this time
        Uri uri = Uri.parse(uriString);
        Uri.Builder uriBuilder = new Uri.Builder()
                .scheme(uri.getScheme())
                .authority(uri.getAuthority());
        for (String path : uri.getPathSegments()) {
            uriBuilder.appendPath(path);
        }
        for (String key : uri.getQueryParameterNames()) {
            uriBuilder.appendQueryParameter(key, uri.getQueryParameter(key));
        }
        String correctUrl = uriBuilder.build().toString();
        return correctUrl;
    }


    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @return the path
     * @author paulburke
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }

            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * Is external storage document boolean.
     *
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * Is downloads document boolean.
     *
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * Is media document boolean.
     *
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Decode file bitmap.
     *
     * @param f the f
     * @return the bitmap
     */
    public static Bitmap decodeFile(File f) {
        try {
            Bitmap returnedBitmap = null;
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 120;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
            ExifInterface ei = new ExifInterface(f.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    returnedBitmap = rotateImage(bitmap, 90);
                    //Free up the memory
                    bitmap.recycle();
                    bitmap = null;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    returnedBitmap = rotateImage(bitmap, 180);
                    //Free up the memory
                    bitmap.recycle();
                    bitmap = null;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    returnedBitmap = rotateImage(bitmap, 270);
                    //Free up the memory
                    bitmap.recycle();
                    bitmap = null;
                    break;
                default:
                    returnedBitmap = bitmap;
            }
            return returnedBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets path.
     *
     * @param uri      the uri
     * @param activity the activity
     * @return the path
     */
    public static String getPath(Uri uri, Activity activity) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /**
     * Gets recording time.
     *
     * @param totalSecs the total secs
     * @return the recording time
     */
    public static String getRecordingTime(int totalSecs) {
        String time = "";
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;
        if (seconds < 10 && minutes < 10) {
            time = "0" + minutes + ":0" + seconds;
        } else if (seconds < 10) {
            time = minutes + ":0" + seconds;
        } else if (minutes < 10) {
            time = "0" + minutes + ":" + seconds;
        } else {
            time = minutes + ":" + seconds;
        }


        return time;
    }

    /**
     * Gets bitmap path.
     *
     * @param filepath the filepath
     * @return the bitmap path
     */
    public static Bitmap getBitmapPath(String filepath) {
        Bitmap bitmap = null;
//        File sd = Environment.getExternalStorageDirectory();
        File imgFile = new File(filepath);
        if (imgFile.exists()) {
            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return bitmap;
    }

    /**
     * Gets duration.
     *
     * @param miilis the miilis
     * @return the duration
     */
    public static String getDuration(long miilis) {
        String time = "";
        int seconds = (int) (miilis / 1000) % 60;
        int minutes = (int) ((miilis / (1000 * 60)) % 60);
        int hours = (int) ((miilis / (1000 * 60 * 60)) % 24);

        if (hours > 0) {
            time = hours + ":" + minutes + ":" + seconds;
        } else {
            time = minutes + ":" + seconds;
        }
        return time;
    }

    /**
     * Null check t.
     *
     * @param <T> the type parameter
     * @param str the str
     * @return the t
     */
    @Nullable
    public static <T> T nullCheck(T str) {
        return str == null ? (T) "" : str;
    }

    /**
     * Gets last ten char.
     *
     * @param myString the my string
     * @return the last ten char
     */
    public static String getLastTenChar(String myString) {

        String replaceAll = myString.replaceAll("\\s+", "");

        if (replaceAll.length() > 10)
            return replaceAll.substring(myString.length() - 10);
        else
            return replaceAll;
    }

    /**
     * Is image file boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    /**
     * Is video file boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public static boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }

    /**
     * Convert utc to current format string.
     *
     * @param dateStr the date str
     * @return the string
     */
    public static String convertUTC_To_CurrentFormat(String dateStr) {

        if (dateStr != null) {
            String formattedDate = null;
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = df.parse(dateStr);
                df.setTimeZone(TimeZone.getDefault());
                formattedDate = df.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                formattedDate = null;
            }

            return formattedDate;
        } else {
            return null;
        }
    }

    /**
     * Gets date from utc timestamp.
     *
     * @param mTimestamp   the m timestamp
     * @param mDateFormate the m date formate
     * @return the date from utc timestamp
     */
    public static String getDateFromUTCTimestamp(long mTimestamp, String mDateFormate) {
        String date = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(mTimestamp * 1000L);
            date = DateFormat.format(mDateFormate, cal.getTimeInMillis()).toString();

            SimpleDateFormat formatter = new SimpleDateFormat(mDateFormate);
            Date value = formatter.parse(date);

            SimpleDateFormat dateFormatter = new SimpleDateFormat(mDateFormate, Locale.getDefault());
            date = dateFormatter.format(value);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * Convert ut cto local string.
     *
     * @param formate the formate
     * @param utcTime the utc time
     * @return the string
     */
    public static String convertUTCtoLocal(String formate, String utcTime) {

        String dateStr = utcTime;
        SimpleDateFormat df = new SimpleDateFormat(formate, Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = df.format(date);
        String finalTime = changeTimeFormat(formattedDate, "yyyy-MM-dd HH:mm", "yyyy-MM-dd hh:mm a");
        return finalTime;
    }

    /**
     * Convert ut cto local with day string.
     *
     * @param from    the from
     * @param to      the to
     * @param utcTime the utc time
     * @return the string
     */
    public static String convertUTCtoLocalWithDay(String from, String to, String utcTime) {

        String dateStr = utcTime;
        SimpleDateFormat df = new SimpleDateFormat(from, Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = df.format(date);
        String finalTime = changeTimeFormat(formattedDate, from, to);
        return finalTime;
    }

    /**
     * Convert ut cto local date string.
     *
     * @param formate the formate
     * @param utcTime the utc time
     * @return the string
     */
    public static String convertUTCtoLocalDate(String formate, String utcTime) {

        String dateStr = utcTime;
        SimpleDateFormat df = new SimpleDateFormat(formate, Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = df.format(date);
        // String finalTime = Util.changeTo12hourFormat(formattedDate);
        return formattedDate;
    }

    /**
     * Gets date from time stamp.
     *
     * @param timeStamp the time stamp
     * @param formate   the formate
     * @return the date from time stamp
     */
    public static String getDateFromTimeStamp(long timeStamp, String formate) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formate);
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * Change time format string.
     *
     * @param d    the d
     * @param from the from
     * @param to   the to
     * @return the string
     */
    public static String changeTimeFormat(String d, String from, String to) {
        if (TextUtils.isEmpty(d))
            return "";

        Date date1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(from, Locale.getDefault());
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(to, Locale.getDefault());
        try {
            date1 = simpleDateFormat.parse(d);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return simpleDateFormat1.format(date1);

    }

    /**
     * Change to long date long.
     *
     * @param dateStr     the date str
     * @param fromFormate the from formate
     * @return the long
     */
    public static long changeToLongDate(String dateStr, String fromFormate) {
        if (TextUtils.isEmpty(dateStr))
            return 0;

        try {
            String dateString = dateStr;
            SimpleDateFormat sdf = new SimpleDateFormat(fromFormate);
            Date date = sdf.parse(dateString);

            long timeStamp = date.getTime();
            return timeStamp;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * Change time format with ordinal string.
     *
     * @param d    the d
     * @param from the from
     * @param to   the to
     * @return the string
     */
    public static String changeTimeFormatWithOrdinal(String d, String from, String to) {
        if (TextUtils.isEmpty(d))
            return "";

        Date date1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(from, Locale.ENGLISH);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(to, Locale.ENGLISH);
        try {
            date1 = simpleDateFormat.parse(d);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return simpleDateFormat1.format(date1);

    }

    /**
     * Format value string.
     *
     * @param value the value
     * @return the string
     */
    public static String formatValue(double value) {
        try {
            int power;
            String suffix = " kmbt";
            String formattedNumber = "";

            NumberFormat formatter = new DecimalFormat("#,###.#");
            power = (int) StrictMath.log10(value);
            value = value / (Math.pow(10, (power / 3) * 3));
            formattedNumber = formatter.format(value);
            formattedNumber = formattedNumber + suffix.charAt(power / 3);
            return formattedNumber.length() > 4 ? formattedNumber.replaceAll("\\.[0-9]+", "") : formattedNumber;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // .parse(str.replaceAll("(?<=\\d)(st|nd|rd|th)", ""))


    /**
     * Gets current time.
     *
     * @param formate the formate
     * @return the current time
     */
    public static String getCurrentTime(String formate) {
        // return new SimpleDateFormat("HH:mm").format(new Date());
        return new SimpleDateFormat(formate).format(new Date());
    }


    /**
     * Show alert.
     *
     * @param context the context
     * @param title   the title
     * @param icon    the icon
     * @param message the message
     */
    public static void showAlert(Context context, String title, int icon, String message) {
       /* Alerter.create((Activity) context)
                .setTitle(title)
                .setIcon(icon)
                .setText(message)
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show();*/
    }

    /**
     * Show alert.
     *
     * @param context the context
     * @param title   the title
     * @param message the message
     */
    public static void showAlert(Context context, String title, String message) {
        Utils.showWarningSneaker(context, message);
        /*Alerter.create((Activity) context)
                .setTitle(title)
                .setText(message)
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show();*/
    }

    /**
     * Gets difference.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the difference
     */
    public static String getDifference(Date startDate, Date endDate) {
        //milliseconds
        String formatedStartDate = new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(startDate);
        String formaedEndDate = new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(endDate);

        Date dateStart = null;
        Date dateEnd = null;
        try {
            dateStart = new SimpleDateFormat("dd/M/yyyy hh:mm:ss").parse(formatedStartDate);
            dateEnd = new SimpleDateFormat("dd/M/yyyy hh:mm:ss").parse(formaedEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (dateStart != null && dateEnd != null) {
            long different = endDate.getTime() - startDate.getTime();

            System.out.println("startDate : " + startDate);
            System.out.println("endDate : " + endDate);
            System.out.println("different : " + different);

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            System.out.printf(
                    "%d days, %d hours, %d minutes, %d seconds%n",
                    elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
            String diff = "";
            if (elapsedDays != 0.0)
                diff = diff + elapsedDays + " day,";
            if (elapsedHours != 0.0)
                diff = diff + elapsedHours + " hour,";
            if (elapsedMinutes != 0.0)
                diff = diff + elapsedMinutes + " min,";
            if (elapsedSeconds != 0.0)
                diff = diff + elapsedSeconds + " sec";

            return diff;
        }

        return null;

    }

    /**
     * Gets current date.
     *
     * @param formate the formate
     * @return the current date
     */
    public static String getCurrentDate(String formate) {
        //  return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return new SimpleDateFormat(formate).format(new Date());
    }

    /**
     * Gets current locale.
     *
     * @param context the context
     * @return the current locale
     */
    static Locale getCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }

    /**
     * Convert to 24 hour string.
     *
     * @param time    the time
     * @param context the context
     * @return the string
     */
    public static String convertTo24Hour(String time, Context context) {
        try {
            SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);

            SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

            return date24Format.format(date12Format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Change to 12 hour format string.
     *
     * @param d the d
     * @return the string
     */
    public static String changeTo12hourFormat(String d) {
        Date date1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("hh:mm a", Locale.US);
        try {
            date1 = simpleDateFormat.parse(d);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return simpleDateFormat1.format(date1);

    }

    /**
     * Change to 12 hour format string.
     *
     * @param d    the d
     * @param from the from
     * @param to   the to
     * @return the string
     */
    public static String changeTo12hourFormat(String d, String from, String to) {
        Date date1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("hh:mm a", Locale.US);
        try {
            date1 = simpleDateFormat.parse(d);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return simpleDateFormat1.format(date1);

    }
  /*  public static String changeTo12hourFormatPrakharSir(String odate, String time) {

        String sDate1=odate+" "+time;
        Date date=new SimpleDateFormat("dd/MM/yyyy HH:MM").parse(sDate1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gmt = new Date(sdf.format(date));
        return gmt;

    }*/


    /**
     * Is time within interval string.
     *
     * @param time the time
     * @return the string
     */
    public static String isTimeWithinInterval(String time) {

        // Time 1 in string - Lower limit
        try {

            Calendar c = Calendar.getInstance();
            System.out.println("Current time =&gt; " + c.getTime());
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            String currentDate = df.format(c.getTime());

            if (time.compareTo(currentDate) >= 0) {
                //checkes whether the current time is between two times
                time = time;
            } else {
                time = "0";
            }


        } catch (Exception ignored) {
        }
        return time;
    }

    /**
     * Body to string string.
     *
     * @param request the request
     * @return the string
     */
    /*
     * get response from retrofit
     * */
    public static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    public static RequestBody getRequestBody(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return requestBody;
    }

    public static MultipartBody.Part getPart(File file, String key) {
        if(file!=null){
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), getRequestBody(file));
            return part;
        }else
            return null;
    }



    public static RequestBody getRequestBody(String str) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), TextUtils.isEmpty(str) ? "" : str);
        return requestBody;
    }


    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            Log.e("NULL Bitmap", "failed getViewBitmap(" + v + ")", new RuntimeException());
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }


    /**
     * Is showing boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static Boolean isShowing(Context context) {
        return mProgressDialog.isShowing();
    }

    private static AnimationDrawable animationDrawable;
    private static ImageView mProgressBar;
    public static void showProgress(Context mContext) {

      /*  if (mContext != null && mProgressDialog != null) {
            mProgressDialog = new Dialog(mContext);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.setContentView(R.layout.progress_bar_dialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
        }*/

        if (mProgressDialog == null) {
            mProgressDialog = new Dialog(mContext);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.setContentView(R.layout.dialog_progress);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);

//            if(mp == null) {
//                mp = MediaPlayer.create(mContext, R.raw.cat_meowing);
//                mp.start();
//            }


//            mProgressBar = (ImageView)mProgressDialog.findViewById(R.id.main_progress);

//            mProgressBar.setBackgroundResource(R.drawable.pika_animator);
//            animationDrawable = (AnimationDrawable)mProgressBar.getBackground();

            if (!mProgressDialog.isShowing()){
//                mProgressBar.setVisibility(View.VISIBLE);
//                animationDrawable.start();
                mProgressDialog.show();
            }

                //mProgressDialog.show();

        }
    }


    /**
     * method to hide progress
     */
    public static void hideProgress() {

        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
                mProgressDialog.cancel();
                mProgressDialog = null;

//                mProgressBar.setVisibility(View.GONE);
//                animationDrawable.stop();
            }

        } catch (Exception e) {
        }

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
                mProgressDialog.cancel();

                mProgressBar.setVisibility(View.GONE);
//                animationDrawable.stop();
            } catch (Exception e) {
            }
        }

    }

    /**
     * Bitmap resolution float [ ].
     *
     * @param view        the view
     * @param loadedImage the loaded image
     * @return the float [ ]
     */
    /*according to screen resolution show image with given bitmap height and width*/
    public static float[] bitmapResolution(final View view, final Bitmap loadedImage) {
        float requiredWidth = 0;
        float requiredHeight = 0;
        try {
            float width = (float) loadedImage.getWidth();
            float height = (float) loadedImage.getHeight();

            float ratio = height / width;
            requiredWidth = view.getWidth();
            requiredHeight = ratio * requiredWidth;
            // ((ImageView) view).setImageBitmap(Bitmap.createScaledBitmap(loadedImage, (int) requiredWidth, (int) requiredHeight, false));
        } catch (Exception e) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    try {
                        float width = (float) loadedImage.getWidth();
                        float height = (float) loadedImage.getHeight();

                        float ratio = height / width;
                        float requiredWidth = view.getWidth();
                        float requiredHeight = ratio * requiredWidth;
                        //((ImageView) view).setImageBitmap(Bitmap.createScaledBitmap(loadedImage, (int) requiredWidth, (int) requiredHeight, false));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 300);
        }
        float[] myArray = new float[2];
        myArray[0] = (requiredWidth);
        myArray[1] = (requiredHeight);
        return myArray;
    }


    /**
     * Gets video frame from video.
     *
     * @param videoPath the video path
     * @return the video frame from video
     */
    public static Bitmap getVideoFrameFromVideo(String videoPath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }


    /**
     * Is internet connection boolean.
     *
     * @param mContext the m context
     * @return the boolean
     */
    public static boolean isInternetConnection(Context mContext) {
        final ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        @SuppressLint("MissingPermission") final NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        @SuppressLint("MissingPermission") final NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable() && wifi.getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else return mobile.isAvailable() && mobile.getState() == NetworkInfo.State.CONNECTED;

        /*if (wifi.isAvailable() && wifi.getStateId() == NetworkInfo.State.CONNECTED) {
            return true;
        } else if (mobile.isAvailable() && mobile.getStateId() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }*/
    }

    /**
     * Dp to px int.
     *
     * @param dp       the dp
     * @param mContext the m context
     * @return the int
     */
    public static int dpToPx(int dp, Context mContext) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /**
     * Px to dp int.
     *
     * @param px       the px
     * @param mContext the m context
     * @return the int
     */
    public static int pxToDp(int px, Context mContext) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /**
     * Px to dp int.
     *
     * @param context the context
     * @param pixel   the pixel
     * @return the int
     */
    public static int pxToDp(Context context, int pixel) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, r.getDisplayMetrics());
    }

    /**
     * Sets grid view height based on children.
     *
     * @param gridView the grid view
     * @param columns  the columns
     */
    public static void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;


        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if (items > columns && items < 6) {
            x = items / columns;
            rows = (int) (x + 1);
            totalHeight *= rows;
        } else if (items == 6) {
            rows = 1 + 1;
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);

    }

    /**
     * Gets profile image.
     *
     * @param bitmap the bitmap
     * @return the profile image
     */
///// encode image and send to server
    public static String getProfileImage(Bitmap bitmap) {


        byte[] byteArrayImage = null;

        try {

            // Bitmap bitmap = ((BitmapDrawable)imgViewProfileBg.getDrawable()).getBitmap();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byteArrayImage = baos.toByteArray();
        } catch (Exception e) {

            Log.e("", e.toString());
            e.printStackTrace();

        }

        String imageEncoded = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        return imageEncoded;

    }

    /**
     * Is network available boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * To date string.
     *
     * @param timestamp the timestamp
     * @return the string
     */
    public static String toDate(long timestamp) {
        Date date = new Date(timestamp * 1000);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * Change time to relative time string.
     *
     * @param time the time
     * @return the string
     */
    public static String changeTimeTORelativeTime(String time) {

// it comes out like this 2013-08-31 15:55:22 so adjust the date format
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(time);
            long epoch = date.getTime();


            String timePassedString = DateUtils.getRelativeTimeSpanString(epoch, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS).toString();
            return timePassedString;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Is location enabled boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isLocationEnabled(final Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return !(!gps_enabled && !network_enabled);
    }

    /**
     * Email valilidation boolean.
     *
     * @param context the context
     * @param eMailId the e mail id
     * @return the boolean
     */
    public static boolean emailValilidation(Context context, String eMailId) {
        if (eMailId.trim().length() != 0 && EMAIL_ADDRESS_PATTERN.matcher(eMailId).matches()) {
            return true;
        } else {
            Toast.makeText(context, "Please enter valid email", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /*//**//* *//**//**//**/

    /**
     * method to validate the email address string
     *
     * @param context   context of the component
     * @param edt_email reference to a EditText to which value to be validate
     * @return true if email address is valid
     */
    /**//**//**/
    public static boolean isValidEmail(Context context, EditText edt_email) {
        boolean validation = true;

        // EditText is for email
        if (edt_email.getText().toString().trim().length() == 0) {
            //edt_email.setError("You cannot leave this field blank.");
            validation = false;

        } else if (!EMAIL_ADDRESS_PATTERN.matcher(edt_email.getText().toString()).matches()) {
            //edt_email.setError("Please enter a valid email address");
            //edt_email.setError("Please enter a valid email address");
            validation = false;

        }

        return validation;
    }

    /**
     * Pass validation boolean.
     *
     * @param context  the context
     * @param password the password
     * @return the boolean
     */
    public static boolean passValidation(Context context, String password) {
        if (password.trim().length() < 6) {
            Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }

        return false;
    }

    /**
     * Pass repass validation boolean.
     *
     * @param context    the context
     * @param password   the password
     * @param rePassword the re password
     * @return the boolean
     */
    public static boolean passRepassValidation(Context context, String password, String rePassword) {
        if (!password.equals(rePassword)) {
            Toast.makeText(context, "Password do not match, please enter valid password.", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

  /*  public static boolean firstNameValidation(Context context, String firstName) {

        Integer maxlength = 15;

        if (!(firstName.trim().length() > 0)) {
            Toast.makeText(context, R.string.Name_cannot_be_empty, Toast.LENGTH_SHORT).show();
        } else if (!specialCharValidation(firstName.trim())) {
            Toast.makeText(context, R.string.first_name_only_special_msg, Toast.LENGTH_SHORT).show();
        } else if (firstName.trim().length() < 3) {
            Toast.makeText(context, R.string.first_name_error_msg, Toast.LENGTH_SHORT).show();
        }
        else if (firstName.trim().length() > 15) {
            Toast.makeText(context, R.string.first_lenghtexceed, Toast.LENGTH_SHORT).show();
        }
        else {
            return true;
        }
        return false;
    }

    public static boolean lastNameValidation(Context context, String firstName) {

        Integer maxlength = 15;

        if (!(firstName.trim().length() > 0)) {
            Toast.makeText(context, R.string.last_cannot_be_empty, Toast.LENGTH_SHORT).show();
        } else if (!specialCharValidation(firstName.trim())) {
            Toast.makeText(context, R.string.last_name_only_special_msg, Toast.LENGTH_SHORT).show();
        } else if (firstName.trim().length() < 3) {
            Toast.makeText(context, R.string.last_name_error_msg, Toast.LENGTH_SHORT).show();
        }
        else if (firstName.trim().length() > 15) {
            Toast.makeText(context, R.string.last_lenghtexceed, Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }*/

    /**
     * Check strings contains only special char boolean.
     *
     * @param inputString the input string
     * @return the boolean
     */
    public static boolean checkStringsContainsOnlySpecialChar(String inputString) {
        boolean found = false;
        String splChrs = "/^[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$/";
        found = inputString.matches("[" + splChrs + "]+");
        return found;
    }

    /**
     * Special char validation boolean.
     *
     * @param inputString the input string
     * @return the boolean
     */
    public static boolean specialCharValidation(String inputString) {
        boolean found = false;
        String splChrs = "[a-zA-Z\\s]+";
        found = inputString.matches("[" + splChrs + "]+");
        return found;
    }

    /**
     * Is numeric boolean.
     *
     * @param str the str
     * @return the boolean
     */
    private static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Locate view rect.
     *
     * @param v the v
     * @return the rect
     */
    public static Rect locateView(View v) {
        int[] loc_int = new int[2];
        if (v == null) return null;
        try {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException npe) {
            //Happens when the view doesn't exist on screen anymore.
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0];
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight();
        return location;
    }

    /**
     * Round off to 1 dec places string.
     *
     * @param val the val
     * @return the string
     */
    public static String roundOffTo1DecPlaces(float val) {
        return String.format("%.1f", val);
    }

    /**
     * Round off to 2 dec places string.
     *
     * @param val the val
     * @return the string
     */
    public static String roundOffTo2DecPlaces(float val) {
        return String.format("%.2f", val);
    }

    /**
     * Is oreo boolean.
     *
     * @return the boolean
     */
    public static Boolean isOreo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * Is running on emulator boolean.
     *
     * @return the boolean
     */
    public static boolean isRunningOnEmulator() {
        boolean result =//
                Build.FINGERPRINT.startsWith("generic")//
                        || Build.FINGERPRINT.startsWith("unknown")//
                        || Build.MODEL.contains("google_sdk")//
                        || Build.MODEL.contains("Emulator")//
                        || Build.MODEL.contains("Android SDK built for x86");
        if (result)
            return true;
        result |= Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic");
        if (result)
            return true;
        result |= "google_sdk".equals(Build.PRODUCT);
        return result;
    }

    /**
     * Show toast.
     *
     * @param activity the activity
     * @param message  the message
     */
    public static void showToast(Activity activity, String message) {
       /* LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) activity.findViewById(R.id.toast_layout_root));

        TextView text = layout.findViewById(R.id.text);
        text.setText(message);

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();*/
    }


    /**
     * Convert utc to local time 2 string.
     *
     * @param dateStr the date str
     * @return the string
     */
    public static String convertUTCToLocalTime2(String dateStr) {
        //2016-12-19 16:48:17
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = df.parse(dateStr);
            df.setTimeZone(TimeZone.getDefault());
            String formattedDate = df.format(date);
            return formattedDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets long from string date.
     *
     * @param dateString the date string
     * @param formate    the formate
     * @return the long from string date
     */
    public static long getLongFromStringDate(String dateString, String formate) {
        long dateLong = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formate);
            Date date = sdf.parse(dateString);

            dateLong = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateLong;
    }

    /**
     * Gets date from long date.
     *
     * @param TimeinMilliSeccond the timein milli seccond
     * @param formate            the formate
     * @return the date from long date
     */
    public static String getDateFromLongDate(long TimeinMilliSeccond, String formate) {
        String dateString = "";
        try {
            dateString = new SimpleDateFormat(formate, Locale.getDefault()).format(new Date(TimeinMilliSeccond));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }


    /**
     * Gets bitmap.
     *
     * @param path    the path
     * @param context the context
     * @return the bitmap
     */
    public static Bitmap getBitmap(String path, Context context) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            in = context.getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.d("", "scale = " + scale + ", orig-width: " + o.outWidth + ", orig-height: " + o.outHeight);

            Bitmap b = null;
            in = context.getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                Log.d("", "1th scale operation dimenions - width: " + width + ", height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            Log.d("", "bitmap size - width: " + b.getWidth() + ", height: " +
                    b.getHeight());
            return b;
        } catch (IOException e) {
            Log.e("", e.getMessage(), e);
            return null;
        }
    }

}


