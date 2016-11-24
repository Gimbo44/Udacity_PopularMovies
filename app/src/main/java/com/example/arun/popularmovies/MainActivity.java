package com.example.arun.popularmovies;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.Movie_RecycleView) RecyclerView mMovieRecycleView;
    private MovieRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new MovieRecyclerAdapter(this, new ArrayList<Movie>());
        mMovieRecycleView.setAdapter(mAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        mMovieRecycleView.setLayoutManager(gridLayoutManager);
        mMovieRecycleView.hasOnClickListeners();

        //If device is connected, then execute the async task.
        if(isNwConnected(this)){
            FetchMovieAsyncTask task = new FetchMovieAsyncTask();
            task.execute("popular");
        }
    }

    /**
     * A simple method designed to check the network connection status
     * @param context, the activity context
     * @return a boolean value, indicating the network connection status
     */
    public static boolean isNwConnected(Context context) {
        if (context == null) {
            return true;
        }
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
        if (nwInfo != null && nwInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_by, menu);
        menu.findItem(R.id.menuSortPopular).setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FetchMovieAsyncTask task = new FetchMovieAsyncTask();
        switch (item.getItemId()){
            case R.id.menuSortPopular:
                item.setChecked(true);
                task.execute("popular");
                break;
            case R.id.menuSortRating:
                item.setChecked(true);
                task.execute("top_rated");
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Class designed to handle the background Async calls to the movie API.
     * Runs on the background thread to return a new arraylist of movies.
     */
    private class FetchMovieAsyncTask extends AsyncTask<String, Void, ArrayList<Movie>>{

        private String LOG_TAG = FetchMovieAsyncTask.class.getSimpleName();

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            //clear any existing data
            mAdapter.clear();

            //check if movies is null since doInBackground returns null
            //in certain cases
            if (movies != null) {
                //if not, update the adapter
                mAdapter.addAll(movies);
            } else {
                //notify the user about the failure scenaria via a Toast
                Toast.makeText(MainActivity.this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = "";

            try {

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https").authority("api.themoviedb.org").path("3/movie").appendPath(params[0]);
                builder.appendQueryParameter("api_key", getString(R.string.api_key));

                URL url = new URL(builder.build().toString());
                Log.d(LOG_TAG, builder.toString());
                // Create the request to OpenWeatherMap, and open the connection
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
                return getMovieDataFromJson(movieJsonStr);

            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error ", e);
            }

            return null;


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
                movieObjs.add(new Movie(
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
}
