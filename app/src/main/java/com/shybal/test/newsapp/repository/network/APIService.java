package com.shybal.test.newsapp.repository.network;
import com.shybal.test.newsapp.repository.network.dto.NewsResultsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * interface to perform HTTP requests with annotation
 */
public interface APIService {
    /**
     * performs and http network request on the API
     * @param token a unique id required to access the API
     * @param format specifies the required format for the response
     * @param timeStamp sends the current time to the API
     * @param sort specifies the tye of sorting for the response
     * @param query contains filters that define the API response.
     * @return the request and response pair from the webservice
     */
    @GET("/filterWebContent")
    Call<NewsResultsResponse> getLatestNews(@Query("token") String token,
                                        @Query("format") String format,
                                        @Query("ts") String timeStamp,
                                        @Query("sort") String sort,
                                        @Query("q") String query);
}