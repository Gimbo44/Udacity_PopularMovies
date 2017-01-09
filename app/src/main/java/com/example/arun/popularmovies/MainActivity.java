package com.example.arun.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.arun.popularmovies.Adapters.FavCursRecycleAdapter;
import com.example.arun.popularmovies.Adapters.MovieRecyclerAdapter;
import com.example.arun.popularmovies.AsyncTasks.MovieAsyncTask;
import com.example.arun.popularmovies.APIObjects.Movie;
import com.example.arun.popularmovies.data.MovieContract;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int FAVOURITE_LOADER = 0;


    @BindView(R.id.Movie_RecycleView) RecyclerView mMovieRecycleView;
    private MovieRecyclerAdapter mAdapter;
    private FavCursRecycleAdapter mFavCursRecycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new MovieRecyclerAdapter(this, new ArrayList<Movie>());
        mFavCursRecycleAdapter = new FavCursRecycleAdapter(this, null);
        mMovieRecycleView.setAdapter(mAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        mMovieRecycleView.setLayoutManager(gridLayoutManager);
        mMovieRecycleView.hasOnClickListeners();

        //If device is connected, then execute the async task.
        if(isNwConnected(this)){
            MovieAsyncTask task = new MovieAsyncTask(this, mAdapter);

            task.execute(MovieAsyncTask.MOVIE_QUERY_POPULAR);
        }
        getLoaderManager().initLoader( 0, null, this);
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
                mMovieRecycleView.setAdapter(mAdapter);
                break;
            case R.id.menuSortRating:
                item.setChecked(true);
                task.execute(MovieAsyncTask.MOVIE_QUERY_TOP_RATED);
                mMovieRecycleView.setAdapter(mAdapter);
                break;
            case R.id.menuSortFavourite:
                item.setChecked(true);
                mMovieRecycleView.setAdapter(mFavCursRecycleAdapter);
                mFavCursRecycleAdapter.notifyDataSetChanged();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Returns a new CursorLoader
        String[] projection = {
                MovieContract.MovieEntry._ID,
                MovieContract.MovieEntry.COLUMN_TITLE,
                MovieContract.MovieEntry.COLUMN_DESCRIPTION,
                MovieContract.MovieEntry.COLUMN_RATING,
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
                MovieContract.MovieEntry.COLUMN_IMG_URL,


                };
        return new CursorLoader(
                this,                                           // Parent activity context
                MovieContract.MovieEntry.CONTENT_URI,           // Table to query
                projection,                                     // Projection to return
                null,                                           // No selection clause
                null,                                           // No selection arguments
                null                                            // Default sort order
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mFavCursRecycleAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFavCursRecycleAdapter.swapCursor(null);
    }
}
