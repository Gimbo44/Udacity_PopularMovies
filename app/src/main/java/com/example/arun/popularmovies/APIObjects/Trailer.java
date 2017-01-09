package com.example.arun.popularmovies.APIObjects;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by arun on 27/12/16.
 */

public class Trailer {

    private String mTitle;
    private URL mUrl;
    private static final String URL_SCHEME = "http";
    private static final String URL_AUTH = "www.youtube.com";
    private static final String URL_PATH = "watch";

    public Trailer(String title, String key) {
        mTitle = title;
        mUrl = buildURL(key);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    private URL buildURL(String key){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(URL_SCHEME).authority(URL_AUTH).path(URL_PATH);
        builder.appendQueryParameter("v", key);

        try {
            return new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL getUrl() {
        return mUrl;
    }

    public void setUrl(String videoKey) {
        mUrl = buildURL(videoKey);
    }
}
