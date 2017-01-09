package com.example.arun.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.arun.popularmovies.APIObjects.Movie;
import com.example.arun.popularmovies.MovieActivity;
import com.example.arun.popularmovies.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by arun on 23/11/16.
 * Custom RecyclerView.Adapter class designed to handle the display of movie posters
 */

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>{


    private Context mContext;
    private ArrayList<Movie> mDataList;
    private static String LOG_TAG = MovieRecyclerAdapter.class.getSimpleName();


    /**
     * A ViewHolder class designed at holding a view and a {@link Movie}
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


    public MovieRecyclerAdapter(Context context, ArrayList<Movie> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_listview_row, parent, false);
        MovieViewHolder vh = new MovieViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        holder.mMovie = (Movie) mDataList.get(position);
        //Using the Picasso library to handle external image resourcing
        Picasso.with(mContext).load(holder.mMovie.getImgUrl()).into(holder.mPoster);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * Method designed to replicate ArrayAdapter.addAll, takes an arraylist of movies and
     * sets the adapters movie list to the new list.
     * @param data, arraylist of T objects
     */
    public void addAll(ArrayList<Movie> data){
        mDataList = data;
        notifyDataSetChanged();
    }

    /**
     * Method designed to reset the adapters arraylist, similar to the ArrayAdapter.clear method
     */
    public void clear(){
        mDataList = new ArrayList<>();
        notifyDataSetChanged();
    }

}
