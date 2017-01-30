package com.example.android.popularmovies;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView mMovieTitleText;
    private TextView mMovieSynopsisText;
    private TextView mMovieReleaseDateText;
    private TextView mMovieRatingDateText;

    private ImageView mMoviePosterLarge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mMovieSynopsisText = (TextView) findViewById(R.id.tv_movie_synopsis);
        mMovieTitleText = (TextView) findViewById(R.id.tv_movie_title);
        mMovieReleaseDateText = (TextView) findViewById(R.id.tv_release_date);
        mMovieRatingDateText = (TextView) findViewById(R.id.tv_rating);

        mMoviePosterLarge = (ImageView) findViewById(R.id.iv_movie_poster_large);

        Intent intentThatStartedThisActivity = getIntent();
        Bundle extras = intentThatStartedThisActivity.getExtras();

        String title = extras.getString("TITLE");
        String synopsis = extras.getString("SYNOPSIS");
        String releaseDate = extras.getString("RELEASE_DATE");
        String posterPath = extras.getString("LARGE_POSTER_PATH");
        String rating = extras.getString("RATING");

        mMovieTitleText.setText(title);
        mMovieSynopsisText.setText(synopsis);
        mMovieReleaseDateText.setText(releaseDate);
        mMovieRatingDateText.setText(rating);

        Picasso.with(MovieDetailsActivity.this).load(posterPath).into(mMoviePosterLarge);

//        if(intentThatStartedThisActivity.hasExtra("synopsis")){
//            String synopsis = intentThatStartedThisActivity.getStringExtra("synopsis");
//            mMovieTitleText.setText(synopsis);
//        }
    }
}
