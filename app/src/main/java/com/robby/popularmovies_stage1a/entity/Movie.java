package com.robby.popularmovies_stage1a.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Robby on 7/2/2017.
 *
 * @author Robby Tan
 */

public class Movie implements Parcelable {

    public static final String TAG_ID = "id";
    public static final String TAG_TITLE = "title";
    public static final String TAG_POSTER_PATH = "poster_path";
    public static final String TAG_OVERVIEW = "overview";
    public static final String TAG_VOTE_AVERAGE = "vote_average";
    public static final String TAG_RELEASE_DATE = "release_date";

    private int id;
    private double voteAverage;
    private String title;
    private String posterPath;
    private String overview;
    private String releaseDate;


    public Movie() {
    }

    private Movie(Parcel in) {
        id = in.readInt();
        voteAverage = in.readDouble();
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return "https://image.tmdb.org/t/p/w185" + posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(voteAverage);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }
}
