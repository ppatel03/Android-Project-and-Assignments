package com.mylab6application;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prashant on 2/12/2015.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private static final int GRID_LAYOUT_TAB_POSITION = 1;
    final List<Map<String, ?>> movieList;
    private Context context;
    OnItemClickListener onItemClickListener;
    private int tabPosition;

    public MyRecyclerAdapter(Context context, List<Map<String, ?>> moviesList, int tabPosition) {
        this.context = context;
        this.movieList = moviesList;
        this.tabPosition = tabPosition;
    }

    public HashMap getItemAtPosition(int position) {
        if (position >= 0 && position < movieList.size()) {
            return (HashMap) movieList.get(position);
        } else return null;
    }

    public void addItemAtPosition(Map<String, ?> movieMap, int position) {
        if (position >= 0 && position < movieList.size()) {
            movieList.add(position + 1, movieMap);
        }
    }


    public void removeItemFromList(List<Integer> deletionList) {
        if (deletionList.size() >= this.movieList.size()) {
            this.movieList.clear();
        } else {
            int count = 0;
            for (int position : deletionList) {
                if (this.movieList != null && this.movieList.size() > position) {
                    this.movieList.remove(position - (count++));
                }
            }

        }

    }


    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        View v;


        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);


        MyViewHolder viewHolder = new MyViewHolder(v);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyRecyclerAdapter.MyViewHolder myViewHolder, int i) {

        Map<String, ?> movieMap = movieList.get(i);
        myViewHolder.bindMovieData(movieMap);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    //interface inside the class
    public interface OnItemClickListener {
        void onMyRecyclerItemClick(View view, int position);

        void onMyRecyclerItemLongClick(View view, int position);

        void onOverFlowButtonClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    /*This is where inner are mostly used. this example perfectly demonstrate the use of inner classes.
     In the constructor itself it attaches the listener(onItemClickListener) which uses parent class's instance variables.
    Had there been no inner class, then this listener should have been extracted from MyRecyclerViewAdapter class and
    assigned to MyViewHolder constructor
    */
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView movieTitle;
        TextView movieDescription;
        ImageView movieImage;
        RatingBar movieRatings;
        ImageView movieOverFLowButton;
        // final MyRecyclerAdapter myRecyclerAdapter;


        MyViewHolder(View v) {
            super(v);
            // this.myRecyclerAdapter = myRecyclerAdapter;
            movieImage = (ImageView) v.findViewById(R.id.movieImage);


            movieDescription = (TextView) v.findViewById(R.id.movieDescription);
            movieTitle = (TextView) v.findViewById(R.id.movieTitle);
            movieRatings = (RatingBar) v.findViewById(R.id.movieRatings);
            movieOverFLowButton = (ImageView) v.findViewById(R.id.overflow_button);

            if (movieOverFLowButton != null) {
                movieOverFLowButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onOverFlowButtonClick(v, getPosition());
                        }
                    }
                });
            }


            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        public HashMap getItemAtPosition(int position) {
            if (position >= 0 && position < movieList.size()) {
                return (HashMap) movieList.get(position);
            } else {
                return null;
            }
        }


        public void bindMovieData(Map<String, ?> movieMap) {
            movieImage.setImageResource((Integer) movieMap.get("image"));

            if (tabPosition != GRID_LAYOUT_TAB_POSITION) {
                movieTitle.setText((String) movieMap.get("name"));
                movieDescription.setText((String) movieMap.get("description"));
                movieOverFLowButton.setBackgroundColor(Color.LTGRAY);
                movieRatings.setRating(Float.parseFloat(movieMap.get("rating").toString()) / 2);

            } else {
                movieTitle.setText("");
                movieDescription.setText("");
                movieOverFLowButton.setVisibility(View.INVISIBLE);
                movieOverFLowButton.getLayoutParams().height =0;
                movieRatings.setVisibility(View.INVISIBLE);
                movieRatings.getLayoutParams().height =0;
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onMyRecyclerItemLongClick(v, getPosition());
            }
            return true;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onMyRecyclerItemClick(v, getPosition());
            }
        }

    }


}
