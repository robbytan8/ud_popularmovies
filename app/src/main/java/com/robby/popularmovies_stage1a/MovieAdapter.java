package com.robby.popularmovies_stage1a;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.robby.popularmovies_stage1a.entity.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Robby on 7/3/2017.
 * @author Robby Tan
 */

public class MovieAdapter extends BaseAdapter {

    private ArrayList<Movie> movies;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
        movies = new ArrayList<>();
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }
        Picasso.with(context)
                .load(movies.get(position).getPosterPath())
                .into(imageView);
        return imageView;
    }
}
