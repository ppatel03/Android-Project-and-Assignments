package com.findmybusapplication.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.findmybusapplication.R;
import com.findmybusapplication.adapter.MyFragmentPagerAdapter;
import com.findmybusapplication.bean.Bus;
import com.findmybusapplication.bean.BusForLocation;
import com.findmybusapplication.constants.ArgumentConstants;
import com.findmybusapplication.data.BusData;
import com.findmybusapplication.fragments.ViewPagerFragment;
import com.findmybusapplication.jsonhandler.JsonBusDataParser;

import java.util.List;
import java.util.Map;

public class ViewPagerActivity extends ActionBarActivity implements ViewPagerFragment.OnViewPagerButtonClickListener{
    MyFragmentPagerAdapter myPagerAdapter;
    ViewPager viewPager;
    BusData busData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        busData = new BusData();
        busData.generateBusData(getApplicationContext());

        FragmentManager fragmentManager = getFragmentManager();

        myPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),busData);

        viewPager = (ViewPager) findViewById(R.id.viewPagerActivity);

        viewPager.setAdapter(myPagerAdapter);

        setCustomAnimationListeners();

        viewPager.setCurrentItem(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_pager, menu);
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

    private void setCustomAnimationListeners(){
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer(){
            @Override
            public void transformPage(View page, float position){
                final float nomralizedPosition = Math.abs(Math.abs(position) - 1);
                page.setScaleX(nomralizedPosition/2 + 0.5f);
                page.setScaleY(nomralizedPosition/2 + 0.5f);

            }
        });
    }


    @Override
    public void OnViewPagerButtonClick(int id, BusData data) {
        Intent intent = null;
        myPagerAdapter.setBusData(data);
        switch (id){
            case R.id.searchBusImage :
                viewPager.setCurrentItem(1);
                break;
            case R.id.waitRequestImage :
                viewPager.setCurrentItem(2);
                break;
            case R.id.savedSearchImage:
                viewPager.setCurrentItem(3);
                break;
            case R.id.shareLocationImage:
                intent = new Intent(this,BusShareLocationActivity.class);
                intent.putExtra(ArgumentConstants.ARGS_BUS_DATA,data);
                startActivity(intent);
                break;
            case R.id.trackLocationImage:
                intent = new Intent(this,TrackYourBusDataActivity.class);
                intent.putExtra(ArgumentConstants.ARGS_BUS_DATA,data);
                startActivity(intent);
                break;
            case R.id.waitForMeImage:
                intent = new Intent(this,WaitForMeRequestActivity.class);
                intent.putExtra(ArgumentConstants.ARGS_BUS_DATA,data);
                startActivity(intent);
                break;
            case R.id.submitBusSearchImage:
                intent = new Intent(this,RecyclerViewActivityForBusSearchResults.class);
                intent.putExtra(ArgumentConstants.ARGS_BUS_DATA,data);
                startActivity(intent);
                Log.i("","Opening the Reycler Activity for Bus Search Results");
                break;

        }
    }
}