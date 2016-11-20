package com.example.arun.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.Z;

/**
 * Created by arun on 19/11/16.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {


    public MovieAdapter(Context context, ArrayList<Movie> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_listview_row, parent, false);
        }

        Movie currentMovie = getItem(position);
        if(currentMovie != null){
            ImageView moviePosterImage = (ImageView) listItemView.findViewById(R.id.Movie_ImageView);
            Picasso.with(getContext()).load(currentMovie.getImgUrl()).into(moviePosterImage);
        }

        return listItemView;
    }
}
