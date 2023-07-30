package com.example.apimoviesapp.ViewModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apimoviesapp.Api.ApiClient;
import com.example.apimoviesapp.Api.Interface.MovieApi;
import com.example.apimoviesapp.Model.Movie;
import com.example.apimoviesapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieList;
    private String currentQuery = "";

    Context currentContext;

    public LiveData<List<Movie>> getMovies(Context context, String newQuery) {
        if (this.currentQuery != newQuery) {
            if (movieList == null) {
                currentContext = context;
                movieList = new MutableLiveData<>();
            }
            fetchMovies(newQuery);
        }
        return movieList;
    }

    private void fetchMovies(String query) {
        String apiKey = currentContext.getString(R.string.api_key);
        MovieApi movieApi = ApiClient.getRetrofit().create(MovieApi.class);
        Call<ResponseBody> call = movieApi.getMovies(apiKey, query, "en-US");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (!response.isSuccessful()) {
                        Log.e("apiError", response.message());
                        showError();
                        return;
                    }
                    String responseBody = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseBody);
                    JSONArray results = jsonObject.getJSONArray("results");
                    loadResultsInMovieList(results);

                } catch (Exception e) {
                    showError();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showError();
                Log.e("api", "Error");
            }
        });
    }
    
    private void loadResultsInMovieList(JSONArray results){
        List<Movie> movies = new ArrayList<>();
        for (int i=0;i<results.length();i++) {
            try {
                JSONObject object = results.getJSONObject(i);
                String title = object.getString("title");
                String poster_path = object.getString("poster_path");
                String release_date = object.getString("release_date");
                String overview = object.getString("overview");
                String backdrop_path = object.getString("backdrop_path");
                movies.add(new Movie(title, poster_path, release_date, overview, backdrop_path));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        movieList.setValue(movies);
    }

    private void showError() {
        Toast.makeText(currentContext, "Error", Toast.LENGTH_LONG).show();
    }
}
