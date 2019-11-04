package com.shybal.test.newsapp.repository.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * auto generated DTO
 */
public class NewsResultsResponse {

    @SerializedName("posts")
    @Expose
    private List<Post> posts = null;
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;
    @SerializedName("moreResultsAvailable")
    @Expose
    private Integer moreResultsAvailable;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("requestsLeft")
    @Expose
    private Integer requestsLeft;
    @SerializedName("warnings")
    @Expose
    private Object warnings;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getMoreResultsAvailable() {
        return moreResultsAvailable;
    }

    public void setMoreResultsAvailable(Integer moreResultsAvailable) {
        this.moreResultsAvailable = moreResultsAvailable;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Integer getRequestsLeft() {
        return requestsLeft;
    }

    public void setRequestsLeft(Integer requestsLeft) {
        this.requestsLeft = requestsLeft;
    }

    public Object getWarnings() {
        return warnings;
    }

    public void setWarnings(Object warnings) {
        this.warnings = warnings;
    }
}
