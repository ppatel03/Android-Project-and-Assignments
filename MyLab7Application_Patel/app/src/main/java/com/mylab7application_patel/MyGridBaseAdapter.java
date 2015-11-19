package com.mylab7application_patel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by prashant on 2/6/2015.
 */
public class MyGridBaseAdapter extends BaseAdapter{
    private  final Context context;
    private final List<Map<String,?>> moviesList;

    public MyGridBaseAdapter(Context context, List<Map<String,?>> moviesList){
        this.context = context;
        this.moviesList = moviesList;
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



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return AdapterHelper.getGridRecycledView(parent, position, convertView, context, moviesList);
    }
}
