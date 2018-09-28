package com.example.retrofitexample;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by nvtamcntt on 2018/09/28.
 */

public interface StackOverflowAPI {
    @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")
    Call<StackOverflowQuestions> loadQuestions(@Query("tagged") String tags);
}
