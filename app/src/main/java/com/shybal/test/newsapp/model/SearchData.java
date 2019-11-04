package com.shybal.test.newsapp.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * observable model for the search keyword
 */
public class SearchData extends BaseObservable {
    private String searchKeyWord;

    /**
     * returns the searchKeyword
     * @return a value of type string
     */
    @Bindable
    public String getSearchKeyWord() {
        return searchKeyWord;
    }

    /**
     * sets the keyword entered by the user
     * @param searchKeyWord is a value of type string
     */
    public void setSearchKeyWord(String searchKeyWord) {
        this.searchKeyWord = searchKeyWord;
    }

    /**
     * validates the search keyword searched by the user
     * @return true if the keywords searched is a not empty or null
     */
    public boolean isValid() {
        return searchKeyWord != null && !searchKeyWord.equalsIgnoreCase("");
    }
}
