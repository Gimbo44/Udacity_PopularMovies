package com.example.arun.popularmovies.AsyncTasks;

import android.content.Context;
import android.widget.Toast;

import com.example.arun.popularmovies.Adapters.ReviewRecyclerAdapter;
import com.example.arun.popularmovies.APIObjects.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by arun on 28/12/16.
 */

public class ReviewAsyncTask extends BaseAsyncTask {

    private ReviewRecyclerAdapter mAdapter;


    public ReviewAsyncTask(Context context, ReviewRecyclerAdapter adapter) {
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
                ArrayList<Review> test = getReviewDataFromJson(data);
                mAdapter.addAll(test);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            //notify the user about the failure scenaria via a Toast
            Toast.makeText(mContext, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<Review> getReviewDataFromJson(String movieJsonStr) throws JSONException{

        //Defining the required json keys
        final String REVIEW_LIST_RESULTS = "results";
        final String REVIEW_AUTHOR = "author";
        final String REVIEW_CONTENT = "content";

        //Parse json string into a json object
        JSONObject moviesJson = new JSONObject(movieJsonStr);
        JSONArray movieJsonArray = moviesJson.getJSONArray(REVIEW_LIST_RESULTS);

        ArrayList<Review> reviewObjs = new ArrayList<>();

        //Iterating through each movie json object to build the new Movie object
        for(int i=0; i< movieJsonArray.length(); i++){
            JSONObject movieJson = movieJsonArray.getJSONObject(i);
            // Adding the new movies to the movie arraylist
            reviewObjs.add( new Review(
                    movieJson.getString(REVIEW_AUTHOR),
                    movieJson.getString(REVIEW_CONTENT))
            );
        }

        return reviewObjs;
    }

}
