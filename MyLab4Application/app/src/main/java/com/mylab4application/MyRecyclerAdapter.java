package com.mylab4application;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prashant on 2/12/2015.
 */

public class  MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    final List<Map<String,?>> movieList;
    private Context context;
    OnItemClickListener onItemClickListener;

    public MyRecyclerAdapter(Context context, List<Map<String,?>> moviesList){
        this.context = context;
        this.movieList = moviesList;
    }

    public HashMap getItemAtPosition(int position){
        if (position >=0 && position < movieList.size()){
            return (HashMap) movieList.get(position);
        } else return null;
    }

    public void addItemAtPosition(Map<String,?> movieMap, int position){
        if (position >=0 && position < movieList.size()){
            movieList.add(position+1,movieMap);
        }
    }



    public void removeItemFromList(List<Integer> deletionList){
        if(deletionList.size() >= this.movieList.size()){
            this.movieList.clear();
        }
        else{
            int count = 0;
            for(int position : deletionList){
                if(this.movieList != null && this.movieList.size() > position ){
                    this.movieList.remove(position-(count++));
                }
            }

        }

    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        Map<String,?> movieMap = movieList.get(position);

        if( ((Double)movieMap.get("rating")) < 8.0) {
          return 0;
        }
        return 1;
    }

    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        View v ;

        if(i ==1){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view,viewGroup,false);
        }
        else{
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_diff,viewGroup,false);

        }

        MyViewHolder viewHolder = new MyViewHolder(v);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyRecyclerAdapter.MyViewHolder myViewHolder, int i) {

        Map<String,?> movieMap = movieList.get(i);
        myViewHolder.bindMovieData(movieMap);


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    //interface inside the class
    public interface OnItemClickListener{
        void onMyRecyclerItemClick(View view, int position);
        void onMyRecyclerItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener){
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
        CheckBox movieCheckbox;
       // final MyRecyclerAdapter myRecyclerAdapter;


        MyViewHolder(View v) {
            super(v);
           // this.myRecyclerAdapter = myRecyclerAdapter;
            movieTitle = (TextView) v.findViewById(R.id.movieTitle);
            movieDescription = (TextView) v.findViewById(R.id.movieDescription);
            movieImage = (ImageView) v.findViewById(R.id.movieImage);
            movieRatings = (RatingBar) v.findViewById(R.id.movieRatings);
            movieCheckbox = (CheckBox) v.findViewById(R.id.movieCheckBox);


            movieCheckbox.setOnClickListener(new View.OnClickListener(){

                public void onClick(View v){
                    Boolean checkedStatus = ((CheckBox)v).isChecked();
                    HashMap<String,Boolean> movieMap = (HashMap<String,Boolean>) movieList.get(getPosition());
                    Log.i("Checkbox clicked :"+checkedStatus+" at Position "+getPosition(),"Checkbox clicked :"+checkedStatus);
                    movieMap.put("selection",checkedStatus);


                }

            });

            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        public HashMap getItemAtPosition(int position){
            if (position >=0 && position < movieList.size()){
                return (HashMap) movieList.get(position);
            }
            else {
                return null;
            }
        }


        @Override
        public void onClick(View v) {

            if(onItemClickListener != null){
                onItemClickListener.onMyRecyclerItemClick(v, getPosition());
            }
        }

        public void bindMovieData( Map<String,?> movieMap){
            movieTitle.setText((String) movieMap.get("name"));
            movieDescription.setText((String)movieMap.get("description"));
            movieImage.setImageResource((Integer) movieMap.get("image"));
            movieRatings.setRating(Float.parseFloat(movieMap.get("rating").toString())/2);
            movieCheckbox.setChecked((Boolean)movieMap.get("selection"));

            movieCheckbox.setTextColor(Color.rgb(255, 165, 0));
            movieCheckbox.setBackgroundColor(Color.LTGRAY);

        }

        @Override
        public boolean onLongClick(View v) {
            if(onItemClickListener != null){
                onItemClickListener.onMyRecyclerItemLongClick(v, getPosition());
            }
            return true;
        }

    }


}
