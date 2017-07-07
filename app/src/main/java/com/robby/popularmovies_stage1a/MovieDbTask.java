package com.robby.popularmovies_stage1a;

import android.content.Context;
import android.os.AsyncTask;

import com.robby.popularmovies_stage1a.entity.Movie;
import com.robby.popularmovies_stage1a.utilities.MovieDbTaskCompleted;
import com.robby.popularmovies_stage1a.utilities.MyUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robby on 7/7/2017.
 * @author Robby Tan
 */

public class MovieDbTask extends AsyncTask<String, Void, ArrayList<Movie>> {

    private MovieDbTaskCompleted movieDbTaskCompleted;
    private Context context;

    public MovieDbTask(Context context, MovieDbTaskCompleted movieDbTaskCompleted) {
        this.context = context;
        this.movieDbTaskCompleted = movieDbTaskCompleted;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {
            InputStream inputStream;
            HttpURLConnection connection = null;
            ArrayList<Movie> movies = null;
            try {
                URL url = MyUtilities.buildUrl(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    movies = new ArrayList<>();
                    inputStream = new BufferedInputStream(connection.getInputStream());
                    String moviesInString = MyUtilities.convertStreamToString(inputStream);
                    movies.addAll(this.convertStreamToData(moviesInString));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return movies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        movieDbTaskCompleted.onTaskCompleted(movies);
        ((MainActivity)context).showData();
    }

    private List<Movie> convertStreamToData(String moviesInString) {
        List<Movie> movies = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(moviesInString);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Movie movie = new Movie();
                movie.setId(object.getInt(Movie.TAG_ID));
                movie.setTitle(object.getString(Movie.TAG_TITLE));
                movie.setPosterPath(object.getString(Movie.TAG_POSTER_PATH));
                movie.setOverview(object.getString(Movie.TAG_OVERVIEW));
                movie.setVoteAverage(object.getDouble(Movie.TAG_VOTE_AVERAGE));
                movie.setReleaseDate(object.getString(Movie.TAG_RELEASE_DATE));
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
