package com.findmybusapplication.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.findmybusapplication.R;
import com.findmybusapplication.adapter.MyFragmentPagerAdapter;


public class ViewPagerActivity extends ActionBarActivity {

    MyFragmentPagerAdapter myPagerAdapter;
    ViewPager viewPager;
    private  static final String VIEW_PAGER = "view_pager";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);


        FragmentManager fragmentManager = getFragmentManager();

        myPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),5);

        viewPager = (ViewPager) findViewById(R.id.viewPagerActivity);

        viewPager.setAdapter(myPagerAdapter);

        setCustomAnimationListeners();

        viewPager.setCurrentItem(3);

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


}
