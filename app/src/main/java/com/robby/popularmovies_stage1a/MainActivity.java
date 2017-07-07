package com.robby.popularmovies_stage1a;

import android.content.Intent;
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
import com.robby.popularmovies_stage1a.utilities.MovieDbTaskCompleted;
import com.robby.popularmovies_stage1a.utilities.MyUtilities;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity implements MovieDbTaskCompleted {

    @BindView(R.id.gd_movies_poster)
    GridView gdMoviesPoster;
    @BindView(R.id.pb_loader_bar)
    ProgressBar pbLoader;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        movieAdapter = new MovieAdapter(this);
        gdMoviesPoster.setAdapter(movieAdapter);
        if (savedInstanceState == null) {
            getMoviesFromTmdb(MyUtilities.URL_POPULAR);
        } else if (savedInstanceState != null && savedInstanceState.getParcelableArrayList(getResources().getString(R.string.bundle_parcel_movie)).size() == 0) {
            getMoviesFromTmdb(MyUtilities.URL_POPULAR);
        } else {
            ArrayList<Movie> movies = savedInstanceState.getParcelableArrayList(getResources().getString(R.string.bundle_parcel_movie));
            movieAdapter.setMovies(movies);
            showData();
        }
    }

    @OnItemClick(R.id.gd_movies_poster)
    public void movieClicked(AdapterView<?> parent, int position) {
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
                getMoviesFromTmdb(MyUtilities.URL_POPULAR);
                break;
            case R.id.action_top_rated:
                getMoviesFromTmdb(MyUtilities.URL_TOP_RATED);
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

    @Override
    public void onTaskCompleted(ArrayList<Movie> movies) {
        if (movies == null) {
            Toast.makeText(this, "Error retrieving data!!!", Toast.LENGTH_SHORT).show();
        } else {
            movieAdapter.setMovies(movies);
        }
    }

    private void showLoader() {
        pbLoader.setVisibility(View.VISIBLE);
        gdMoviesPoster.setVisibility(View.INVISIBLE);
    }

    public void showData() {
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

    private void getMoviesFromTmdb(String url) {
        if (MyUtilities.hasInternetConnection(this)) {
            new MovieDbTask(this, this).execute(url);
        } else {
            Toast.makeText(this, "Please check your internet connection!!!", Toast.LENGTH_SHORT).show();
        }
    }
}
