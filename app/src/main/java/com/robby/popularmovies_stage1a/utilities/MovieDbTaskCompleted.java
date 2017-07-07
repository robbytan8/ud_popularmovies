package com.robby.popularmovies_stage1a.utilities;

import com.robby.popularmovies_stage1a.entity.Movie;

import java.util.ArrayList;

/**
 * Created by Robby on 7/7/2017.
 * @author Robby Tan
 */

public interface MovieDbTaskCompleted {

    void onTaskCompleted(ArrayList<Movie> movies);
}
