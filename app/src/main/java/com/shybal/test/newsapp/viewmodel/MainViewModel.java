package com.shybal.test.newsapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.shybal.test.newsapp.R;
import com.shybal.test.newsapp.model.DataUpdateChecker;
import com.shybal.test.newsapp.model.NewsDetail;
import com.shybal.test.newsapp.model.SearchData;
import com.shybal.test.newsapp.repository.RepositoryManager;
import com.shybal.test.newsapp.utils.OnSearchCloseSoftKeyboardListener;
import com.shybal.test.newsapp.utils.UpdateProgressBar;
import com.shybal.test.newsapp.view.adapter.NewsResultsRecViewAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * view model for the MainActivity
 */
public class MainViewModel extends BaseViewModel implements OnSearchCloseSoftKeyboardListener , UpdateProgressBar {
    private RepositoryManager repositoryManager;
    private SearchData searchData;
    private MutableLiveData<SearchData> searchButtonClick = new MutableLiveData<>();
    private MutableLiveData<NewsDetail> selected;    //holds the selected list item
    private NewsResultsRecViewAdapter adapter;
    private MutableLiveData<Boolean> dataUpdateAvailable;
    private DataUpdateChecker dataUpdateChecker;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * initialises all the classes of the model, the LiveData components Observed by the MainActivity
     * and the recycler view adapter
     */
    public void initialiseComponents() {
        dataUpdateChecker = new DataUpdateChecker(getApplication().getApplicationContext());
        selected = new MutableLiveData<>();
        dataUpdateAvailable = new MutableLiveData<>();
        dataUpdateAvailable.setValue(false);
        repositoryManager = new RepositoryManager(getApplication(), dataUpdateChecker,this);
        searchData = new SearchData();
        searchData.setSearchKeyWord(repositoryManager.getSearchKeyword());
        adapter = new NewsResultsRecViewAdapter(this, R.layout.news_results_rec_view_item);
        if(dataUpdateChecker.isNetworkAvailable() && dataUpdateChecker.isDayUpdated()) {
            showForceUpdateDialog();
        }
    }

    /**
     * updates the obervable for the force update dialog so that it can be observed in the activity
     */
    private void showForceUpdateDialog() {
        dataUpdateAvailable.setValue(true);
    }

    /**
     * fetches the searchData so it can be reflected in the view
     * @return a {@link SearchData} object
     */
    public SearchData getSearchData() {
        return searchData;
    }

    /**
     * method responsible for handling the search button clicks
     */
    public void onSearchClick() {
        if (dataUpdateChecker.isNetworkAvailable())
            performSearch();
        else
            getDisplayMessage().setValue(getApplication().getApplicationContext().getString(R.string.internet_not_available));
    }

    /**
     * fetches data from the service if the search data is valid
     */
    private void performSearch() {
        if (searchData.isValid()) {
            repositoryManager.fetchApiData(searchData.getSearchKeyWord());
            searchButtonClick.setValue(searchData);
        }else {
            getDisplayMessage().setValue(getApplication().getApplicationContext().getString(R.string.enter_key));
        }
    }

    /**
     * fetches a particular item based on the position from the list
     * @param position of the particular item
     * @return the details item
     */
    public NewsDetail getNewsAt(Integer position) {
        if (repositoryManager.getLiveResponses().getValue() != null &&
                position != null &&
                repositoryManager.getLiveResponses().getValue().size() > position) {
            return repositoryManager.getLiveResponses().getValue().get(position);
        }
        return null;
    }

    /**
     * click listener for each item in the list
     * @param position of the item clicksd
     */
    public void onItemClick(Integer position) {
        NewsDetail newsTipDetail = getNewsAt(position);
        selected.setValue(newsTipDetail); // selected is the live data holding the selected item
    }


    /**
     * Updates the recycler view adapter with the list of items
     * @param news list of items used to populate the adapter
     */
    public void populateInAdapter(List<NewsDetail> news) {
        this.adapter.setNews(news);
        this.adapter.notifyDataSetChanged();
    }

    /**
     * returns the selected list item to be observed in the fragment
     * @return value of the {@link MutableLiveData} object
     */
    public MutableLiveData<NewsDetail> getSelected() {
        return selected;
    }


    /**
     * returns the update available LiveData object to be observed in the fragment
     * @return value of the {@link MutableLiveData} object
     */
    public MutableLiveData<Boolean> getDataUpdateAvailable() {
        return dataUpdateAvailable;
    }

    /**
     * binds the adapter to the recycler view
     * @return a {@link NewsResultsRecViewAdapter} object that is bound to the view
     */
    public NewsResultsRecViewAdapter getAdapter() {
        return adapter;
    }


    /**
     * returns the list of items to be observed from the fragment
     * @return value of the {@link MutableLiveData} object
     */
    public MutableLiveData<LinkedList<NewsDetail>> fetchList() {
        return repositoryManager.getLiveResponses();
    }


    /**
     * methods used for observing changes to the button click LiveData component
     * @return value of the {@link MutableLiveData} object
     */
    public MutableLiveData<SearchData> getSearchButtonClick() {
        return searchButtonClick;
    }

    /**
     * handles the user clicks on the soft keyboard
     */
    @Override
    public void onSearchCloseSoftKeyboard() {
        onSearchClick();
    }

    /**
     * sets the progress visibity based on the onterface callback from the repository manager
     * @param visibility true if the progress bar should be made visible
     */
    @Override
    public void setProgressBarVisibility(boolean visibility) {
        getProgressVisible().set(visibility);
    }
}
