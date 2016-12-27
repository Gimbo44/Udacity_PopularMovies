package com.example.arun.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.arun.popularmovies.Adapters.MovieRecyclerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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
            MovieAsyncTask<Movie> task = new MovieAsyncTask<>(this, mAdapter);

            task.execute(MovieAsyncTask.MOVIE_QUERY_POPULAR);
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
        MovieAsyncTask task = new MovieAsyncTask(this, mAdapter);
        switch (item.getItemId()){
            case R.id.menuSortPopular:
                item.setChecked(true);
                task.execute(MovieAsyncTask.MOVIE_QUERY_POPULAR);
                break;
            case R.id.menuSortRating:
                item.setChecked(true);
                task.execute(MovieAsyncTask.MOVIE_QUERY_TOP_RATED);
                break;

        }
        return super.onOptionsItemSelected(item);
    }



}
