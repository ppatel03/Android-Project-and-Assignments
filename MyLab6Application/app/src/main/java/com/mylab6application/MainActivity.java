package com.mylab6application;

import android.content.Intent;
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
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements CoverPageFragment.OnButtonItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, CoverPageFragment.newInstance(1))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.recycle_icon) {
            Toast.makeText(getApplicationContext(), "Clicked on Recycler Bin", Toast.LENGTH_LONG).show();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Clicked on Settings", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onButtonItemClickListener(View v) {

        Log.i("inside  ", "inside Overridden Method ");

        int id = v.getId();

        MovieDataJson movie = null;
        Intent intent = null;
        switch (id) {
            case R.id.aboutMeFragmentButton:
                Log.i("Loading ", "Loading About me Fragment ");

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, AboutMeFragment.newInstance(1))
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.viewPagerActivityButton:
                try {
                    movie = new MovieDataJson(getApplicationContext());
                    //invoking another activity
                    intent = new Intent(this, ViewPagerActivity.class);
                    intent.putExtra("movie", movie);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("MainActivity : ", e.toString());
                }

                break;

            case R.id.recyclerViewActivityButton:
                try {
                    movie = new MovieDataJson(getApplicationContext());
                    //invoking another activity
                    intent = new Intent(this, RecyclerViewActivity.class);
                    intent.putExtra("movie", movie);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("MainActivity : ", e.toString());
                }
                break;

        }

    }


}
