package com.example.arun.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieActivity extends AppCompatActivity {


    @BindView(R.id.DescriptionTextView) TextView mPlotTextView;
    @BindView(R.id.MovieImageView) ImageView mPosterImageView;
    @BindView(R.id.ReleaseYearTextView) TextView mReleaseDateTextView;
    @BindView(R.id.RatingTextView) TextView mRatingTextView;

    @BindView(R.id.ReviewsRecycleView) RecyclerView mReviewRecycleView;
    @BindView(R.id.TrailerRecycleView) RecyclerView mMovieRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);



        Intent intent = getIntent();
        if(intent.hasExtra("MovieObj")){
            Movie movie = intent.getParcelableExtra("MovieObj");

            setTitle(movie.getTitle());
            mPlotTextView.setText(movie.getPlot());
            Picasso.with(this).load(movie.getImgUrl()).into(mPosterImageView);
            mReleaseDateTextView.setText(String.format(getString(R.string.releaseDateFormat), movie.getDate()));
            mRatingTextView.setText(String.format(getString(R.string.ratingFormat), movie.getRating()));
        }else{
            finish();
        }
    }
}
