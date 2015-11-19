package com.findmybusapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.findmybusapplication.bean.Bus;
import com.findmybusapplication.bean.BusForLocation;
import com.findmybusapplication.constants.MenuConstants;
import com.findmybusapplication.data.BusData;
import com.findmybusapplication.fragments.ViewPagerFragment;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by prashant on 2/20/2015.
 */
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private BusData busData;

    public BusData getBusData() {
        return busData;
    }

    public void setBusData(BusData busData) {
        this.busData = busData;
    }

    public MyFragmentPagerAdapter( FragmentManager fragmentManager,BusData busData){
        super(fragmentManager);
        this.busData =  busData;
    }

    @Override
    public Fragment getItem(int position) {

        return ViewPagerFragment.newInstance(position,this.busData);
    }

    @Override
    public int getCount() {
        return MenuConstants.MENU_LENGTH;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        Locale loc = Locale.getDefault();
        String name = "";

        switch (position) {
            case 0:
                name = MenuConstants.MENU_HOME;
                break;
            case 1:
                name = MenuConstants.SEARCH_BUSES;
                break;
            case 2:
                name = MenuConstants.VIEW_WAIT_REQUESTS;
                break;
            case 3 :
                name = MenuConstants.SAVED_SEARCHES;
                break;
            default :
                name = MenuConstants.MENU_HOME;
        }

        return name.toUpperCase(loc);
    }
}
