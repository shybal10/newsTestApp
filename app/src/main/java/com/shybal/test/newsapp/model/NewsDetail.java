package com.shybal.test.newsapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;

import java.io.Serializable;

/**
 * class acting as the model for the database and application data
 */
@Entity(tableName = "news")
public class NewsDetail extends BaseObservable implements Serializable {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "news_title")
    private String title;
    @ColumnInfo(name = "news_image")
    private String image;
    @ColumnInfo(name = "news_description")
    private String newsDescription;
    @ColumnInfo(name = "news_date")
    private String date;

    public NewsDetail(int id,String title, String image, String newsDescription, String date) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.newsDescription = newsDescription;
        this.date = date;
    }

    /**
     * return the date
     * @return  value of type string
     */
    public String getDate() {
        return date;
    }

    /**
     * returns the news title
     * @return value of type string
     */
    public String getTitle() {
        return title;
    }

    /**
     * returns the news' main posted image
     * @return value of type string
     */
    public String getImage() {
        return image;
    }

    /**
     * returns the news' detailed description
     * @return value of type string
     */
    public String getNewsDescription() {
        return newsDescription;
    }

    /**
     * returns the id which acts as a primary key in the local database
     * @return value of type int
     */
    public int getId() {
        return id;
    }

}
