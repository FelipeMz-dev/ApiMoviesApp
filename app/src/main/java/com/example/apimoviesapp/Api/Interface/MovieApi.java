package com.example.apimoviesapp.Api.Interface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {
    @GET("movie")
    Call<ResponseBody> getMovies(@Query("api_key") String apiKey,
                               @Query("query") String query,
                               @Query("language") String language);
}
