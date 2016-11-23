package com.example.arun.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by arun on 23/11/16.
 */

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {

    private Context mContext;
    private ArrayList<Movie> mMovieList;

    public static class MovieViewHolder extends RecyclerView.ViewHolder{

        ImageView mPoster;
        Movie mMovie;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mPoster = (ImageView) itemView.findViewById(R.id.Movie_ImageView);
        }
    }


    public MovieRecyclerAdapter(Context context, ArrayList<Movie> movieList) {
        mContext = context;
        mMovieList = movieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.movie_listview_row, parent, false);
        return new MovieViewHolder(view);
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
