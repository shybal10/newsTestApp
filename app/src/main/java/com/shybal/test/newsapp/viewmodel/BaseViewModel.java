package com.shybal.test.newsapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

/**
 * the base view model for all the viewmodels of the application
 */
public  class BaseViewModel extends AndroidViewModel {
    private MutableLiveData<String> displayMessage = new MutableLiveData<>();
    private ObservableBoolean progressVisible = new ObservableBoolean();
    BaseViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * used to set all toast messages in the application
     * @return the {@link MutableLiveData<>} that can be set the message to be showed
     */
    public MutableLiveData<String> getDisplayMessage() { return displayMessage; }

    /**
     * returns the observable for the progressbar
     * @return the {@link ObservableBoolean} that can be set to true to show progress bar and false to hide it
     */
    public ObservableBoolean getProgressVisible() { return progressVisible; }

}
