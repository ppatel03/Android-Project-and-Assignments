package com.findmybusapplication.activities;

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
import com.findmybusapplication.bean.Bus;
import com.findmybusapplication.fragments.BusFragmentDetailView;
import com.findmybusapplication.fragments.RecyclerViewFragmentForBusSearchResults;

import java.util.HashMap;

public class RecyclerViewActivityForBusSearchResults extends ActionBarActivity implements
        RecyclerViewFragmentForBusSearchResults.OnListItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_activity_for_bus_search_results);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recyclerViewActivityForBusSearchResults, RecyclerViewFragmentForBusSearchResults.newInstance(0))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recycler_view_activity_for_bus_search_results, menu);
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

    @Override
    public void onListItemSelected(int position, Bus bus) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recyclerViewActivityForBusSearchResults, BusFragmentDetailView.newInstance(bus))
                .addToBackStack(null)
                .commit();

    }


}
