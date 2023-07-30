package com.example.apimoviesapp.View.Adapter;

import static com.example.apimoviesapp.View.MainActivity.URL_IMAGE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apimoviesapp.Model.Movie;
import com.example.apimoviesapp.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList;
    private Context context;
    final MovieAdapter.onItemClickListener listener;

    public MovieAdapter(Context context, List<Movie> movieList, MovieAdapter.onItemClickListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_element, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.bindData(movie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setData(List<Movie> movies) {
        this.movieList = movies;
        notifyDataSetChanged();
    }

    public interface onItemClickListener{
        void onItemClick(Movie item);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvMovieTittle;
        private final ImageView ivMoviePoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTittle = itemView.findViewById(R.id.tvMovieTittle);
            ivMoviePoster = itemView.findViewById(R.id.ivMoviePoster);
        }

        void bindData(final Movie item){
            itemView.setOnClickListener(v -> {listener.onItemClick(item);});
            tvMovieTittle.setText(item.getTitle());
            String urlImage = URL_IMAGE + item.getImageUrl();
            Glide.with(context)
                    .load(urlImage)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(ivMoviePoster);
        }
    }
}