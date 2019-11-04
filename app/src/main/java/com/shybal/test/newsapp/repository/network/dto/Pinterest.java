package com.shybal.test.newsapp.repository.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * auto generated DTO
 */
public class Pinterest {
    @SerializedName("shares")
    @Expose
    private Integer shares;

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }
}
