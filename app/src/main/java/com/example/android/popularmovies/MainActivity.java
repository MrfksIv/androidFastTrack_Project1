package com.example.android.popularmovies;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.utilities.MoviesJsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;

    private MovieAdapter movieAdapter;
    private Toast mToast;

    private String top_rated_movies_url;
    private String popular_movies_url;

    GridView gridView;

    ArrayList<Movie> returnedMovies;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if (id == R.id.action_popular_movies){
            loadMovieData(popular_movies_url);
            return true;
        }

        if (id == R.id.action_top_rated_movies){
            loadMovieData(top_rated_movies_url);
            return true;
        }

         return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        top_rated_movies_url = getString(R.string.top_rated_url);
        popular_movies_url = getString(R.string.popular_url);


        gridView = (GridView) findViewById(R.id.movies_grid);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Movie movieClicked = returnedMovies.get(position);

                String title = movieClicked.title;
                String synopsis = movieClicked.overview;
                String releaseDate = movieClicked.releaseDate;
                String posterPath = movieClicked.posterLargePath;
                String rating = movieClicked.rating+"";

                Bundle extras = new Bundle();
                extras.putString("TITLE", title);
                extras.putString("SYNOPSIS", synopsis);
                extras.putString("RELEASE_DATE", releaseDate);
                extras.putString("LARGE_POSTER_PATH", posterPath);
                extras.putString("RATING", rating);

                Context context = MainActivity.this;
                Class destinationActivity = MovieDetailsActivity.class;

                Intent intent = new Intent(context, destinationActivity);

                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        loadMovieData(top_rated_movies_url);

    }

    private void loadMovieData(String url) {
        if (isOnline()){
            mLoadingIndicator.setVisibility(View.VISIBLE);
            mErrorMessageDisplay.setVisibility(View.INVISIBLE);
            new FetchMoviesTask(this).execute(url);
        } else {
            mErrorMessageDisplay.setText("No internet connection. Please connect to the internet and try again");
            mErrorMessageDisplay.setVisibility(View.VISIBLE);
        }
    }


    private void showMovieDataView() {

        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);

        gridView.setVisibility(View.VISIBLE);
    }


    public class FetchMoviesTask extends AsyncTask<String, Void, String> {

        private Activity mContext;

        public FetchMoviesTask(Activity context) {
            mContext = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String movies_url = params[0];
            URL topMoviesRequestUrl = NetworkUtils.buildUrl(movies_url);

            try {
                String jsonMoviesReponse = NetworkUtils.getResponseFromHttpRequest(topMoviesRequestUrl);
                returnedMovies = MoviesJsonUtils.createMoviesList(jsonMoviesReponse);

                return jsonMoviesReponse;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String movieData) {
            movieAdapter = new MovieAdapter(mContext, returnedMovies);
            showMovieDataView();
            gridView.setAdapter(movieAdapter);

        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
