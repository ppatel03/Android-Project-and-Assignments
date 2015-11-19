package com.mylab6application;


import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import java.util.HashMap;


public class ViewPagerActivity extends ActionBarActivity implements RecyclerViewFragment.OnListItemSelectedListener{
    MyFragmentPagerAdapter myPagerAdapter;
    ViewPager viewPager;
    MovieDataJson movieData;
    private  static final String VIEW_PAGER = "view_pager";
    private  static final String MASTER_DATA = "master_data";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        try{
            movieData = new MovieDataJson(getApplicationContext());
        }
        catch (Exception e){
            Log.e("ViewPagerActivity : ", e.toString());
        }

        myPagerAdapter = new MyFragmentPagerAdapter(movieData,getSupportFragmentManager(),movieData.getSize());

        viewPager = (ViewPager) findViewById(R.id.viewPagerActivity);

        viewPager.setAdapter(myPagerAdapter);

        setCustomAnimationListeners();

        viewPager.setCurrentItem(0);

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


    @Override
    public void onListItemSelected(int position, HashMap<String, ?> movie) {
        Intent intent = new Intent(this, FragmentDetailViewActivity.class);
        intent.putExtra("moviemap", movie);
        startActivity(intent);

    }
}
