package com.example.arun.popularmovies.AsyncTasks;

import android.content.Context;
import android.widget.Toast;

import com.example.arun.popularmovies.Adapters.TrailerRecyclerAdapter;
import com.example.arun.popularmovies.APIObjects.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by arun on 28/12/16.
 */

public class TrailerAsyncTask extends BaseAsyncTask {

    private TrailerRecyclerAdapter mAdapter;


    public TrailerAsyncTask(Context context, TrailerRecyclerAdapter adapter) {
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
                mAdapter.addAll(getTrailerDataFromJson(data));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            //notify the user about the failure scenaria via a Toast
            Toast.makeText(mContext, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<Trailer> getTrailerDataFromJson(String movieJsonStr) throws JSONException{

        //Defining the required json keys
        final String TRAILER_LIST_RESULTS = "results";
        final String TRAILER_NAME = "name";
        final String TRAILER_KEY = "key";

        //Parse json string into a json object
        JSONObject trailersJson = new JSONObject(movieJsonStr);
        JSONArray trailersJsonArray = trailersJson.getJSONArray(TRAILER_LIST_RESULTS);

        ArrayList<Trailer> trailerObjs = new ArrayList<>();

        //Iterating through each movie json object to build the new Movie object
        for(int i=0; i< trailersJsonArray.length(); i++){
            JSONObject trailerJson = trailersJsonArray.getJSONObject(i);
            trailerObjs.add(new Trailer(
                    trailerJson.getString(TRAILER_NAME),
                    trailerJson.getString(TRAILER_KEY)

            ));

        }

        return trailerObjs;
    }
}
