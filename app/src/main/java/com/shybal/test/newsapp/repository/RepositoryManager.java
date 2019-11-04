package com.shybal.test.newsapp.repository;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import com.shybal.test.newsapp.model.DataUpdateChecker;
import com.shybal.test.newsapp.model.NewsDetail;
import com.shybal.test.newsapp.repository.database.AppDatabase;
import com.shybal.test.newsapp.repository.database.dao.NewsDao;
import com.shybal.test.newsapp.repository.network.APIService;
import com.shybal.test.newsapp.repository.network.ApiUtils;
import com.shybal.test.newsapp.repository.network.dto.NewsResultsResponse;
import com.shybal.test.newsapp.utils.SharedPrefsUtils;
import com.shybal.test.newsapp.utils.UpdateProgressBar;

import java.util.LinkedList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * handles the fetching and population of data to the model
 */
public class RepositoryManager {
    private NewsDao newsDao;
    private static final String TAG = "RepositoryManager";
    private MutableLiveData<LinkedList<NewsDetail>> newsList = new MutableLiveData<>();
    private Application application;
    private DataUpdateChecker dataUpdateChecker;
    private UpdateProgressBar updateProgressBar;


    public RepositoryManager(Application application,DataUpdateChecker dataUpdateChecker,UpdateProgressBar updateProgressBar) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        this.application = application;
        newsDao = appDatabase.newsDao();
        this.dataUpdateChecker = dataUpdateChecker;
        this.updateProgressBar = updateProgressBar;
        getAllNewsFromDatabase();
    }

    /**
     * makes an http request using retrofit to fetch all the latest news within 24 hours
     * pertaing to the keyword
     * @param keyword the string input searched by the user
     */
    public void fetchApiData(final String keyword) {
        final long DAY_IN_MILLI_SECONDS = 86400000;
        final String token = "42e12e91-8641-4e59-aece-8b051e64acca";
        final String format = "json";
        String timeStamp = String.valueOf(dataUpdateChecker.getCurrentTimeStamp() - DAY_IN_MILLI_SECONDS);
        final String sort = "published";
        String query = "performance_score:>0 (title:" + keyword + ")";
        updateProgressBar.setProgressBarVisibility(true);
        APIService service = ApiUtils.getAPIService();
        service.getLatestNews(token,format,timeStamp,sort,query).enqueue(new Callback<NewsResultsResponse>(){
            @Override
            public void onResponse(@NonNull Call<NewsResultsResponse> call,@NonNull Response<NewsResultsResponse> response) {
                Log.e(TAG,response.toString());
                updateProgressBar.setProgressBarVisibility(false);
                storeKeyWord(keyword);
                dataUpdateChecker.updateSearchDate();
                LinkedList<NewsDetail> newsDetails;
                if (response.body() != null) {
                    newsDetails = populateList(response.body());
                    updateUi(response.body(),newsDetails);
                    updateDatabase(newsDetails);
                }else {
                    newsList.postValue(null);
                }

            }
            @Override
            public void onFailure(@NonNull Call<NewsResultsResponse> call,@NonNull Throwable t) {
                updateProgressBar.setProgressBarVisibility(false);
                Log.e(TAG,t.toString());
                newsList.postValue(null);
            }
        });
    }

    /**
     * updates the Application UI with the latest results from the service call
     * @param response object received from the service call
     * @param newsDetails list of news items used to populate the UI
     */
    private void updateUi(NewsResultsResponse response, LinkedList<NewsDetail> newsDetails) {
        if (response.getPosts().size() == 0) {
            newsList.postValue(null);
        }else {
            newsList.postValue(newsDetails);
        }
    }

    /**
     * stores all the recently received service list in the room persistence storage
     * @param newsDetails list of items that are to be stored
     */
    private void updateDatabase(LinkedList<NewsDetail> newsDetails) {
        deletedAllNewsFromDatabase();
        addNewsAllToDatabase(newsDetails);
    }

    /**
     * fetches the keyword used for the last API request
     * @return the keyword of type string
     */
    public String getSearchKeyword() {
        return SharedPrefsUtils.getStringPreference(application.getApplicationContext(), "KEY");
    }

    /**
     * stores the most recent keyword used for the service call in the local storage
     * @param keyWord the keyword of type string
     */
    private void storeKeyWord(String keyWord) {
        SharedPrefsUtils.setStringPreference(application.getApplicationContext(),"KEY",keyWord);
    }

    /**
     * fetches any data updates received either from the database or the API to the view model
     * @return a {@link MutableLiveData} object
     */
    public MutableLiveData<LinkedList<NewsDetail>> getLiveResponses(){
        return newsList;
    }


    /**
     * fetches items recieved from the service response and populates the model
     * @param resultsResponse is the response from the service
     * @return the list of items added to the model
     */
    private LinkedList<NewsDetail> populateList(NewsResultsResponse resultsResponse) {
        LinkedList<NewsDetail> newsDetails = new LinkedList<>();
        for (int i=0; i<resultsResponse.getPosts().size(); i++) {
            newsDetails.add(new NewsDetail( i+1,
                                            resultsResponse.getPosts().get(i).getTitle(),
                                            resultsResponse.getPosts().get(i).getThread().getMainImage(),
                                            resultsResponse.getPosts().get(i).getText(),
                                            dataUpdateChecker.getFormattedDate(resultsResponse.getPosts().get(i).getPublished())));
        }
        return newsDetails;
    }

    /**
     * triggers the async task perform all the insert operations in the background thread
     * @param newsDetails list of items in the model
     */
    private void addNewsAllToDatabase(LinkedList<NewsDetail> newsDetails) {
        new InsertNewsAsyncTask(newsDao).execute(newsDetails);
    }

    /**
     * triggers the async task perform the delete operation for all items in the database
     */
    private void deletedAllNewsFromDatabase() {
        new DeleteNewsAsyncTask(newsDao).execute();
    }

    /**
     * triggers the async task that fetches the most recently stored items in the database
     */
    private void getAllNewsFromDatabase() {
        new GetAllNewsAsyncTask(newsDao).execute(newsList);
    }

    /**
     * Async task to fetch the most recent stored items in the database
     */
    private static class GetAllNewsAsyncTask extends AsyncTask<MutableLiveData<LinkedList<NewsDetail>>,Void,Void> {
        private NewsDao newsDao;
        private GetAllNewsAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(MutableLiveData<LinkedList<NewsDetail>>... mutableLiveData) {
            LinkedList<NewsDetail> newsList = new LinkedList<>(newsDao.getAllNews());
            mutableLiveData[0].postValue(newsList);
            return null;
        }
    }

    /**
     * Async task to insert list of items to the database
     */
    private static class InsertNewsAsyncTask extends AsyncTask<LinkedList<NewsDetail>,Void,Void> {
        private NewsDao newsDao;
        private InsertNewsAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(LinkedList<NewsDetail>... newsItems) {
            for (int i=0; i<newsItems[0].size(); i++)
            newsDao.addNews(newsItems[0].get(i));
            return null;
        }
    }

    /**
     * Async task to delete all items from the database
     */
    private static class DeleteNewsAsyncTask extends AsyncTask<Void,Void,Void> {
        private NewsDao newsDao;
        private DeleteNewsAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            newsDao.deleteAllNews();
            return null;
        }
    }
}
