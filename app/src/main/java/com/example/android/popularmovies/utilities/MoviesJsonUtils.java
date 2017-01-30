package com.example.android.popularmovies.utilities;

import android.util.Log;

import com.example.android.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mrfksiv on 24/01/2017.
 */

public class MoviesJsonUtils {

    public static JSONObject parseJSON(String jsonString) {

        JSONObject obj = null;
        try {
            obj = new JSONObject(jsonString);
            return obj;

        } catch(Throwable t){
            Log.e("My App", "Could not parse malformed JSON: \"" + jsonString + "\"");
        }
        return obj;


//        JSONObject  obj = MoviesJsonUtils.parseJSON(jsonMoviesReponse);
//        Log.println(Log.DEBUG,"MORFKLOG-RESULTS", jArray.length()+"");
    }

    public static ArrayList<Movie> createMoviesList(String jsonString) {
        JSONObject obj = null;
        ArrayList<Movie> popularMovies = new ArrayList<Movie>();

        obj = parseJSON(jsonString);

        try {
            JSONArray jArray = obj.getJSONArray("results");

            for (int i=0; i< jArray.length(); i++) {

                JSONObject tmp = (JSONObject) jArray.get(i);

                Movie movie = new Movie(tmp.getString("original_title"), tmp.getString("overview"),
                         tmp.getString("poster_path"), tmp.getLong("vote_count"),
                         tmp.getLong("vote_average"), tmp.getString("release_date"));

                popularMovies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return popularMovies;

    }
}
