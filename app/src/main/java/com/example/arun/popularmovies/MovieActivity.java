package com.example.arun.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Intent intent = getIntent();
        if(intent.hasExtra("MovieObj")){
            Movie movie = (Movie) intent.getSerializableExtra("MovieObj");
            setTitle(movie.getTitle());
            TextView plotTextView = (TextView) findViewById(R.id.plotTextView);
            ImageView posterImageView = (ImageView) findViewById(R.id.Movie_ImageView);
            TextView releaseDateTextView = (TextView) findViewById(R.id.ReleaseTextView);
            TextView ratingTextView = (TextView) findViewById(R.id.RatingTextView);

            plotTextView.setText(movie.getPlot());
            Picasso.with(this).load(movie.getImgUrl()).into(posterImageView);
            releaseDateTextView.setText(movie.getDate());
            ratingTextView.setText(String.valueOf(movie.getRating()));
        }else{
            finish();
        }
    }
}
