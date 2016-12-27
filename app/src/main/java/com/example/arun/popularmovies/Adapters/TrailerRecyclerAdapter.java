package com.example.arun.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arun.popularmovies.Movie;
import com.example.arun.popularmovies.MovieActivity;
import com.example.arun.popularmovies.R;
import com.example.arun.popularmovies.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by arun on 27/12/16.
 */

public class TrailerRecyclerAdapter extends RecyclerView.Adapter<TrailerRecyclerAdapter.TrailerViewHolder>{


    private Context mContext;
    private ArrayList<Trailer> mDataList;
    private static String LOG_TAG = MovieRecyclerAdapter.class.getSimpleName();


    /**
     * A ViewHolder class designed at holding a view and a {@link com.example.arun.popularmovies.Movie}
     * Object.
     */
    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTtitle;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            mTtitle = (TextView) itemView.findViewById(R.id.TrailerTitleTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            //Implemented an onClick listener for the itemlayout
//            Intent intent = new Intent(mContext, MovieActivity.class);
//            intent.putExtra("MovieObj", mMovie);
//            mContext.startActivity(intent);
        }
    }


    public TrailerRecyclerAdapter(Context context, ArrayList<Trailer> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public TrailerRecyclerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_listview_row, parent, false);
        TrailerRecyclerAdapter.TrailerViewHolder vh = new TrailerRecyclerAdapter.TrailerViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(TrailerRecyclerAdapter.TrailerViewHolder holder, int position) {

        Trailer currentTrailer = mDataList.get(position);
        holder.mTtitle.setText(currentTrailer.getTitle());
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
    public void addAll(ArrayList<Trailer> data){
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
