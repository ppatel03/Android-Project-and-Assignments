package com.mylab2application;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prashant on 1/30/2015.
 */
public class MovieDataOperations implements View.OnTouchListener, RatingBar.OnRatingBarChangeListener, View.OnClickListener{
    View view;
    MovieData movieData;
    int movieCount = 0;

    MovieDataOperations(View view){
        this.view = view;
        this.movieData = new MovieData();
        movieCount = 0;
    }


    public void loadMovie(){


        HashMap movieMap = movieData.getItem(movieCount);

        attachDataToLayoutFromMovieMap(movieMap);

        ImageView nextMovieView = (ImageView) view.findViewById(R.id.movieNextButtonImage);
        nextMovieView.setOnClickListener(this);

        ImageView prevMovieView = (ImageView) view.findViewById(R.id.moviePrevButtonImage);
        prevMovieView.setOnClickListener(this);

    }

    private void attachDataToLayoutFromMovieMap(HashMap movieMap){
        String title = (String) movieMap.get("name");
        TextView titleView = (TextView) view.findViewById(R.id.movieTitle);
        titleView.setText(title);


        String description = (String) movieMap.get("description");
        TextView descriptionView = (TextView) view.findViewById(R.id.movieDescription);
        descriptionView.setText(description);

        int imageResourceId = (int) movieMap.get("image");
        ImageView imageView = (ImageView) view.findViewById(R.id.movieImage);
        imageView.setImageResource(imageResourceId);

        String year = (String) movieMap.get("year");
        TextView yearView = (TextView) view.findViewById(R.id.movieYear);
        yearView.setText(year);

        String stars = (String) movieMap.get("stars");
        TextView starsView = (TextView) view.findViewById(R.id.movieStars);
        starsView.setText(stars);

        double rating = (double) movieMap.get("rating");
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.movieRatings);
        ratingBar.setRating((float) rating / 2);
        ratingBar.setOnRatingBarChangeListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        ratingBar.setIsIndicator(true);
        ratingBar.setFocusable(false);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.movieNextButtonImage:
                //DO something
                if(movieCount >=0 && movieCount < this.movieData.getSize()){
                    HashMap nextMovieMap = movieData.getItem(++movieCount);
                    if(nextMovieMap != null){
                        attachDataToLayoutFromMovieMap(nextMovieMap);
                    }
                    if(movieCount > this.movieData.getSize()-1){
                        movieCount = this.movieData.getSize() - 1;
                    }
                }

                break;
            case R.id.moviePrevButtonImage:
                //DO something
                if(movieCount >=0 && movieCount < this.movieData.getSize()) {
                    HashMap prevMovieMap = movieData.getItem(--movieCount);
                    if (prevMovieMap != null) {
                        attachDataToLayoutFromMovieMap(prevMovieMap);
                    }
                    if(movieCount < 0){
                        movieCount = 0;
                    }
                }


                break;

        }



    }
}
