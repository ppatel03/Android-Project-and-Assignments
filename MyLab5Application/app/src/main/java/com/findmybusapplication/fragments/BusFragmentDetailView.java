package com.findmybusapplication.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.findmybusapplication.R;
import com.findmybusapplication.bean.Bus;
import com.findmybusapplication.constants.ArgumentConstants;
import com.findmybusapplication.constants.GenericConstants;
import com.findmybusapplication.constants.ImageMappingConstants;


import java.util.HashMap;
import java.util.List;



public class BusFragmentDetailView extends Fragment {    // TODO: Rename parameter arguments, choose names that match
    private  Bus bus= null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * param ARGS_OPTION option 1.
     * @return A new instance of fragment AboutMeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(Bus bus){
        BusFragmentDetailView fragment = new BusFragmentDetailView();
        Bundle args = new Bundle();
        args.putSerializable(ArgumentConstants.ARGS_BUS_DETAILS_DATA, bus);
        fragment.setArguments(args);
        return fragment;
    }



    public BusFragmentDetailView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if(getArguments() != null){
            bus = (Bus) getArguments().getSerializable(ArgumentConstants.ARGS_BUS_DETAILS_DATA);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        View fragmentPagerView = inflater.inflate(R.layout.fragment_bus_detail_view, container,false);

        //populate movie Data
        populateMovieData(fragmentPagerView);

        return fragmentPagerView;
    }

    private void populateMovieData(View view){
        String title = bus.getBusName();
        TextView titleView = (TextView) view.findViewById(R.id.busDetailTitle);
        titleView.setText(title);

        String description = bus.getBusDescription();
        TextView descriptionView = (TextView) view.findViewById(R.id.busDetailDescription);
        descriptionView.setText(description);

        String imageString = bus.getBusImage();
        int imageID = ImageMappingConstants.imageThumbnailLocationMap.get(imageString);
        ImageView imageView = (ImageView)view.findViewById(R.id.busDetailImage);
        imageView.setImageResource(imageID);

        //async task to get movie image from the url
        /*
        String url = (String)movieMap.get("url");
        final Bitmap bitmap = mImgMemoryCache.get("url");
        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
        }
        else{
            MyDownloadMovieImageTask task = new MyDownloadMovieImageTask(imageView);
            task.execute(new String[] {url});
        }
        */
        List<String> totalListOfStops = bus.getBusStops();
        String busStopsSepatorString = "";
        for(String stop : totalListOfStops){
            busStopsSepatorString += stop+"->";
        }
        busStopsSepatorString+="No More Stops";

        TextView busDetailTotalStops = (TextView) view.findViewById(R.id.busDetailTotalStops);
        busDetailTotalStops.setText(busStopsSepatorString);

        List<Double> nextAvailableTimingsList = bus.getNextTimings() ;
        String timeSepatorString = "Operates at ";
        for(Double d : nextAvailableTimingsList){
            timeSepatorString += d.toString()+ ",";
        }
        TextView nextAvailableTimingsView = (TextView) view.findViewById(R.id.busDetailNextAvailableTimings);
        nextAvailableTimingsView.setText(timeSepatorString);

        double tripCost = bus.getTotalTripCost();
        TextView tripCostView = (TextView) view.findViewById(R.id.busDetailTripCost);
        tripCostView.setText("Total Trip Cost : "+Math.round((float)tripCost)+"");

        String doNotOperateDays = bus.getDoNotOperate();
        TextView doNotOperateView = (TextView) view.findViewById(R.id.busDetailDoNotOperate);
        doNotOperateView.setText("This Bus do not operate on "+GenericConstants.doNotOperateString+doNotOperateDays);

        if(bus.getBusRatings() != null){
            Double rating = bus.getBusRatings();
            RatingBar ratingBar = (RatingBar) view.findViewById(R.id.busDetailRatings);
            ratingBar.setRating(Float.parseFloat(rating.toString())/2);
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
