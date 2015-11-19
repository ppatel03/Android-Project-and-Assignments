package com.findmybusapplication.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.findmybusapplication.R;
import com.findmybusapplication.constants.ArgumentConstants;
import com.findmybusapplication.constants.GenericConstants;
import com.findmybusapplication.proxy.FrontPageViewProxy;

import java.util.HashMap;

/**
 * Created by prashant on 4/20/2015.
 */
public class ViewPagerFragment extends Fragment implements View.OnClickListener{
    OnViewPagerButtonClickListener onViewPagerButtonClickListener;

    public interface OnViewPagerButtonClickListener{
        public void OnViewPagerButtonClick(int id);
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * param ARGS_OPTION option 1.
     * @return A new instance of fragment AboutMeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance( int type){
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ArgumentConstants.ARGS_LAYOUT, type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,  Bundle savedInstanceState) {
        setRetainInstance(true);
        int option = getArguments().getInt(ArgumentConstants.ARGS_LAYOUT);

        //FrontPageViewProxy frontPageViewProxy = new FrontPageViewProxy(getActivity(),onViewPagerButtonClickListener);

        View rootView = inflater.inflate(R.layout.home_view, container, false);
        performHomePageViewPagerOperations(rootView);

        //return frontPageViewProxy.getViewFromOption(inflater,container,option);
        return rootView;
    }

    /**
     *
     * @param rootView
     */
    public void performHomePageViewPagerOperations(View rootView){
/*
        ImageView searchBusButtonImage = (ImageView)rootView.findViewById(R.id.searchBusImage);
        searchBusButtonImage.setOnClickListener( this);

        ImageView nearestBusStopButtonImage = (ImageView)rootView.findViewById(R.id.nearestBusStopImage);
        nearestBusStopButtonImage.setOnClickListener(this);

        ImageView savedSearchButtonImage = (ImageView)rootView.findViewById(R.id.savedSearchImage);
        savedSearchButtonImage.setOnClickListener(this);

        ImageView shareLocationButtonImage = (ImageView)rootView.findViewById(R.id.shareLocationImage);
        shareLocationButtonImage.setOnClickListener(this); */
    }


    /*
    @Override
    public void onAttach(Activity activity) {
        Log.i("RecyclerViewFragment ", " inside onAttach");
        super.onAttach(activity);
        try {
            onViewPagerButtonClickListener = (OnViewPagerButtonClickListener) activity;
            Log.i("RecyclerViewFragment ", " mListener is " + (onViewPagerButtonClickListener));

        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    } */

    @Override
    public void onClick(View v) {
        int id = v.getId();
        this.onViewPagerButtonClickListener.OnViewPagerButtonClick(id);
    }


}
