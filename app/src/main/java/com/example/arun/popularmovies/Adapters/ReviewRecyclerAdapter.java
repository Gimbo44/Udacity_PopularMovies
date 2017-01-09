package com.example.arun.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arun.popularmovies.APIObjects.Movie;
import com.example.arun.popularmovies.R;
import com.example.arun.popularmovies.APIObjects.Review;

import java.util.ArrayList;

/**
 * Created by arun on 26/12/16.
 */

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ReviewViewHolder>{


    private Context mContext;
    private ArrayList<Review> mDataList;
    private static String LOG_TAG = MovieRecyclerAdapter.class.getSimpleName();


    /**
     * A ViewHolder class designed at holding a view and a {@link Movie}
     * Object.
     */
    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        TextView mAuthor;
        TextView mContent;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            mAuthor = (TextView) itemView.findViewById(R.id.AuthorTextView);
            mContent = (TextView) itemView.findViewById(R.id.ContentTextView);

        }
    }


    public ReviewRecyclerAdapter(Context context, ArrayList<Review> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public ReviewRecyclerAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.review_listview_row, parent, false);
        ReviewRecyclerAdapter.ReviewViewHolder vh = new ReviewRecyclerAdapter.ReviewViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review currentObj = mDataList.get(position);
        holder.mAuthor.setText(currentObj.getAuthor());
        holder.mContent.setText(currentObj.getContent());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * Method designed to replicate ArrayAdapter.addAll, takes an arraylist of movies and
     * sets the adapters movie list to the new list.
     *
     * @param data, arraylist of T objects
     */
    public void addAll(ArrayList<Review> data) {
        mDataList = data;
        notifyDataSetChanged();
    }

    /**
     * Method designed to reset the adapters arraylist, similar to the ArrayAdapter.clear method
     */
    public void clear() {
        mDataList = new ArrayList<>();
        notifyDataSetChanged();
    }

}
