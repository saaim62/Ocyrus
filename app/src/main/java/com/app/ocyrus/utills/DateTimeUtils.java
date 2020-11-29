package com.app.ocyrus.utills;

import android.text.TextUtils;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * The type Date time utils.
 */
public class DateTimeUtils {

    /**
     * The constant DATE_FORMAT_USER.
     */
    public static final String DATE_FORMAT_USER = "dd MMMM, yyyy";
    /**
     * The constant DATE_FORMAT_USER_2.
     */
    public static final String DATE_FORMAT_USER_2 = "EEE dd MMMM, yyyy";
    /**
     * The constant DATE_FORMAT_USER_3.
     */
    public static final String DATE_FORMAT_USER_3 = "dd MMM, yyyy";
    /**
     * The constant DATE_FORMAT_SERVER.
     */
    public static final String DATE_FORMAT_SERVER = "yyyy-MM-dd";
    /**
     * The constant DATE_FORMAT_SERVER_2.
     */
    public static final String DATE_FORMAT_SERVER_2 = "yyyy-MM-dd HH:mm:ss";
    /**
     * The constant TIME_FORMAT_USER.
     */
    public static final String TIME_FORMAT_USER = "hh:mm aa";
    /**
     * The constant TIME_STAMP.
     */
    public static final String TIME_STAMP = "dd MMM yyyy HH:mm a";
    /**
     * The constant DATE_TIME_FORMAT_USER.
     */
    public static final String DATE_TIME_FORMAT_USER = "dd MMM yyyy hh:mm a";

    /**
     * Gets time 12 hour string.
     *
     * @param hourOfDay the hour of day
     * @param minute    the minute
     * @return the time 12 hour string
     */
    public static String getTime12HourString(int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        return getSimpleDateFormat(TIME_FORMAT_USER).format(c.getTime());
    }

    /**
     * Gets time from string for user.
     *
     * @param timeString the time string
     * @return the time from string for user
     */
    public static Date getTimeFromStringForUser(String timeString) {
        if (TextUtils.isEmpty(timeString)) return null;
        return getDateFromString(timeString, TIME_FORMAT_USER);
    }

    /**
     * Gets date from string for user.
     *
     * @param dateString the date string
     * @return the date from string for user
     */
    public static Date getDateFromStringForUser(String dateString) {
        if (TextUtils.isEmpty(dateString)) return null;
        return getDateFromString(dateString, DATE_FORMAT_USER);
    }


    /**
     * Gets date from string for user.
     *
     * @param dateString the date string
     * @return the date from string for user
     */
    public static Date getDateFromStringForUser(String dateString,String formate) {
        if (TextUtils.isEmpty(dateString)) return null;
        return getDateFromString(dateString, formate);
    }

    /**
     * Gets date from string for sever.
     *
     * @param dateString the date string
     * @return the date from string for sever
     */
    public static Date getDateFromStringForSever(String dateString) {
        if (TextUtils.isEmpty(dateString)) return null;
        return getDateFromString(dateString, DATE_FORMAT_SERVER);
    }

    /**
     * Gets date string from date for sever.
     *
     * @param date the date
     * @return the date string from date for sever
     */
    public static String getDateStringFromDateForSever(Date date) {
        if (date == null) return null;
        return getDateStringFromDate(date, DATE_FORMAT_SERVER);
    }

    /**
     * Convert date string to sever date string string.
     *
     * @param dateString        the date string
     * @param currentDateFormat the current date format
     * @return the string
     */
    public static String convertDateStringToSeverDateString(String dateString, String currentDateFormat) {
        if (TextUtils.isEmpty(dateString)) return null;
        return getDateStringFromDate(getDateFromString(dateString, currentDateFormat), DATE_FORMAT_SERVER);
    }

    /**
     * Convert date string to another date string string.
     *
     * @param dateString        the date string
     * @param currentDateFormat the current date format
     * @param anotherDateFormat the another date format
     * @return the string
     */
    public static String convertDateStringToAnotherDateString(String dateString, String currentDateFormat,
                                                              String anotherDateFormat) {
        if (TextUtils.isEmpty(dateString)) return null;
        return getDateStringFromDate(getDateFromString(dateString, currentDateFormat), anotherDateFormat);
    }

