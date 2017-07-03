package com.robby.popularmovies_stage1a;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.robby.popularmovies_stage1a.entity.Movie;
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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView gdMoviesPoster;
    private MovieAdapter movieAdapter;
    private ProgressBar pbLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gdMoviesPoster = (GridView) findViewById(R.id.gd_movies_poster);
        pbLoader = (ProgressBar) findViewById(R.id.pb_loader_bar);
        gdMoviesPoster.setOnItemClickListener(this);
        movieAdapter = new MovieAdapter(this);
        gdMoviesPoster.setAdapter(movieAdapter);
        if (savedInstanceState == null) {
            new MovieDbTask().execute(MyUtilities.URL_POPULAR);
        } else {
            ArrayList<Movie> movies = savedInstanceState.getParcelableArrayList(getResources().getString(R.string.bundle_parcel_movie));
            movieAdapter.setMovies(movies);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Movie m = (Movie) parent.getItemAtPosition(position);
        Intent newIntent = new Intent(MainActivity.this, MovieDetailActivity.class);
        newIntent.putExtra(getResources().getString(R.string.send_parcel_movie), m);
        startActivity(newIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showLoader();
        switch (item.getItemId()) {
            case R.id.action_popular:
                new MovieDbTask().execute(MyUtilities.URL_POPULAR);
                break;
            case R.id.action_top_rated:
                new MovieDbTask().execute(MyUtilities.URL_TOP_RATED);
                break;
        }
        changeAppTitle(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(getResources().getString(R.string.bundle_parcel_movie), movieAdapter.getMovies());
        super.onSaveInstanceState(outState);
    }

    private void showLoader() {
        pbLoader.setVisibility(View.VISIBLE);
        gdMoviesPoster.setVisibility(View.INVISIBLE);
    }

    private void showData() {
        pbLoader.setVisibility(View.INVISIBLE);
        gdMoviesPoster.setVisibility(View.VISIBLE);
    }

    private void changeAppTitle(int itemId) {
        switch (itemId) {
            case R.id.action_popular:
                setTitle(getResources().getString(R.string.popular_title));
                break;
            case R.id.action_top_rated:
                setTitle(getResources().getString(R.string.top_rated_title));
                break;
        }
    }

    class MovieDbTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            if (MyUtilities.hasInternetConnection(getApplicationContext())) {
                InputStream inputStream;
                HttpURLConnection connection = null;
                ArrayList<Movie> movies = new ArrayList<>();
                try {
                    URL url = MyUtilities.buildUrl(params[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
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
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            if (movies != null) {
                movieAdapter.setMovies(movies);
                showData();
            } else {
                Toast.makeText(MainActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
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
}
