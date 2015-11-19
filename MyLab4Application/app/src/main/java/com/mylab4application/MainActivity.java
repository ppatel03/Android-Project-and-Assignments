package com.mylab4application;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;


public class MainActivity extends ActionBarActivity {
    private  static final String ARGS_OPTION = "argument_option";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //to retain the view actions like checbox selection, deletion of items etc. WHEN THE ORIENTATION IS CHANGED
        //movieData = new MovieData();
       // setRetainInstance(true);

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
        private MovieData movieData;

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
            movieData = new MovieData();
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;

            setRetainInstance(true);
            int option = getArguments().getInt(ARGS_OPTION);


            switch (option){
                case R.id.about_me :
                    rootView = inflater.inflate(R.layout.fragment_about_me, container,false);
                    break;

                case R.id.recycler_view:
                    ViewAdapterProxy recyclerViewAdapterProxy = new ViewAdapterProxy(getActivity(),movieData);
                    rootView =  recyclerViewAdapterProxy.getViewUsingBaseAdapter(inflater, container);


                    break;

                default:
                    rootView = inflater.inflate(R.layout.fragment_about_me, container,false);

            }

            return rootView;
        }

        public static Fragment newInstance(int option){
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARGS_OPTION, option);
            fragment.setArguments(args);
            return fragment;
        }
    }
}
