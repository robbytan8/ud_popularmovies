package com.robby.popularmovies_stage1a;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.robby.popularmovies_stage1a.entity.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_movie_overview) TextView tvMovieOverview;
    @BindView(R.id.tv_movie_rating_detail) TextView tvMovieRating;
    @BindView(R.id.tv_movie_release_date_detail) TextView tvMovieReleaseDate;
    @BindView(R.id.tv_movie_title_detail) TextView tvMovieTitle;
    @BindView(R.id.tv_more) TextView tvMore;
    @BindView(R.id.tv_less) TextView tvLess;
    @BindView(R.id.im_movie_poster_detail) ImageView ivMoviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
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
            System.out.println("Line count: " + tvMovieOverview.getLineCount());
            System.out.println("Max line "+ tvMovieOverview.getLineHeight());
            tvMovieOverview.post(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Line count: " + tvMovieOverview.getLineCount());
                    if (tvMovieOverview.getLineCount() > 5 && tvMore.getVisibility() == View.INVISIBLE) {
                        tvMore.setVisibility(View.VISIBLE);
                        System.out.println("Run A");
                    } else if (tvMovieOverview.getLineCount() > 5 && tvMore.getVisibility() == View.VISIBLE) {
//                        tvMore.setVisibility(View.INVISIBLE);
//                        tvLess.setVisibility(View.VISIBLE);
                        showMore();
                        System.out.println("Run B");
                    } else if (tvMovieOverview.getLineCount() > 5 && tvLess.getVisibility() == View.VISIBLE) {
//                        tvMore.setVisibility(View.VISIBLE);
//                        tvLess.setVisibility(View.INVISIBLE);
                        showLess();
                        System.out.println("Run C");
                    }
                }
            });
        } else {
            Toast.makeText(MovieDetailActivity.this, "Data not sent", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.tv_more)
    public void showMore() {
        tvMore.setVisibility(View.INVISIBLE);
        tvLess.setVisibility(View.VISIBLE);
        tvMovieOverview.setMaxLines(Integer.MAX_VALUE);
    }

    @OnClick(R.id.tv_less)
    public void showLess() {
        tvMore.setVisibility(View.VISIBLE);
        tvLess.setVisibility(View.INVISIBLE);
        tvMovieOverview.setMaxLines(5);
    }
}
