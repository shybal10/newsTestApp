package com.shybal.test.newsapp.model;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.shybal.test.newsapp.utils.SharedPrefsUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * class containing helper methods to handle data updates based on the time elapsed and network availability
 */
public class DataUpdateChecker {

    private Context context;
    public DataUpdateChecker(Context context) {
        this.context = context;
    }

    /**
     * method to check if the 24 hours have passed since the last update
     * @return true if 24 hours have passed since the last webservice call
     */
    public boolean isDayUpdated() {
        final long HOUR_IN_MILLI_SECONDS = 86400000;
        long lastResetTime = SharedPrefsUtils.getLongPreference(context,"RESET_TIME",0);
        long currentTime = getCurrentTimeStamp();
        long requiredTimeElapsed = lastResetTime + HOUR_IN_MILLI_SECONDS;
        return lastResetTime != 0 && currentTime > requiredTimeElapsed;
    }

    /**
     * saves the time stamp for the most recent call locally
     */
    public void updateSearchDate() {
        SharedPrefsUtils.setLongPreference(context,"RESET_TIME",getCurrentTimeStamp());
    }

    /**
     * checks if the application is connected to the internet
     * @return true if the network is available and false otherwise
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * generates the latest time stamp from the system
     * @return a long of the value of the current system time stamp
     */
    public long getCurrentTimeStamp() {
        Date date= new Date();
        return date.getTime();
    }

    /**
     * converts the format of the date and time recieved from the webservice into another format
     * @param strDate is the date and time recieved from the service
     * @return the newly generated date and time formate
     */
    public String getFormattedDate(String strDate) {
        String formattedDate = "";
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date d = formatter.parse(strDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.HOUR, 2);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat fmtOut = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            formattedDate = fmtOut.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

}
