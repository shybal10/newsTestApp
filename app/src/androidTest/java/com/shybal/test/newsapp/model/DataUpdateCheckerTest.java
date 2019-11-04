package com.shybal.test.newsapp.model;

import android.support.test.InstrumentationRegistry;

import com.shybal.test.newsapp.utils.SharedPrefsUtils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataUpdateCheckerTest  {

    @Test
    public void isDayUpdated_ReturnFalseForSameDayTest()  {
        DataUpdateChecker dataUpdateChecker = new DataUpdateChecker(InstrumentationRegistry.getContext());
        long lastResetTimeInput = dataUpdateChecker.getCurrentTimeStamp();
        SharedPrefsUtils.setLongPreference(InstrumentationRegistry.getContext(),"RESET_TIME",lastResetTimeInput);
        assertFalse(dataUpdateChecker.isDayUpdated());
    }

    @Test
    public void isDayUpdated_ReturnTrueForNextDayCheck() {
        DataUpdateChecker dataUpdateChecker = new DataUpdateChecker(InstrumentationRegistry.getContext());
        long lastResetTimeInput = 1572535459429L; //update the time stamp to yesterday
        SharedPrefsUtils.setLongPreference(InstrumentationRegistry.getContext(),"RESET_TIME",lastResetTimeInput);
        assertTrue(dataUpdateChecker.isDayUpdated());
    }

    @Test
    public void getFormattedDate_SimpleTest() {
        String inputDate = "2019-11-03T03:15:00.000+02:00";
        DataUpdateChecker dataUpdateChecker = new DataUpdateChecker(InstrumentationRegistry.getContext());
        String outputDate = dataUpdateChecker.getFormattedDate(inputDate);
        String expected="03/11/2019 05:15 AM";
        assertEquals(expected,outputDate);
    }

    @Test
    public void getFormattedDate_NoHoursAdded() {
        String inputDate = "2019-11-03T03:15:00.000";
        DataUpdateChecker dataUpdateChecker = new DataUpdateChecker(InstrumentationRegistry.getContext());
        String outputDate = dataUpdateChecker.getFormattedDate(inputDate);
        String expected = "03/11/2019 05:15 AM";
        assertEquals(expected,outputDate);
    }

}