package com.shybal.test.newsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * A pack of helpful getter and setter methods for reading/writing to {@link SharedPreferences}.
 */
final public class SharedPrefsUtils {
    private SharedPrefsUtils() {}

    /**
     * Helper method to retrieve a String value from {@link SharedPreferences}.
     *
     * @param context a {@link Context} object.
     * @param key  unique  for the stored data
     * @return The value from shared preferences, or null if the value could not be read.
     */
    public static String getStringPreference(Context context, String key) {
        String value = "";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null)
        {
            value = preferences.getString(key, "");
        }
        return value;
    }

    /**
     * Helper method to write a String value to {@link SharedPreferences}.
     *
     * @param context a {@link Context} object.
     * @param key unique identifier for the data to be stored
     * @param value the data to be stored
     */
    public static void setStringPreference(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            editor.apply();
        }
    }



    /**
     * Helper method to retrieve a long value from {@link SharedPreferences}.
     *
     * @param context a {@link Context} object.
     * @param key unique identifier for the value to be stored
     * @param defaultValue A default to return if the value could not be read.
     * @return The value from shared preferences, or the provided default.
     */
    public static long getLongPreference(Context context, String key, long defaultValue) {
        long value = defaultValue;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getLong(key, defaultValue);
        }
        return value;
    }

    /**
     * Helper method to write a long value to {@link SharedPreferences}.
     *
     * @param context a {@link Context} object.
     * @param key unique identifier for the value to be stored
     * @param value the data to be stored
     */
    public static void setLongPreference(Context context, String key, long value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong(key, value);
            editor.apply();
        }
    }

}