package com.findmybusapplication.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.findmybusapplication.R;
import com.findmybusapplication.constants.ArgumentConstants;
import com.findmybusapplication.data.BusData;
import com.findmybusapplication.gpshandler.GPSDataHandler;

public class TrackYourBusDataActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_your_bus_data);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, BusTrackLocationFragment.newInstance(2))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_track_your_bus_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class BusTrackLocationFragment extends Fragment {

        public BusTrackLocationFragment() {
        }

        public static Fragment newInstance( int type){
            BusTrackLocationFragment fragment = new BusTrackLocationFragment();
            Bundle args = new Bundle();
            args.putInt(ArgumentConstants.ARGS_LAYOUT, type);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            setRetainInstance(true);
            int option = getArguments().getInt(ArgumentConstants.ARGS_LAYOUT);
            Intent intent = getActivity().getIntent();
            BusData busData = (BusData)intent.getSerializableExtra(ArgumentConstants.ARGS_BUS_DATA);
            View rootView = inflater.inflate(R.layout.fragment_track_your_bus_data, container, false);
            GPSDataHandler gpsDataHandler = new GPSDataHandler(getActivity(),busData);
            rootView = gpsDataHandler.getViewAndTrackBusGPSForShareLocation(inflater,container,rootView);
            return rootView;
        }
    }
}
