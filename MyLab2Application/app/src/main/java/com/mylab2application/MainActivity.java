package com.mylab2application;

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
import android.widget.ImageView;
import android.widget.SeekBar;


public class MainActivity extends ActionBarActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PlaceholderFragment.newInstance(1))
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

            getSupportFragmentManager().beginTransaction().
                        replace(R.id.container,PlaceholderFragment.newInstance(id)).
                        commit();





        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private  static final String ARGS_OPTION = "argument_option";

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;

            int option = getArguments().getInt(ARGS_OPTION);

            switch(option){

                case R.id.about_me :
                    rootView = inflater.inflate(R.layout.fragment_about_me, container, false);
                    break;

                case R.id.linear_layout :
                    rootView = inflater.inflate(R.layout.fragment_linear_layout, container, false);
                    break;

                case R.id.relative_layout :
                    rootView = inflater.inflate(R.layout.fragment_relative_layout, container, false);
                    break;

                case R.id.grid_layout :
                    rootView = inflater.inflate(R.layout.fragment_grid_layout, container, false);
                    break;

                case R.id.movie_data :
                    rootView = inflater.inflate(R.layout.fragment_movie_data, container, false);
                    MovieDataOperations movieDataOperations = new MovieDataOperations(rootView);
                    movieDataOperations.loadMovie();
                    break;

                case R.id.seekbar :
                    rootView = inflater.inflate(R.layout.fragment_seekbar, container, false);
                    SeekBarOperations seekBarOperations = new SeekBarOperations((SeekBar) rootView.findViewById(R.id.actionSeekBar),
                            (ImageView)rootView.findViewById(R.id.imageSeekBar));
                    seekBarOperations.attachListenersToSeekBar();
                    break;

                default: rootView = inflater.inflate(R.layout.fragment_about_me, container, false);
            }




            return rootView;

        }

        public static Fragment newInstance(int option) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARGS_OPTION,option);
            fragment.setArguments(args);
            return fragment;
        }
    }
}
