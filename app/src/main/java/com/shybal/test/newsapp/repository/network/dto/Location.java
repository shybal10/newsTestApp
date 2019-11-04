package com.shybal.test.newsapp.repository.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * auto generated DTO
 */
public class Location {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sentiment")
    @Expose
    private String sentiment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }
}
