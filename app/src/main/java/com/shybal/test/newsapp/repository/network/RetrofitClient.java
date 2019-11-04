package com.shybal.test.newsapp.repository.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * a class for generating a single instance of the retrofit client used for making API call in the Application
 */
class RetrofitClient {
    private static Retrofit retrofit = null;

    /**
     * generates the single instance of the rerofit client used throughout the application
     * @param baseUrl for the Http request
     * @return Retofit client instance
     */
    static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}