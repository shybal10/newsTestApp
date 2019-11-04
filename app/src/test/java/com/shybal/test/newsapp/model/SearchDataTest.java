package com.shybal.test.newsapp.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class SearchDataTest {

    @Test
    public void isValid_Invalid_Test_For_Empty_Key() {
        String inputKey = "";
        SearchData searchData = new SearchData();
        searchData.setSearchKeyWord(inputKey);
        assertFalse(searchData.isValid());
    }

    @Test
    public void isValid_Invalid_Test_For_Null_Key() {
        String inputKey = null;
        SearchData searchData = new SearchData();
        searchData.setSearchKeyWord(inputKey);
        assertFalse(searchData.isValid());
    }

    @Test
    public void isValid_Invalid_Test_Sample() {
        String inputKey = "shybal";
        SearchData searchData = new SearchData();
        searchData.setSearchKeyWord(inputKey);
        assertTrue(searchData.isValid());
    }
}