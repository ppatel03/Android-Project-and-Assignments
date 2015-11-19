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

public class WaitForMeRequestActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_me_request);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, WaitForMeRequestFragment.newInstance(2))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wait_for_me_request, menu);
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
    public static class WaitForMeRequestFragment extends Fragment {

        public WaitForMeRequestFragment() {
        }

        public static Fragment newInstance( int type){
            WaitForMeRequestFragment fragment = new WaitForMeRequestFragment();
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
            View rootView = inflater.inflate(R.layout.fragment_wait_for_me_request, container, false);
            GPSDataHandler gpsDataHandler = new GPSDataHandler(getActivity(),busData);
            rootView = gpsDataHandler.getViewAndSetWaitForMeLocation(inflater,container,rootView);
            return rootView;
        }
    }
}
