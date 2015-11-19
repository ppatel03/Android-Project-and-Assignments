package com.mylab7application_patel;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * helper class for myBaseAdapter used for setting the data from movieList
 *
 * Created by prashant on 2/5/2015.
 */
public class AdapterHelper {

    public static View getRecycledView( ViewGroup parent, int position, View convertView,
                                        Context context, List<Map<String,?>> moviesList ){

        View rowView = null;
        MyViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_row, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.movieImage= (ImageView) rowView.findViewById(R.id.movieImage);
            viewHolder.movieTitle = (TextView) rowView.findViewById(R.id.movieTitle);
            viewHolder.movieDescription = (TextView) rowView.findViewById(R.id.movieDescription);
            viewHolder.movieRatings = (RatingBar) rowView.findViewById(R.id.movieRatings);
            viewHolder.movieCheckbox = (CheckBox) rowView.findViewById(R.id.movieCheckBox);

            rowView.setTag(viewHolder);
        }
        else{
            rowView = convertView;
            viewHolder = (MyViewHolder)rowView.getTag();
        }


        //setting the color of the checkbox
        viewHolder.movieCheckbox.setTextColor(Color.rgb(255, 165, 0));
        viewHolder.movieCheckbox.setBackgroundColor(Color.LTGRAY);

        //setting the color for odd and even positions
        if(position % 2 == 0){
            rowView.setBackgroundResource(R.drawable.blackbackground1);
        }
        else{
            rowView.setBackgroundResource(R.drawable.blacknbackground8);
        }


        Map<String,?> movieMap = moviesList.get(position);
        if(movieMap != null){
            try{
                //setting the movie image from movie map
                int imageResource = (Integer)movieMap.get("image");
                viewHolder.movieImage.setImageResource(imageResource);

                //setting the movie title from movie map
                String movieTitleString = (String) movieMap.get("name");
                viewHolder.movieTitle.setText(movieTitleString);
                viewHolder.movieTitle.setTextColor(Color.YELLOW);


                //setting the movie Description from movie map
                String movieDescriptionString = (String) movieMap.get("description");
                viewHolder.movieDescription.setText(movieDescriptionString);

                //setting the ratings from movie map
                Double rating = (Double)movieMap.get("rating");
                viewHolder.movieRatings.setRating((float) (rating / 2));

                //set the previous checked box
                if(movieMap.get("selection") != null){
                    Boolean isChecked = (Boolean) movieMap.get("selection");
                    viewHolder.movieCheckbox.setChecked(isChecked);
                }

            }
            catch (ClassCastException e){
                Log.e("ClassCastException while setting value from Movie Map ", e.toString());
            }

        }
        return  rowView;
    }

    public static View getGridRecycledView( ViewGroup parent, int position, View convertView,
                                        Context context, List<Map<String,?>> moviesList ){

        View rowView = null;
        MyViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.grid_view, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.movieImage= (ImageView) rowView.findViewById(R.id.movieGridImage);
            viewHolder.movieRatings = (RatingBar) rowView.findViewById(R.id.movieGridRatings);
            rowView.setTag(viewHolder);
        }
        else{
            rowView = convertView;
            viewHolder = (MyViewHolder)rowView.getTag();
        }

        //setting the color for odd and even positions
        if(position % 2 == 0){
            rowView.setBackgroundResource(R.drawable.blackbackground1);
        }
        else{
            rowView.setBackgroundResource(R.drawable.blacknbackground8);
        }


        Map<String,?> movieMap = moviesList.get(position);
        if(movieMap != null){
            try{
                //setting the movie image from movie map
                int imageResource = (Integer)movieMap.get("image");
                viewHolder.movieImage.setImageResource(imageResource);

                //setting the ratings from movie map
                Double rating = (Double)movieMap.get("rating");
                viewHolder.movieRatings.setRating((float) (rating *4 / 10));

            }
            catch (ClassCastException e){
                Log.e("ClassCastException while setting value from Movie Map ", e.toString());
            }

        }
        return  rowView;
    }

}
