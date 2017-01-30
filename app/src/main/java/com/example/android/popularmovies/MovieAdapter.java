package com.example.android.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mrfksiv on 23/01/2017.
 */

public class MovieAdapter extends ArrayAdapter<Movie>{

    public MovieAdapter(Activity context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_view_list, parent, false);
        }
        ImageView posterView = (ImageView) convertView.findViewById(R.id.iv_movie_poster);

        Picasso.with(parent.getContext()).load(movie.posterSmallPath).into(posterView);

//        posterView.setImageBitmap(movie.posterSmall);

        TextView movieTitle = (TextView) convertView.findViewById(R.id.tv_movie_name);
        movieTitle.setText(movie.title);

        // todo: do the rest of the items of the view
        return convertView;
    }


}
