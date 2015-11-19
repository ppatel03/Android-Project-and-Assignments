package com.findmybusapplication.fragments;

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

import java.util.HashMap;


public class FragmentDetailView extends Fragment implements View.OnClickListener {    // TODO: Rename parameter arguments, choose names that match


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private  static final String ARGS_MOVIE = "argument_movie";
    private  static final String ARGS_LAYOUT = "argument_layout";
    private  static final String VIEW_PAGER = "view_pager";



    private int total = 0;

    private String textClickString1 = "The TextView is clicked  ";
    private String textClickString2 = " times ";
    private String finalOutputString=null;

    private HashMap<String,?> movieMap = null;


    public int getTotal(){
        return this.total;
    }

    public void setFinalOutputString(String finalOutputString){
        this.finalOutputString = finalOutputString;

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
        FragmentDetailView fragment = new FragmentDetailView();
        Bundle args = new Bundle();
        args.putInt(ARGS_LAYOUT,type);
        fragment.setArguments(args);
        return fragment;
    }



    public FragmentDetailView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if(getArguments() != null){
            movieMap = (HashMap<String,?>) getArguments().getSerializable(ARGS_MOVIE);
        }

        if(savedInstanceState != null){
            finalOutputString = savedInstanceState.getString("Total");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        View fragmentPagerView = inflater.inflate(R.layout.home_view, container,false);

        //populate movie Data
        //populateMovieData(fragmentPagerView);

        return fragmentPagerView;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("Total", finalOutputString);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();


    }
}
