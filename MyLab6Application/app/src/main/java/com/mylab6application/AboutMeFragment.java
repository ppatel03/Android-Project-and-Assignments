package com.mylab6application;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AboutMeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private  static final String ARGS_OPTION = "argument_option";




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * param ARGS_OPTION option 1.
     * @return A new instance of fragment AboutMeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(int option){
        AboutMeFragment fragment = new AboutMeFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_OPTION, option);
        fragment.setArguments(args);
        return fragment;
    }
    public AboutMeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View aboutMeView = inflater.inflate(R.layout.fragment_about_me, container,false);
        return aboutMeView;
    }



}
