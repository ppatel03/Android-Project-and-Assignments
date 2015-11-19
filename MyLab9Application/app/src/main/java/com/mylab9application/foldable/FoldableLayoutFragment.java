package com.mylab9application.foldable;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mylab9application.R;
import com.mylab9application.movie.MovieDataJson;
import com.mylab9application.proxy.ViewAdapterProxy;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoldableLayoutFragment extends Fragment {


    private  static final String ARGS_OPTION = "argument_option";
    private OnListItemSelectedListener mListener;
    ViewAdapterProxy recyclerViewAdapterProxy;

    public interface OnListItemSelectedListener{
        public void onListItemSelected(int position, HashMap<String, ?> movie);
    }


    public FoldableLayoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        Intent intent = getActivity().getIntent();
        MovieDataJson movieData = (MovieDataJson)intent.getSerializableExtra("movie");
        recyclerViewAdapterProxy = new ViewAdapterProxy(getActivity(),movieData,mListener,null);
        View rootView =  recyclerViewAdapterProxy.getViewUsingBaseAdapterForFoldable(inflater, container);
        return rootView;
    }

    public static FoldableLayoutFragment newInstance(int option){
        FoldableLayoutFragment fragment = new FoldableLayoutFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_OPTION, option);
        fragment.setArguments(args);
        return fragment;
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
