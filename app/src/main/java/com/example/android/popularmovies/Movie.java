package com.example.android.popularmovies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mrfksiv on 23/01/2017.
 */

public class Movie {

    String title;
    String overview;
    String releaseDate;
    String posterSmallPath;
    String posterLargePath;
    float popularity;
    float rating;

    public Movie( String title, String overview, String posterPath, float popularity, float rating,
                  String releaseDate) {

        this.title = title;
        this.overview = overview;
        this.posterSmallPath = "http://image.tmdb.org/t/p/w185/" + posterPath;
        this.posterLargePath = "http://image.tmdb.org/t/p/w780/" + posterPath;
        this.popularity = popularity;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }
}
