package com.shybal.test.newsapp.repository.network;

public class ApiUtils {
    /**
     * class to define the base URL for the http request and return the retrofit client
     */
    private ApiUtils() {}
    private static final String BASE_URL = "http://webhose.io";

    /**
     * method to return the generated retrofit client for the API call
     * @return a ({@link APIService}) object for the http request
     */
    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}