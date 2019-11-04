package com.shybal.test.newsapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.shybal.test.newsapp.model.NewsDetail;

/**
 * view model for the DetailsActivity
 */
public class DetailsViewModel extends BaseViewModel {
    private MutableLiveData<NewsDetail> newsDetailObservable = new MutableLiveData<>();
    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * gets the news the user has selected and sets its value to the Observable
     * @param newsDetail has the details which are to be shown
     */
    public void init(NewsDetail newsDetail) {
        newsDetailObservable = getNewsDetails(newsDetail);
    }


    /**
     * retuns the LiveData to be Observed for data
     * @return {@link MutableLiveData} object that is being observed
     */
    public MutableLiveData<NewsDetail> getNewsTipDetailObservable() {
        return newsDetailObservable;
    }

    /**
     * sets the value of the LiveData observable
     * @param newsDetail is the data that is to be set to the observable
     * @return a {@link MutableLiveData} object
     */
    private MutableLiveData<NewsDetail> getNewsDetails(NewsDetail newsDetail) {
        MutableLiveData<NewsDetail> newsDetailObservableField = new MutableLiveData<>();
        newsDetailObservableField.setValue(newsDetail);
        return newsDetailObservableField;
    }
}
