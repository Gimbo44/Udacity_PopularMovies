package com.example.arun.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.arun.popularmovies.APIObjects.Movie;
import com.example.arun.popularmovies.MovieActivity;
import com.example.arun.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import static android.os.Build.VERSION_CODES.M;


/**
 * Created by arun on 2/01/17.
 */

public class FavCursRecycleAdapter extends CursorRecyclerViewAdapter<FavCursRecycleAdapter.MovieViewHolder>{

    static final int MOVIE_COLUMN_ID_INDEX = 0;
    static final int MOVIE_COLUMN_TITLE_INDEX = 1;
    static final int MOVIE_COLUMN_DESC_INDEX = 2;
    static final int MOVIE_COLUMN_RATING_INDEX = 3;
    static final int MOVIE_COLUMN_DATE_INDEX = 4;
    static final int MOVIE_COLUMN_IMG_URL_INDEX = 5;

    public FavCursRecycleAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mPoster;
        Movie mMovie;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mPoster = (ImageView) itemView.findViewById(R.id.Movie_ImageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //Implemented an onClick listener for the itemlayout
            Intent intent = new Intent(mContext, MovieActivity.class);
            intent.putExtra("MovieObj", mMovie);
            mContext.startActivity(intent);
        }
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, Cursor cursor) {


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        viewHolder.mMovie = new Movie(
                cursor.getLong(MOVIE_COLUMN_ID_INDEX),
                cursor.getString(MOVIE_COLUMN_TITLE_INDEX),
                dateFormat.format(cursor.getLong(MOVIE_COLUMN_DATE_INDEX)),
                cursor.getString(MOVIE_COLUMN_IMG_URL_INDEX),
                cursor.getInt(MOVIE_COLUMN_RATING_INDEX),
                cursor.getString(MOVIE_COLUMN_DESC_INDEX)
        );

        //Using the Picasso library to handle external image resourcing
        Picasso.with(mContext).load(viewHolder.mMovie.getImgUrl()).into(viewHolder.mPoster);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_listview_row, parent, false);
        MovieViewHolder vh = new MovieViewHolder(itemView);
        return vh;
    }
}
