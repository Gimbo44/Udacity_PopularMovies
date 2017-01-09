package com.example.arun.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arun.popularmovies.APIObjects.Movie;
import com.example.arun.popularmovies.R;
import com.example.arun.popularmovies.APIObjects.Trailer;

import java.util.ArrayList;

/**
 * Created by arun on 27/12/16.
 */

public class TrailerRecyclerAdapter extends RecyclerView.Adapter<TrailerRecyclerAdapter.TrailerViewHolder>{


    private Context mContext;
    private ArrayList<Trailer> mDataList;
    private static String LOG_TAG = MovieRecyclerAdapter.class.getSimpleName();


    /**
     * A ViewHolder class designed at holding a view and a {@link Movie}
     * Object.
     */
    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTitle;
        Trailer mTrailer;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.TrailerTitleTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //Implemented an onClick listener for the itemlayout
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mTrailer.getUrl().toString())));
        }
    }


    public TrailerRecyclerAdapter(Context context, ArrayList<Trailer> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public TrailerRecyclerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.trailer_listview_row, parent, false);
        TrailerRecyclerAdapter.TrailerViewHolder vh = new TrailerRecyclerAdapter.TrailerViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(TrailerRecyclerAdapter.TrailerViewHolder holder, int position) {

        Trailer currentTrailer = mDataList.get(position);
        holder.mTitle.setText(currentTrailer.getTitle());
        holder.mTrailer = currentTrailer;
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

    public ArrayList<Trailer> getTrailers(){
        return mDataList;
    }

}
