package com.robby.popularmovies_stage1a;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.robby.popularmovies_stage1a.entity.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView tvMovieTitle;
    private TextView tvMovieReleaseDate;
    private TextView tvMovieRating;
    private TextView tvMovieOverview;
    private ImageView ivMoviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        tvMovieOverview = (TextView) findViewById(R.id.tv_movie_overview);
        tvMovieRating = (TextView) findViewById(R.id.tv_movie_rating_detail);
        tvMovieReleaseDate = (TextView) findViewById(R.id.tv_movie_release_date_detail);
        tvMovieTitle = (TextView) findViewById(R.id.tv_movie_title_detail);
        ivMoviePoster = (ImageView) findViewById(R.id.im_movie_poster_detail);

        Intent intent = getIntent();
        if (intent.hasExtra(getResources().getString(R.string.send_parcel_movie))) {
            Movie movie = (Movie) intent.getParcelableExtra(getResources().getString(R.string.send_parcel_movie));
            tvMovieOverview.setText(movie.getOverview());
            tvMovieRating.setText(String.valueOf(movie.getVoteAverage()) + " / 10");
            tvMovieTitle.setText(movie.getTitle());
            tvMovieReleaseDate.setText(movie.getReleaseDate().substring(0, 4));
            Picasso.with(this)
                    .load(movie.getPosterPath())
                    .into(ivMoviePoster);

        } else {
            Toast.makeText(MovieDetailActivity.this, "Data not sent", Toast.LENGTH_SHORT).show();
        }
    }
}
