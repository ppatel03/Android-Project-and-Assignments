package com.mylab7application_patel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prashant on 2/5/2015.
 */
public class MyBaseAdapter extends BaseAdapter {
    private  final Context context;
    private final List<Map<String,?>> moviesList;

    public MyBaseAdapter(Context context, List<Map<String,?>> moviesList){
        this.context = context;
        this.moviesList = moviesList;
    }

    public boolean isEnabled(int position){
        if(position == 0){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public int getCount() {
        return moviesList.size();
    }

    @Override
    public Object getItem(int position) {
        return moviesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void removeItemFromList(List<Integer> deletionList){
        if(deletionList.size() >= this.moviesList.size()){
            this.moviesList.clear();
        }
        else{
            int count = 0;
            for(int position : deletionList){
                if(this.moviesList != null && this.moviesList.size() > position && this.isEnabled(position)){
                    this.moviesList.remove(position-(count++));
                }
            }

        }

    }

    public void duplicateMovieItemToNextPosition(int position){
        if(position < this.moviesList.size()){
            final HashMap<String, ?> movieMap = (HashMap<String, ?>) this.moviesList.get(position);
            HashMap<String, ?> duplicateMovieMap = movieMap;
            this.moviesList.add(position+1,duplicateMovieMap);
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return AdapterHelper.getRecycledView(parent, position, convertView, context, moviesList);
    }
}
