package com.findmybusapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.findmybusapplication.R;
import com.findmybusapplication.constants.ImageMappingConstants;


import java.util.List;
import java.util.Map;


/**
 * Created by prashant on 2/5/2015.
 */

public class MyBaseAdapterForFoldable extends BaseAdapter{


    private  final Context context;
    private final List<Map<String,?>> waitRequestList;

    public MyBaseAdapterForFoldable(Context context, List<Map<String,?>> waitRequestList){
        this.context = context;
        this.waitRequestList = waitRequestList;
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
        return waitRequestList.size();
    }

    @Override
    public Object getItem(int position) {
        return waitRequestList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void removeItemFromList(List<Integer> deletionList){
        if(deletionList.size() >= this.waitRequestList.size()){
            this.waitRequestList.clear();
        }
        else{
            int count = 0;
            for(int position : deletionList){
                if(this.waitRequestList != null && this.waitRequestList.size() > position && this.isEnabled(position)){
                    this.waitRequestList.remove(position-(count++));
                }
            }

        }

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = null;
        MyViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.wait_requests_list_item, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.faceImage= (ImageView) rowView.findViewById(R.id.faceImage);
            viewHolder.faceTitle= (TextView) rowView.findViewById(R.id.faceTitle);
            viewHolder.faceDescription = (TextView) rowView.findViewById(R.id.faceDescription);
            rowView.setTag(viewHolder);
        }
        else{
            rowView = convertView;
            viewHolder = (MyViewHolder)rowView.getTag();
        }



        Map<String,?> waitMap = waitRequestList.get(position);
        if(waitMap != null){
            try{
                viewHolder.faceImage.setImageResource(ImageMappingConstants.imageThumbnailFaceMap.get(position%6));

                //setting the movie title from movie map
                if(waitMap.get("name") != null){
                    String waitTitleString = (String) waitMap.get("name");
                    viewHolder.faceTitle.setText(waitTitleString);
                }


                //setting the movie Description from movie map
                if(waitMap.get("description") != null){
                    String waitDescriptionString = (String) waitMap.get("description");
                    viewHolder.faceDescription.setText(waitDescriptionString);
                }
            }
            catch (ClassCastException e){
                Log.e("ClassCastException ", e.toString());
            }

        }
        return  rowView;
    }




}

class MyViewHolder{
    TextView faceTitle;
    TextView faceDescription;
    ImageView faceImage;
}
