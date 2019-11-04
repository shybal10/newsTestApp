package com.shybal.test.newsapp.repository.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.shybal.test.newsapp.model.NewsDetail;
import com.shybal.test.newsapp.repository.database.dao.NewsDao;

/**
 * an abstract class that provides direct access to the room database implementation
 */
@Database(entities = {NewsDetail.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract NewsDao newsDao();

    /**
     * method creates a single instance of the room database that is used to perform database operations
     * @param context a {@link Context} object
     * @return the {@link AppDatabase} object that is used to perform database operations
     */
    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"news_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
