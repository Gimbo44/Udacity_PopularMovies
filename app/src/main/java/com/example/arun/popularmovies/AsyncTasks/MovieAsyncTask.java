package com.example.arun.popularmovies.AsyncTasks;

import android.content.Context;
import android.widget.Toast;

import com.example.arun.popularmovies.Adapters.MovieRecyclerAdapter;
import com.example.arun.popularmovies.APIObjects.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Class designed to handle the background Async calls to the movie API.
 * Runs on the background thread to return a new arraylist of movies.
 */
public class MovieAsyncTask extends BaseAsyncTask {

    private MovieRecyclerAdapter mAdapter;


    public MovieAsyncTask(Context context, MovieRecyclerAdapter adapter) {
        super(context);
        mAdapter = adapter;
    }

    @Override
    protected void onPostExecute(String data) {
        //clear any existing data
        mAdapter.clear();

        //check if movies is null since doInBackground returns null
        //in certain cases
        if (data != null) {
            //if not, update the adapter
            try {
                mAdapter.addAll(getMovieDataFromJson(data));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            //notify the user about the failure scenaria via a Toast
            Toast.makeText(mContext, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<Movie> getMovieDataFromJson(String movieJsonStr) throws JSONException{

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

        ArrayList<Movie> movieObjs = new ArrayList<>();

        //Iterating through each movie json object to build the new Movie object
        for(int i=0; i< movieJsonArray.length(); i++){
            JSONObject movieJson = movieJsonArray.getJSONObject(i);
            // Adding the new movies to the movie arraylist
            movieObjs.add( new Movie(
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