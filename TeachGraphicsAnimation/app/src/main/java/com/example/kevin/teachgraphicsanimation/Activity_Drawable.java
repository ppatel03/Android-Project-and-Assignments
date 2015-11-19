package com.example.kevin.teachgraphicsanimation;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class Activity_Drawable extends ActionBarActivity {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int position;

        int id = item.getItemId();
        switch (id){
            case R.id.action_drawable0:
                position = 0;
                break;
            case R.id.action_drawable1:
                position = 1;
                break;
            case R.id.action_drawable2:
                position = 2;
                break;
            case R.id.action_drawable3:
                position = 3;
                break;
            case R.id.action_drawable4:
                position = 4;
                break;
            case R.id.action_drawable5:
                position = 5;
                break;
            case R.id.action_drawable6:
                position = 6;
                break;
            case R.id.action_drawable7:
                position = 7;
                break;
            case R.id.action_drawable8:
                position = 8;
                break;
            case R.id.action_drawable9:
                position = 9;
                break;
            case R.id.action_drawable10:
                position = 10;
                break;
            case R.id.action_drawable11:
                position = 11;
                break;
            default:
                position = 0;
                break;
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.container, Fragment_Drawable.newInstance(position))
                .commit();

        return true;
    }

}
