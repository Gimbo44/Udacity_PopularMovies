package com.example.arun.popularmovies;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by arun on 19/11/16.
 */

public class Movie implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.LOG_TAG);
        dest.writeLong(this.mID);
        dest.writeString(this.mTitle);
        dest.writeString(this.mDate);
        dest.writeString(this.mImgUrl);
        dest.writeDouble(this.mRating);
        dest.writeString(this.mPlot);
    }

    protected Movie(Parcel in) {
        this.LOG_TAG = in.readString();
        this.mID = in.readLong();
        this.mTitle = in.readString();
        this.mDate = in.readString();
        this.mImgUrl = in.readString();
        this.mRating = in.readDouble();
        this.mPlot = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
