package com.example.fajartestmovie;

import java.util.Date;

public class MovieListItem {
    private int id;
    private String movieName;
    private String movieDesc;
    private String releaseDate;
    private String moviePosterPath;
    private String vote_average;
    private String keyVideo;


    public MovieListItem(int id, String movieName, String movieDesc, String releaseDate, String moviePosterPath, String vote_average) {
        this.id = id;
        this.movieName = movieName;
        this.movieDesc = movieDesc;
        this.releaseDate = releaseDate;
        this.moviePosterPath = moviePosterPath;
        this.vote_average = vote_average;
    }

    public String getKeyVideo() {
        return keyVideo;
    }

    public void setKeyVideo(String keyVideo) {
        this.keyVideo = keyVideo;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }

    public String getMovieDesc() {
        return movieDesc;
    }

    public void setMovieDesc(String movieDesc) {
        this.movieDesc = movieDesc;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
