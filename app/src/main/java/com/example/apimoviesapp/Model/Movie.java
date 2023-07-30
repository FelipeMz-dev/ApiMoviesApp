package com.example.apimoviesapp.Model;

public class Movie {
    private final String title;
    private final String imageUrl;
    private final String releaseDate;
    private final String overview;
    private final String backdrop;

    public Movie(String title, String imageUrl, String releaseDate, String overview, String backdrop) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.backdrop = backdrop;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
