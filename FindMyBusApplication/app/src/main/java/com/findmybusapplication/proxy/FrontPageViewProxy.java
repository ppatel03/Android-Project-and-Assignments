package com.findmybusapplication.proxy;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.findmybusapplication.R;
import com.findmybusapplication.fragments.ViewPagerFragment;

/**
 * Created by prashant on 4/20/2015.
 */
public class FrontPageViewProxy implements View.OnClickListener {
    FragmentActivity fragmentActivity;
    ViewPagerFragment.OnViewPagerButtonClickListener onViewPagerButtonClickListener;

    public FrontPageViewProxy(FragmentActivity fragmentActivity,
                              ViewPagerFragment.OnViewPagerButtonClickListener onViewPagerButtonClickListener){
        this.fragmentActivity = fragmentActivity;
        this.onViewPagerButtonClickListener = onViewPagerButtonClickListener;
    }

    /**
     *
     * @param inflater
     * @param container
     * @param option
     * @return
     */
    public View getViewFromOption(LayoutInflater inflater, ViewGroup container, int option){

        View rootView = null;

        switch(option){
            case 0 :
                rootView = inflater.inflate(R.layout.home_view, container, false);
                performHomePageViewPagerOperations(rootView);
                break;
/*
            case 1 :
                rootView = inflater.inflate(R.layout.search_bus_view, container, false);
                break;

            case 2 :
                rootView = inflater.inflate(R.layout.nearest_bus_stop, container, false);
                break;

            case 3 :
                rootView = inflater.inflate(R.layout.saved_search_view, container, false);
                break;

            case 4 :
                rootView = inflater.inflate(R.layout.share_location_view, container, false);
                break;

     */       default:
                rootView = inflater.inflate(R.layout.home_view, container, false);

        }

        return rootView;

    }

    /**
     *
     * @param rootView
     */
    public void performHomePageViewPagerOperations(View rootView){

        ImageView searchBusButtonImage = (ImageView)rootView.findViewById(R.id.searchBusImage);
        searchBusButtonImage.setOnClickListener(this);

        ImageView nearestBusStopButtonImage = (ImageView)rootView.findViewById(R.id.nearestBusStopImage);
        nearestBusStopButtonImage.setOnClickListener(this);

        ImageView savedSearchButtonImage = (ImageView)rootView.findViewById(R.id.savedSearchImage);
        savedSearchButtonImage.setOnClickListener(this);

        ImageView shareLocationButtonImage = (ImageView)rootView.findViewById(R.id.shareLocationImage);
        shareLocationButtonImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        this.onViewPagerButtonClickListener.OnViewPagerButtonClick(id);
    }
}
