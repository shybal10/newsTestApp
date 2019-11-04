package com.shybal.test.newsapp.repository.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.shybal.test.newsapp.model.NewsDetail;

import java.util.List;

/**
 * data access object where all the database interactions are soecified
 */
@Dao
public interface NewsDao {

    /**
     * inserts a single news to the database
     * @param newsDetail a {@link NewsDetail} object that is to be inserted
     */
    @Insert
    void addNews(NewsDetail newsDetail);

    /**
     * deletes all the items in the database
     */
    @Query("DELETE FROM news")
    void deleteAllNews();

    /**
     * returns the list of all the news in the database
     * @return list of {@link NewsDetail} objects stored in the the database
     */
    @Query("SELECT * from news ORDER BY id ASC")
    List<NewsDetail> getAllNews();
}
