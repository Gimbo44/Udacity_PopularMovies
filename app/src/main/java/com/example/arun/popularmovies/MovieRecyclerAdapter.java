package com.example.arun.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by arun on 23/11/16.
 */

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>{

    private Context mContext;
    private ArrayList<Movie> mMovieList;
    private static String LOG_TAG = MovieRecyclerAdapter.class.getSimpleName();

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
            Intent intent = new Intent(mContext, MovieActivity.class);
            intent.putExtra("MovieObj", mMovie);
            mContext.startActivity(intent);
        }
    }

    public MovieRecyclerAdapter(Context context, ArrayList<Movie> movieList) {
        mContext = context;
        mMovieList = movieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_listview_row, parent, false);
        MovieViewHolder vh = new MovieViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.mMovie = mMovieList.get(position);
        Picasso.with(mContext).load(holder.mMovie.getImgUrl()).into(holder.mPoster);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }


    public void addAll(ArrayList<Movie> movies){
        mMovieList = movies;
        notifyDataSetChanged();
    }

    public void clear(){
        mMovieList = new ArrayList<>();
        notifyDataSetChanged();
    }

}
