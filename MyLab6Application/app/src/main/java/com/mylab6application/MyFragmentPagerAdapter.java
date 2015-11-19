package com.mylab6application;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by prashant on 2/20/2015.
 */
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static final String VIEW_PAGER = "view_pager";

    int movieSize;
    MovieDataJson movieData;

    public MyFragmentPagerAdapter(MovieDataJson movieData, FragmentManager fragmentManager, int size) {
        super(fragmentManager);
        movieSize = size;
        this.movieData = movieData;
    }

    @Override
    public Fragment getItem(int position) {
        //return ViewPagerFragment.newInstance((HashMap<String, ?>) movieData.getItem(position), VIEW_PAGER);
        return RecyclerViewFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale loc = Locale.getDefault();
        String name;

        switch (position) {
            case 0:
                name = "Top Selling Movies";
                break;
            case 1:
                name = "New Movie Releases";
                break;
            default:
                name = "Top Selling Movies";
        }

        return name.toUpperCase(loc);
    }
}
