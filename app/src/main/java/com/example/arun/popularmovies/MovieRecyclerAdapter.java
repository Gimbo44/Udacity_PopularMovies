package com.example.arun.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by arun on 23/11/16.
 * Custom RecyclerView.Adapter class designed to handle the display of movie posters
 */

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>{

    private Context mContext;
    private ArrayList<Movie> mMovieList;
    private static String LOG_TAG = MovieRecyclerAdapter.class.getSimpleName();


    /**
     * A ViewHolder class designed at holding a view and a {@link com.example.arun.popularmovies.Movie}
     * Object.
     */
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
        //Using the Picasso library to handle external image resourcing
        Picasso.with(mContext).load(holder.mMovie.getImgUrl()).into(holder.mPoster);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    /**
     * Method designed to replicate ArrayAdapter.addAll, takes an arraylist of movies and
     * sets the adapters movie list to the new list.
     * @param movies, arraylist of {@link com.example.arun.popularmovies.Movie} objects
     */
    public void addAll(ArrayList<Movie> movies){
        mMovieList = movies;
        notifyDataSetChanged();
    }

    /**
     * Method designed to reset the adapters arraylist, similar to the ArrayAdapter.clear method
     */
    public void clear(){
        mMovieList = new ArrayList<>();
        notifyDataSetChanged();
    }

}
