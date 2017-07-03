package com.robby.popularmovies_stage1a.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Robby on 7/2/2017.
 * @author Robby Tan
 */

public class MyUtilities implements Serializable {

    public static final String API_KEY = com.robby.popularmovies_stage1a.BuildConfig.TMDB_KEY;

    public static final String URL_TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated";
    public static final String URL_POPULAR = "https://api.themoviedb.org/3/movie/popular";
    public static final String URL_NOW_PLAYING = "https://api.themoviedb.org/3/movie/now_playing";
    public static final String URL_UPCOMING = "https://api.themoviedb.org/3/movie/upcoming";

    public static final String PARAM_API = "api_key";

    public static final int NUMBER_OF_COLUMN_PORTRAIT = 2;

    public static URL buildUrl(String typeRequest) {
        Uri uri = Uri.parse(typeRequest).buildUpon()
                .appendQueryParameter(PARAM_API, API_KEY)
                .build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
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

    public static String convertStreamToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder("");
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        inputStream.close();
        return builder.toString();
    }

    public static boolean hasInternetConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
