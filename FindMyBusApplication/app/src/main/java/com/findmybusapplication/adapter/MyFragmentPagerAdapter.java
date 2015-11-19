package com.findmybusapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.findmybusapplication.fragments.FragmentDetailView;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by prashant on 2/20/2015.
 */
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    public static final String MENU_HOME = "Home";
    public static final String SEARCH_BUSES = "Search Buses";
    public static final String NEAREST_BUS_STOP = "Search Bus Stop";
    public static final String SAVED_SEARCHES = "Saved Searches";
    public static final String SHARE_BUS_LOCATION = "Share Your Bus Location";
    private  static final String VIEW_PAGER = "view_pager";

    int movieSize;


    public MyFragmentPagerAdapter( FragmentManager fragmentManager, int size){
        super(fragmentManager);
        movieSize = size;

    }

    @Override
    public Fragment getItem(int position) {
        return FragmentDetailView.newInstance(position);
    }

    @Override
    public int getCount() {
        return movieSize;
    }

    @Override
    public CharSequence getPageTitle(int position) {
       /* Locale loc = Locale.getDefault();
        HashMap<String,?> movieMap = (HashMap<String,?>)movieData.getItem(position);
        String name =(String) movieMap.get("name");
        return name.toUpperCase(loc);*/


        Locale loc = Locale.getDefault();
        String name = "zdf";

        switch (position) {
            case 0:
                name = MENU_HOME;
                break;
            case 1:
                name = SEARCH_BUSES;
                break;
            case 2:
                name = NEAREST_BUS_STOP;
                break;
            case 3 :
                name = SAVED_SEARCHES;
                break;
            case 4 :
                name = SHARE_BUS_LOCATION;
                break;
            default :
                name = MENU_HOME;
        }

        return name.toUpperCase(loc);
    }
}
