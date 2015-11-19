package com.example.kevin.teachgraphicsanimation;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;


public class Activity_Animation extends ActionBarActivity {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_from_left, 0)
                .replace(R.id.container, Fragment_Animation.newInstance(0))
                .commit();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.animation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_from_left:
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_in_from_left, 0)
                        .replace(R.id.container, Fragment_Animation.newInstance(0))
                        .commit();
                break;
            case R.id.action_in_and_out:
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_in_from_left, R.animator.slide_out_to_bottom)
                        .replace(R.id.container, Fragment_Animation.newInstance(1))
                        .commit();
                break;
            case R.id.action_combined:
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.combined, 0)
                        .replace(R.id.container, Fragment_Animation.newInstance(2))
                        .commit();
                break;
            case R.id.action_others:
            default:
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                                R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                        .replace(R.id.container, Fragment_Animation.newInstance(3))
                        .commit();
                break;

        }
        return true;

    }

}
