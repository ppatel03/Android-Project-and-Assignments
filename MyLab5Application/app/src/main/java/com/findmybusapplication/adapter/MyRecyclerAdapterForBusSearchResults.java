package com.findmybusapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.findmybusapplication.R;
import com.findmybusapplication.bean.Bus;
import com.findmybusapplication.constants.ImageMappingConstants;


import java.util.List;


//static import. Java is awesome

/**
 * Created by prashant on 2/12/2015.
 */

public class MyRecyclerAdapterForBusSearchResults extends RecyclerView.Adapter<MyRecyclerAdapterForBusSearchResults.MyViewHolder> {


    final List<Bus>  busDataList;
    private Context context;
    OnBusItemClickListener onItemClickListener;

    public MyRecyclerAdapterForBusSearchResults(Context context, List<Bus> busDataList){
        this.context = context;
        this.busDataList = busDataList;
    }

    public Bus getItemAtPosition(int position){
        if (position >=0 && position < busDataList.size()){
            return (Bus) busDataList.get(position);
        } else return null;
    }

    public void addItemAtPosition(Bus bus, int position){
        if (position >=0 && position < busDataList.size()){
            busDataList.add(position+1,bus);
        }
    }



    public void removeItemFromList(List<Integer> deletionList){
        if(deletionList.size() >= this.busDataList.size()){
            this.busDataList.clear();
        }
        else{
            int count = 0;
            for(int position : deletionList){
                if(this.busDataList != null && this.busDataList.size() > position ){
                    this.busDataList.remove(position-(count++));
                }
            }

        }

    }



    @Override
    public MyRecyclerAdapterForBusSearchResults.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v ;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bus_search_results_card_view,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyRecyclerAdapterForBusSearchResults.MyViewHolder myViewHolder, int i) {

        Bus bus = busDataList.get(i);
        myViewHolder.bindMovieData(bus);

    }

    @Override
    public int getItemCount() {
        return busDataList.size();
    }

    //interface inside the class
    public interface OnBusItemClickListener{
        void onMyRecyclerItemClick(View view, int position);
        void onMyRecyclerItemLongClick(View view, int position);
    }

    public void setOnBusItemClickListener(final OnBusItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }



    /*This is where inner are mostly used. this example perfectly demonstrate the use of inner classes.
     In the constructor itself it attaches the listener(onItemClickListener) which uses parent class's instance variables.
    Had there been no inner class, then this listener should have been extracted from MyRecyclerViewAdapter class and
    assigned to MyViewHolder constructor
    */
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView busTitle;
        TextView busDescription;
        ImageView busImage;
        RatingBar busRatings;
        TextView busNearestTime;

        MyViewHolder(View v) {
            super(v);
            busTitle = (TextView) v.findViewById(R.id.busSearchResultsTitle);
            busDescription = (TextView) v.findViewById(R.id.busSearchResultsDescription);
            busImage = (ImageView) v.findViewById(R.id.busSearchResultsImage);
            busRatings = (RatingBar) v.findViewById(R.id.busSearchResultsRatings);
            busNearestTime = (TextView) v.findViewById(R.id.busNearestTime);

            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        public Bus getItemAtPosition(int position){
            if (position >=0 && position < busDataList.size()){
                return (Bus) busDataList.get(position);
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

        public void bindMovieData( Bus bus){
            busTitle.setText(bus.getBusName()+"-"+bus.getOperatorName());
            busDescription.setText(bus.getBusDescription());
            busRatings.setRating(Float.parseFloat(bus.getBusRatings().toString())/2);
            String imageName = bus.getBusImage();
            int imageID = ImageMappingConstants.imageThumbnailLocationMap.get(imageName);

            busImage.setImageResource(imageID);

            if(bus.getNextTimings() != null){
                Double busTime = (Double)bus.getNextTimings().get(0);
                busNearestTime.setText(busTime+"");
            }


            //async task to get movie image from the url
            /*
            String url = (String)movieMap.get("url");
            final Bitmap bitmap = mImgMemoryCache.get("url");
            if(bitmap != null){
                movieImage.setImageBitmap(bitmap);
            }
            else{
                MyDownloadMovieImageTask task = new MyDownloadMovieImageTask(movieImage);
                task.execute(new String[] {url});
            }
            */

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
