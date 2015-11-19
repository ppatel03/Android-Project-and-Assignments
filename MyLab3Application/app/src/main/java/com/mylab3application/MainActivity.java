package com.mylab3application;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends ActionBarActivity {
    private  static final String ARGS_OPTION = "argument_option";


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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;

            int option = getArguments().getInt(ARGS_OPTION);

            MovieData movieData = new MovieData();

            switch (option){
                case R.id.about_me :
                    rootView = inflater.inflate(R.layout.fragment_about_me, container,false);
                    break;
                case R.id.movieListViewMenu:

                    ViewAdapterProxy listViewAdapterProxy = new ViewAdapterProxy(getActivity(),
                            new MyBaseAdapter(getActivity(),movieData.getMoviesList()));

                    rootView =  listViewAdapterProxy.getViewUsingBaseAdapter(inflater, container, R.id.movieListView);
                    break;

                case R.id.movieGridViewMenu:
                    ViewAdapterProxy gridViewAdapterProxy = new ViewAdapterProxy(getActivity(),
                            new MyGridBaseAdapter(getActivity(), movieData.getMoviesList()));

                    rootView =  gridViewAdapterProxy.getViewUsingBaseAdapter(inflater, container, R.id.movieGridView);
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
