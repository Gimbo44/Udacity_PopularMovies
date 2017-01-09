package com.example.arun.popularmovies;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arun.popularmovies.Adapters.ReviewRecyclerAdapter;
import com.example.arun.popularmovies.Adapters.TrailerRecyclerAdapter;
import com.example.arun.popularmovies.AsyncTasks.ReviewAsyncTask;
import com.example.arun.popularmovies.AsyncTasks.TrailerAsyncTask;
import com.example.arun.popularmovies.APIObjects.Movie;
import com.example.arun.popularmovies.APIObjects.Review;
import com.example.arun.popularmovies.APIObjects.Trailer;
import com.squareup.picasso.Picasso;

import com.example.arun.popularmovies.data.MovieContract.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.arun.popularmovies.MainActivity.isNwConnected;

public class MovieActivity extends AppCompatActivity {


    @BindView(R.id.DescriptionTextView) TextView mPlotTextView;
    @BindView(R.id.MovieImageView) ImageView mPosterImageView;
    @BindView(R.id.ReleaseYearTextView) TextView mReleaseDateTextView;
    @BindView(R.id.RatingTextView) TextView mRatingTextView;

    @BindView(R.id.ReviewsRecycleView) RecyclerView mReviewRecycleView;
    @BindView(R.id.TrailerRecycleView) RecyclerView mTrailerRecycleView;

    @BindView(R.id.floatingActionButton) FloatingActionButton mFavButton;

    private ReviewRecyclerAdapter mReviewRecyclerAdapter;
    private TrailerRecyclerAdapter mTrailerRecyclerAdapter;

    private boolean isFavourite;

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);



        Intent intent = getIntent();
        if(intent.hasExtra("MovieObj")){
            isFavourite = false;
            mMovie = intent.getParcelableExtra("MovieObj");

            setTitle(mMovie.getTitle());
            mPlotTextView.setText(mMovie.getPlot());
            Picasso.with(this).load(mMovie.getImgUrl()).into(mPosterImageView);
            mReleaseDateTextView.setText(String.format(getString(R.string.releaseDateFormat), mMovie.getDate()));
            mRatingTextView.setText(String.format(getString(R.string.ratingFormat), mMovie.getRating()));

            mReviewRecyclerAdapter = new ReviewRecyclerAdapter(this, new ArrayList<Review>());
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            mReviewRecycleView.setLayoutManager(llm);
            mReviewRecycleView.setAdapter(mReviewRecyclerAdapter);

            mTrailerRecyclerAdapter = new TrailerRecyclerAdapter(this, new ArrayList<Trailer>());
            LinearLayoutManager llm2 = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            mTrailerRecycleView.setLayoutManager(llm2);
            mTrailerRecycleView.setAdapter(mTrailerRecyclerAdapter);

            //If device is connected, then execute the async task.
            if(isNwConnected(this)){
                ReviewAsyncTask task = new ReviewAsyncTask(this, mReviewRecyclerAdapter);
                task.execute(ReviewAsyncTask.MOVIE_QUERY_REVIEW, String.valueOf(mMovie.getID()));

                TrailerAsyncTask task2 = new TrailerAsyncTask(this, mTrailerRecyclerAdapter);
                task2.execute(TrailerAsyncTask.MOVIE_QUERY_TRAILERS, String.valueOf(mMovie.getID()));
            }

            mFavButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isFavourite){
                        isFavourite = false;
                        mFavButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                        Toast.makeText(getBaseContext(), R.string.addFavourite, Toast.LENGTH_SHORT).show();
                    }else{
                        isFavourite = true;
                        mFavButton.setImageResource(R.drawable.ic_favorite_white_24dp);
                        saveMovie();
                        Toast.makeText(getBaseContext(), R.string.removeFavourite, Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }else{
            finish();
        }
    }

    private void saveMovie(){
        ContentValues movieData = new ContentValues();
        movieData.put(MovieEntry._ID, mMovie.getID());
        movieData.put(MovieEntry.COLUMN_TITLE, mMovie.getTitle());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            movieData.put(MovieEntry.COLUMN_RELEASE_DATE, sdf.parse(mMovie.getDate()).getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        movieData.put(MovieEntry.COLUMN_IMG_URL, mMovie.getSplicedImgUrl());
        movieData.put(MovieEntry.COLUMN_RATING, mMovie.getRating());
        movieData.put(MovieEntry.COLUMN_DESCRIPTION, mMovie.getPlot());
        Uri newUri = getContentResolver().insert(MovieEntry.CONTENT_URI, movieData);
        Long id = ContentUris.parseId(newUri);
        Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
    }

    private void deleteMovie(){
        
    }
}
