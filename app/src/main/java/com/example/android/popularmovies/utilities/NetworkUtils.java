package com.example.android.popularmovies.utilities;

/**
 * Created by mrfksiv on 22/01/2017.
 */
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.popularmovies.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils extends AppCompatActivity{

    private final static String API_PARAM = "api_key";

//    PLEASE ENTER YOUR OWN API_KEY HERE:
    private final static String API_KEY = "[API-KEY-KEY-HERE]";

    public NetworkUtils() {

    };

    public static URL buildUrl(String basePath) {


        Uri builtUri = Uri.parse(basePath).buildUpon()
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        Log.v(NetworkUtils.class.getSimpleName(), "Built " + url);

        return url;
    }


    public static String getResponseFromHttpRequest(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