    /**
     * Gets date from string.
     *
     * @param dateString the date string
     * @param pattern    the pattern
     * @return the date from string
     */
    public static Date getDateFromString(String dateString, String pattern) {
        Date date = null;
        try {
            date = getSimpleDateFormat(pattern).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Gets simple date format.
     *
     * @param pattern the pattern
     * @return the simple date format
     */
    public static SimpleDateFormat getSimpleDateFormat(String pattern) {
        if (TextUtils.isEmpty(pattern)) throw new RuntimeException("pattern is null or empty");
        return new SimpleDateFormat(pattern, Locale.getDefault());
    }

    /**
     * Check date is today boolean.
     *
     * @param dateString the date string
     * @param dateFormat the date format
     * @return the boolean
     */
    public static boolean checkDateIsToday(String dateString, String dateFormat) {
        if (TextUtils.isEmpty(dateString)) return false;
        Date date = DateTimeUtils.getDateFromString(dateString, dateFormat);
        return date != null && DateUtils.isToday(date.getTime());
    }

    /**
     * Check date is today boolean.
     *
     * @param date the date
     * @return the boolean
     */
    public static boolean checkDateIsToday(Date date) {
        return date != null && DateUtils.isToday(date.getTime());
    }

    /**
     * Gets date string from date.
     *
     * @param date    the date
     * @param pattern the pattern
     * @return the date string from date
     */
    public static String getDateStringFromDate(Date date, String pattern) {
        return getSimpleDateFormat(pattern).format(date);
    }

    /**
     * Gets zodiac sign.
     *
     * @param month the month
     * @param day   the day
     * @return the zodiac sign
     */
    public static String getZodiacSign(int month, int day) {
        if ((month == 12 && day >= 22 && day <= 31) || (month == 1 && day >= 1 && day <= 19))
            return "Capricorn";
        else if ((month == 1 && day >= 20 && day <= 31) || (month == 2 && day >= 1 && day <= 17))
            return "Aquarius";
        else if ((month == 2 && day >= 18 && day <= 29) || (month == 3 && day >= 1 && day <= 19))
            return "Pisces";
        else if ((month == 3 && day >= 20 && day <= 31) || (month == 4 && day >= 1 && day <= 19))
            return "Aries";
        else if ((month == 4 && day >= 20 && day <= 30) || (month == 5 && day >= 1 && day <= 20))
            return "Taurus";
        else if ((month == 5 && day >= 21 && day <= 31) || (month == 6 && day >= 1 && day <= 20))
            return "Gemini";
        else if ((month == 6 && day >= 21 && day <= 30) || (month == 7 && day >= 1 && day <= 22))
            return "Cancer";
        else if ((month == 7 && day >= 23 && day <= 31) || (month == 8 && day >= 1 && day <= 22))
            return "Leo";
        else if ((month == 8 && day >= 23 && day <= 31) || (month == 9 && day >= 1 && day <= 22))
            return "Virgo";
        else if ((month == 9 && day >= 23 && day <= 30) || (month == 10 && day >= 1 && day <= 22))
            return "Libra";
        else if ((month == 10 && day >= 23 && day <= 31) || (month == 11 && day >= 1 && day <= 21))
            return "Scorpio";
        else if ((month == 11 && day >= 22 && day <= 30) || (month == 12 && day >= 1 && day <= 21))
            return "Sagittarius";
        else return "Illegal date";
    }


    public static String findDayDifference(String CurrentDate , String FinalDate){
        try {
//            String CurrentDate= "09/24/2018";
//            String FinalDate= "09/26/2019";
            Date date1;
            Date date2;
            SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
            date1 = dates.parse(CurrentDate);
            date2 = dates.parse(FinalDate);
            long difference = Math.abs(date1.getTime() - date2.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);
            String dayDifference = Long.toString(differenceDates);

            return dayDifference;

        } catch (Exception exception) {

        }

        return "";
    }
}