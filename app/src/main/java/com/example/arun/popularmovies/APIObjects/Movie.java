package com.example.arun.popularmovies.APIObjects;

import android.os.Parcel;
import android.os.Parcelable;

import static android.R.attr.path;

/**
 * Created by arun on 19/11/16.
 * A simple object designed to hold json movie data.
 */

public class Movie implements Parcelable {

    private String IMG_URI = "http://image.tmdb.org/t/p/w780";

    //Movie features
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
        //Required to append the prefix url to the given image url to comply with the api's standards.
        mImgUrl = IMG_URI + imgUrl;
        mRating = rating;
        mPlot = plot;
    }

    /*
    ===================================================================
                                Simple Getters
    ===================================================================
     */
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

    public String getSplicedImgUrl(){
        String imgTag = mImgUrl.substring(mImgUrl.lastIndexOf('/') + 1);
        return "/" + imgTag;
    }

    public double getRating() {
        return mRating;
    }

    public String getPlot() {
        return mPlot;
    }

    /*
    ===================================================================
                       Auto Generated Parcelable methods
    ===================================================================
     */
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
