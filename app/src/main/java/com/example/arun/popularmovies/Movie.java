package com.example.arun.popularmovies;

import android.net.Uri;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by arun on 19/11/16.
 */

public class Movie implements Serializable {

    private String LOG_TAG = Movie.class.getSimpleName();
    private long mID;
    private String mTitle;
    private String mDate;
    private String mImgUrl;
    private double mRating;
    private String mPlot;

    public Movie(long id, String title, String date, String imgUrl, double rating, String plot) {
        mID = id;
        mTitle = title;
        mDate = date;
        mImgUrl = "http://image.tmdb.org/t/p/w780" + imgUrl;
        mRating = rating;
        mPlot = plot;
    }

    public long getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getImgUrl() {
        return mImgUrl;
    }

    public double getRating() {
        return mRating;
    }

    public String getPlot() {
        return mPlot;
    }
}
