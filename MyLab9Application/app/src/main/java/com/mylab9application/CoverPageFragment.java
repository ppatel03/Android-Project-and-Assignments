package com.mylab9application;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by prashant on 2/20/2015.
 */

/**
 * A placeholder fragment containing a simple view.
 */
public  class CoverPageFragment extends Fragment implements View.OnClickListener{


    private  static final String ARGS_OPTION = "argument_option";



    private OnButtonItemClickListener bListener;




    public interface OnButtonItemClickListener{
        public void onButtonItemClickListener(View v);
    }

    public CoverPageFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            bListener = (OnButtonItemClickListener) activity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        attachListenersToButtons(rootView);

        return rootView;
    }

    public void attachListenersToButtons(View rootView){
       ImageView aboutMeButton = (ImageView)rootView.findViewById(R.id.aboutMeFragmentButton);
        aboutMeButton.setOnClickListener(this);


        ImageView masterDataFlowButton = (ImageView )rootView.findViewById(R.id.recyclerViewActivityButton);
        masterDataFlowButton.setOnClickListener(this);

        ImageView foldableLayoutButton = (ImageView) rootView.findViewById(R.id.foldableLayoutActivityButton);
        foldableLayoutButton.setOnClickListener(this);
    }

    public static Fragment newInstance(int option){
        CoverPageFragment fragment = new CoverPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_OPTION, option);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        Log.i("Inside onClick","Inside onClick");
        bListener.onButtonItemClickListener(v);

    }


}