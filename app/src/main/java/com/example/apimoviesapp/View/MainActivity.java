package com.example.apimoviesapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apimoviesapp.View.Adapter.MovieAdapter;
import com.example.apimoviesapp.Model.Movie;
import com.example.apimoviesapp.R;
import com.example.apimoviesapp.ViewModel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String URL_IMAGE = "https://image.tmdb.org/t/p/original";
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private MovieViewModel movieViewModel;
    private List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        initUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovies(query);
                searchView.onActionViewCollapsed();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        return true;
    }

    private void initComponents(){
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void initUI(){
        movieAdapter = new MovieAdapter(this, movieList, this::showDialog);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(movieAdapter);
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
    }

    private void searchMovies(String query){
        movieViewModel.getMovies(getApplicationContext(),query).observe(this, movies -> {
            movieList = movies;
            movieAdapter.setData(movies);
        });
    }

    /**
     * this method open a view where show the movie information
     * @param item is the movie object data
     * **/
    private void showDialog(Movie item){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.item_movie_dialog);
        String title = item.getTitle();
        String releaseDate = item.getReleaseDate();
        String overview = item.getOverview();
        String urlBackdrop = URL_IMAGE + item.getBackdrop();
        // init Components of Dialog
        TextView tvDialogTittle = dialog.findViewById(R.id.tvDialogTittle);
        TextView tvDialogReleaseDate = dialog.findViewById(R.id.tvDialogReleaseDate);
        TextView tvDialogOverview = dialog.findViewById(R.id.tvDialogOverview);
        ImageView ivDialogBackdrop = dialog.findViewById(R.id.ivDialogBackdrop);
        // init UI of Dialog
        tvDialogTittle.setText(title);
        tvDialogReleaseDate.setText(releaseDate);
        tvDialogOverview.setText(overview);
        Glide.with(getApplicationContext())
                .load(urlBackdrop)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(ivDialogBackdrop);
        dialog.show();
    }

}