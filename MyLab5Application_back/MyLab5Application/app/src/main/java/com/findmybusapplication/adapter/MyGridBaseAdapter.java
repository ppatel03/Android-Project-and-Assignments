package com.findmybusapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.findmybusapplication.R;
import com.findmybusapplication.bean.BusFormData;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by prashant on 2/6/2015.
 */
public class MyGridBaseAdapter extends BaseAdapter{
    private  final Context context;
    private final List<BusFormData> busFromDataList;

    public MyGridBaseAdapter(Context context, List<BusFormData> busFromDataList){
        this.context = context;
        this.busFromDataList = busFromDataList;
    }


    public class MyViewHolder {
        ImageView savedSearchBackgroundImage;
        TextView savedSearchGridTag;
    }

    @Override
    public int getCount() {
        return busFromDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return busFromDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = null;
        MyViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.grid_saved_search_view, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.savedSearchBackgroundImage= (ImageView) rowView.findViewById(R.id.savedSearchBackgroundImage);
            viewHolder.savedSearchGridTag = (TextView) rowView.findViewById(R.id.savedSearchGridTag);
            rowView.setTag(viewHolder);
        }
        else{
            rowView = convertView;
            viewHolder = (MyViewHolder)rowView.getTag();
        }

        //setting the color for odd and even positions
        if(position % 2 == 0){
            rowView.setBackgroundResource(R.color.earth_blue);
        }
        else{
            rowView.setBackgroundResource(R.color.cobalt_blue);
        }


        BusFormData busFormData = busFromDataList.get(position);
        if(busFormData != null){
            try{
                String tagText = " From ";
                tagText+= busFormData.getFrom();
                tagText+=" To ";
                tagText+=busFormData.getTo();
                tagText+=" Saved on ";
                tagText+= (new java.util.Date(busFormData.getCreationDate().getTime())).toString();
                viewHolder.savedSearchGridTag.setText(tagText);

            }
            catch (ClassCastException e){
                Log.e("ClassCastException while setting value from Movie Map ", e.toString());
            }

        }
        return  rowView;
    }
}
