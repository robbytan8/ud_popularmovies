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

/*
{
  "page": 1,
  "total_results": 19488,
  "total_pages": 975,
  "results": [
    {
      "vote_count": 3593,
      "id": 321612,
      "video": false,
      "vote_average": 6.8,
      "title": "Beauty and the Beast",
      "popularity": 141.652052,
      "poster_path": "/tWqifoYuwLETmmasnGHO7xBjEtt.jpg",
      "original_language": "en",
      "original_title": "Beauty and the Beast",
      "genre_ids": [
        10751,
        14,
        10749
      ],
      "backdrop_path": "/6aUWe0GSl69wMTSWWexsorMIvwU.jpg",
      "adult": false,
      "overview": "A live-action adaptation of Disney's version of the classic 'Beauty and the Beast' tale of a cursed prince and a beautiful young woman who helps him break the spell.",
      "release_date": "2017-03-16"
    },
 */

/* GET MOVIE (https://api.themoviedb.org/3/movie/321612?api_key=ade29f1ef3ae2c643a2410fbebfbbb27&language=en-US)
{
  "adult": false,
  "backdrop_path": "/6aUWe0GSl69wMTSWWexsorMIvwU.jpg",
  "belongs_to_collection": null,
  "budget": 160000000,
  "genres": [
    {
      "id": 10751,
      "name": "Family"
    },
    {
      "id": 14,
      "name": "Fantasy"
    },
    {
      "id": 10749,
      "name": "Romance"
    }
  ],
  "homepage": "http://movies.disney.com/beauty-and-the-beast-2017",
  "id": 321612,
  "imdb_id": "tt2771200",
  "original_language": "en",
  "original_title": "Beauty and the Beast",
  "overview": "A live-action adaptation of Disney's version of the classic 'Beauty and the Beast' tale of a cursed prince and a beautiful young woman who helps him break the spell.",
  "popularity": 140.652052,
  "poster_path": "/tWqifoYuwLETmmasnGHO7xBjEtt.jpg",
  "production_companies": [
    {
      "name": "Walt Disney Pictures",
      "id": 2
    },
    {
      "name": "Mandeville Films",
      "id": 10227
    }
  ],
  "production_countries": [
    {
      "iso_3166_1": "GB",
      "name": "United Kingdom"
    },
    {
      "iso_3166_1": "US",
      "name": "United States of America"
    }
  ],
  "release_date": "2017-03-16",
  "revenue": 1256977550,
  "runtime": 129,
  "spoken_languages": [
    {
      "iso_639_1": "en",
      "name": "English"
    },
    {
      "iso_639_1": "fr",
      "name": "Français"
    },
    {
      "iso_639_1": "th",
      "name": "ภาษาไทย"
    }
  ],
  "status": "Released",
  "tagline": "Be our guest.",
  "title": "Beauty and the Beast",
  "video": false,
  "vote_average": 6.8,
  "vote_count": 3613
}
 */