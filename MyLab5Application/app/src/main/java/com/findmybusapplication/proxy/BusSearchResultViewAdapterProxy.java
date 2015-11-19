package com.findmybusapplication.proxy;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.findmybusapplication.R;
import com.findmybusapplication.adapter.MyRecyclerAdapterForBusSearchResults;
import com.findmybusapplication.bean.Bus;
import com.findmybusapplication.data.BusData;
import com.findmybusapplication.fragments.RecyclerViewFragmentForBusSearchResults;
import com.findmybusapplication.helper.CacheHelper;
import com.findmybusapplication.helper.GenericHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prashant on 2/5/2015.
 */
public class BusSearchResultViewAdapterProxy implements
        MyRecyclerAdapterForBusSearchResults.OnBusItemClickListener, View.OnClickListener {

    final FragmentActivity fragmentActivity;
    MyRecyclerAdapterForBusSearchResults myRecyclerAdapter;
    private ImageView busSaveSearchOptionButton;

    BusData busData;
    RecyclerViewFragmentForBusSearchResults.OnListItemSelectedListener mListener;

    public BusSearchResultViewAdapterProxy(FragmentActivity fragmentActivity, BusData busData,
                                           RecyclerViewFragmentForBusSearchResults.OnListItemSelectedListener mListener){
        this.fragmentActivity = fragmentActivity;
        this.busData = busData;
        this.mListener = mListener;
    }



    public MyRecyclerAdapterForBusSearchResults getMyRecyclerAdapter(){
        return this.myRecyclerAdapter;
    }

    public BusData getBusData(){
        return this.busData;
    }

    public View getViewUsingBaseAdapter( LayoutInflater inflater, ViewGroup container){

        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_recycler_view_activity_for_bus_search_results,container,false);
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.fragmentActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        busSaveSearchOptionButton = (ImageView) rootView.findViewById(R.id.saveSearchOptionImage);
        busSaveSearchOptionButton.setOnClickListener(this);

        //creating image cache for saving images from the internet
        //CacheHelper.createCacheForImage();

        myRecyclerAdapter = new MyRecyclerAdapterForBusSearchResults(fragmentActivity.getApplicationContext(),
                busData.getSelectedBusList());
        Log.i("", "Calling Aysnc Task : MyDownloadMoviesJsonTask");

        //creating the asyn task
        /*
        MyDownloadMoviesJsonTask myDownloadMoviesJsonTask = new MyDownloadMoviesJsonTask(myRecyclerAdapter,this.movieData);
        String path = MovieDataJson.PATH_FOR_ALL_MOVIES_DATA;
        myDownloadMoviesJsonTask.execute(new String[]{path});
        */
        recyclerView.setAdapter(myRecyclerAdapter);
        Log.i("List adapter Attached", "List adapter Attached" + R.id.recyclerView);
        myRecyclerAdapter.setOnBusItemClickListener(this);

        return rootView;
    }



    @Override
    public void onMyRecyclerItemClick(View view, int position) {
        Log.i("","insider ItemClick");
        //do not know why it is final
        final Bus bus = (Bus) myRecyclerAdapter.getItemAtPosition(position);

        mListener.onListItemSelected(position,bus);


       // String id = (String) movieMap.get("id");

        //use this id to get the details of the movie in the form of json
        /*
        String path = MovieDataJson.PATH_FOR_ALL_MOVIES_DATA+MovieDataJson.PATH_FOR_MOVIE_DETAIL+id;
        MyDownloadMoviesDetailTask myDownloadMoviesDetailTask = new MyDownloadMoviesDetailTask(
                mListener,position);
        myDownloadMoviesDetailTask.execute(new String[]{path});
        */

    }

    @Override
    public void onMyRecyclerItemLongClick(View view, int position) {

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.saveSearchOptionImage :
                GenericHelper.saveSearchData(busData,this.fragmentActivity.getApplicationContext());
                //Show Search Saved Dialog Box
                new AlertDialog.Builder(this.fragmentActivity)
                        .setTitle("Bus Search Crtieria Data")
                        .setMessage("Data Saved Successfully")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Your code
                            }
                        })
                        .show();
                break;
            default:
        }
    }
}
