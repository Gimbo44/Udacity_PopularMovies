package com.example.arun.popularmovies.AsyncTasks;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.StringDef;
import android.util.Log;

import com.example.arun.popularmovies.R;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by arun on 27/12/16.
 */

public class BaseAsyncTask extends AsyncTask<String, Void, String> {

    protected Context mContext;
    private String LOG_TAG = MovieAsyncTask.class.getSimpleName();

    public static final String MOVIE_QUERY_POPULAR = "popular";
    public static final String MOVIE_QUERY_TOP_RATED = "top_rated";
    public static final String MOVIE_QUERY_REVIEW = "reviews";
    public static final String MOVIE_QUERY_TRAILERS = "videos";

    @StringDef({MOVIE_QUERY_POPULAR, MOVIE_QUERY_TOP_RATED,MOVIE_QUERY_REVIEW,MOVIE_QUERY_TRAILERS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface QueryModes {}


    public BaseAsyncTask(Context context) {
        super();
        mContext = context;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String movieJsonStr = "";
        int movieId = -1;
        @MovieAsyncTask.QueryModes String queryMode = params[0];
        if(params.length > 1){
            movieId = Integer.parseInt(params[1]);
        }
        try {

            // Create the request to OpenWeatherMap, and open the connection


            URL url = buildUrl(queryMode, movieId);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                Log.e(LOG_TAG, "Error, Stream was empty. ");
                return null;
            }
            movieJsonStr = buffer.toString();
            Log.d(LOG_TAG, movieJsonStr);
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }

        return movieJsonStr;
    }


    private URL buildUrl(@MovieAsyncTask.QueryModes String queryMode, int movieId){

        Uri.Builder builder = new Uri.Builder();
        String URL_SCHEME = "https";
        String URL_AUTH = "api.themoviedb.org";
        String URL_PATH = "3/movie";

        builder.scheme(URL_SCHEME).authority(URL_AUTH).path(URL_PATH);


        switch (queryMode){
            case MOVIE_QUERY_POPULAR:
                builder.appendPath(MOVIE_QUERY_POPULAR);
                break;
            case MOVIE_QUERY_TOP_RATED:
                builder.appendPath(MOVIE_QUERY_TOP_RATED);
                break;
            case MOVIE_QUERY_REVIEW:
                if(movieId != -1){
                    builder.appendPath(String.valueOf(movieId)).appendPath(MOVIE_QUERY_REVIEW);
                }
                break;
            case MOVIE_QUERY_TRAILERS:
                if(movieId != -1){
                    builder.appendPath(String.valueOf(movieId)).appendPath(MOVIE_QUERY_TRAILERS);
                }
                break;
        }

        builder.appendQueryParameter("api_key", mContext.getString(R.string.api_key));

        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, builder.toString());

        return url;
    }


}
