package com.example.arun.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.StringDef;
import android.util.Log;
import android.widget.Toast;

import com.example.arun.popularmovies.Adapters.MovieRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

/**
 * Class designed to handle the background Async calls to the movie API.
 * Runs on the background thread to return a new arraylist of movies.
 */
class MovieAsyncTask<T> extends AsyncTask<String, Void, ArrayList<T>> {


    public static final String MOVIE_QUERY_POPULAR = "popular";
    public static final String MOVIE_QUERY_TOP_RATED = "top_rated";
    public static final String MOVIE_QUERY_REVIEW = "reviews";
    public static final String MOVIE_QUERY_TRAILERS = "videos";

    @StringDef({MOVIE_QUERY_POPULAR, MOVIE_QUERY_TOP_RATED,MOVIE_QUERY_REVIEW,MOVIE_QUERY_TRAILERS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface QueryModes {}

    private String LOG_TAG = MovieAsyncTask.class.getSimpleName();
    private Context mContext;
    private MovieRecyclerAdapter mAdapter;


    public MovieAsyncTask(Context context, MovieRecyclerAdapter adapter) {
        super();
        mContext = context;
        mAdapter = adapter;
    }

    @Override
    protected void onPostExecute(ArrayList<T> data) {
        //clear any existing data
        mAdapter.clear();

        //check if movies is null since doInBackground returns null
        //in certain cases
        if (data != null) {
            //if not, update the adapter
            mAdapter.addAll((ArrayList<Movie>) data);
        } else {
            //notify the user about the failure scenaria via a Toast
            Toast.makeText(mContext, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private URL buildUrl(@QueryModes String queryMode, int movieId){

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

    @Override
    protected ArrayList<T> doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String movieJsonStr = "";
        int movieId = -1;
        @QueryModes String queryMode = params[0];
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
        try {
            switch (queryMode) {
                case MOVIE_QUERY_POPULAR:
                    return getMovieDataFromJson(movieJsonStr);
                case MOVIE_QUERY_REVIEW:
                    break;
                case MOVIE_QUERY_TOP_RATED:
                    return getMovieDataFromJson(movieJsonStr);
                case MOVIE_QUERY_TRAILERS:
                    break;
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error ", e);
        }

        return null;


    }

    private ArrayList<T> getMovieDataFromJson(String movieJsonStr) throws JSONException{

        //Defining the required json keys
        final String MOVIE_LIST_RESULTS = "results";
        final String MOVIE_ID = "id";
        final String MOVIE_TITLE = "title";
        final String MOVIE_RELEASE_DATE = "release_date";
        final String MOVIE_POSTER = "poster_path";
        final String MOVIE_RATING = "vote_average";
        final String MOVIE_OVERVIEW = "overview";

        //Parse json string into a json object
        JSONObject moviesJson = new JSONObject(movieJsonStr);
        JSONArray movieJsonArray = moviesJson.getJSONArray(MOVIE_LIST_RESULTS);

        ArrayList<T> movieObjs = new ArrayList<>();

        //Iterating through each movie json object to build the new Movie object
        for(int i=0; i< movieJsonArray.length(); i++){
            JSONObject movieJson = movieJsonArray.getJSONObject(i);
            // Adding the new movies to the movie arraylist
            movieObjs.add((T) new Movie(
                    movieJson.getLong(MOVIE_ID),
                    movieJson.getString(MOVIE_TITLE),
                    movieJson.getString(MOVIE_RELEASE_DATE),
                    movieJson.getString(MOVIE_POSTER),
                    movieJson.getDouble(MOVIE_RATING),
                    movieJson.getString(MOVIE_OVERVIEW)

            ));
        }

        return movieObjs;
    }
}