package com.myfirstlabapplication;

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


public class MainActivity extends ActionBarActivity {

    // Overriding the default activity onCreate Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // most of the customization occurs here.
        setContentView(R.layout.activity_main); // R.layout.activity_main comes from the layout file
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    //creates a new fragment and adds it to the main layout using R.id.container
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    // adds the menu from R java class pointing to menu_main. Menu is basically the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//R.menu.menu_main means it points to res/menu/menu_main.xml
        return true;
    }

    //Do various tasks on selecting the options in the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_selection1) {
            return true;
        }
        else if(id == R.id.action_selection2){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        /**
         *
         * @param inflater
         * @param container
         * @param savedInstanceState
         * @return
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Loads the layout in res/layout/fragment_main.xml (inflating the view)
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            return rootView;
        }
    }
}
