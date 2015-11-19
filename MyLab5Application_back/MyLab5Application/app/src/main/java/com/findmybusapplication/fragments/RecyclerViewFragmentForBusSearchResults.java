package com.findmybusapplication.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.findmybusapplication.bean.Bus;
import com.findmybusapplication.constants.ArgumentConstants;
import com.findmybusapplication.data.BusData;
import com.findmybusapplication.proxy.BusSearchResultViewAdapterProxy;


import java.util.HashMap;

/**
 * Created by prashant on 2/20/2015.
 */
public class RecyclerViewFragmentForBusSearchResults extends Fragment {

    private OnListItemSelectedListener mListener;




    public RecyclerViewFragmentForBusSearchResults() {
        // Required empty public constructor
    }

    public interface OnListItemSelectedListener{
        public void onListItemSelected(int position, Bus bus);
    }


    public static Fragment newInstance(int option){
        RecyclerViewFragmentForBusSearchResults fragment = new RecyclerViewFragmentForBusSearchResults();
        Bundle args = new Bundle();
        args.putInt(ArgumentConstants.ARGS_OPTION,option);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Called to do initial creation of a fragment.  This is called after
     * {@link #onAttach(android.app.Activity)} and before
     * {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}.
     * <p/>
     * <p>Note that this can be called while the fragment's activity is
     * still in the process of being created.  As such, you can not rely
     * on things like the activity's content view hierarchy being initialized
     * at this point.  If you want to do work once the activity itself is
     * created, see {@link #onActivityCreated(android.os.Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View aboutMeView = inflater.inflate(R.layout.fragment_recycler_view, container,false);
        setRetainInstance(true);
        Intent intent = getActivity().getIntent();
        BusData busData = (BusData)intent.getSerializableExtra(ArgumentConstants.ARGS_BUS_DATA);
        BusSearchResultViewAdapterProxy recyclerViewAdapterProxy = new BusSearchResultViewAdapterProxy(getActivity(),
                busData,mListener);
        View rootView =  recyclerViewAdapterProxy.getViewUsingBaseAdapter(inflater, container);
        return rootView;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mListener = (OnListItemSelectedListener) activity;

        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }


}
