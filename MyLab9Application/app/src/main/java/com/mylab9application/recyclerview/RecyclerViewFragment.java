package com.mylab9application.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylab9application.R;
import com.mylab9application.adapter.MyRecyclerAdapter;
import com.mylab9application.asynctasks.MyDownloadMoviesJsonTask;
import com.mylab9application.movie.MovieDataJson;
import com.mylab9application.proxy.ViewAdapterProxy;

import java.util.HashMap;

/**
 * Created by prashant on 2/20/2015.
 */
public class RecyclerViewFragment extends Fragment {

    private  static final String ARGS_OPTION = "argument_option";
    private OnListItemSelectedListener mListener;
    private OnMenuQueryTextListener queryTextListener;




    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    public interface OnListItemSelectedListener{
        public void onListItemSelected(int position, HashMap<String, ?> movie);
    }

    public interface OnMenuQueryTextListener{
        public void onMenuQueryTextEntered(String query);
    }

    public void setOnMenuQueryTextListener(final OnMenuQueryTextListener queryTextListener){
        this.queryTextListener = queryTextListener;
    }

    public static Fragment newInstance(int option){
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_OPTION, option);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Called to do initial creation of a fragment.  This is called after
     * {@link #onAttach(android.app.Activity)} and before
     * {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}.
     * <p/>
     * <p>Note that this can be called while the fragment's activity is
     * still in the process of being created.  As such, you can not rely
     * on things like the activity's content view hierarchy being initialized
     * at this point.  If you want to do work once the activity itself is
     * created, see {@link #onActivityCreated(android.os.Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View aboutMeView = inflater.inflate(R.layout.fragment_recycler_view, container,false);
        setRetainInstance(true);
        Intent intent = getActivity().getIntent();
        MovieDataJson movieData = (MovieDataJson)intent.getSerializableExtra("movie");
        ViewAdapterProxy recyclerViewAdapterProxy = new ViewAdapterProxy(getActivity(),movieData,mListener,queryTextListener);
        View rootView =  recyclerViewAdapterProxy.getViewUsingBaseAdapter(inflater, container);
        this.setOnMenuQueryTextListener(recyclerViewAdapterProxy);
        return rootView;
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called {@link #setHasOptionsMenu}.  See
     * {@link android.app.Activity#onCreateOptionsMenu(android.view.Menu) Activity.onCreateOptionsMenu}
     * for more information.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater
     * @see #setHasOptionsMenu
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        {
            if (menu.findItem(R.id.action_search) == null) {
                inflater.inflate(R.menu.menu_actionview, menu);
            }

            SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();

            if (search != null) {
                search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Log.i("Your search query",query);
                        //creating the asyn task
                        queryTextListener.onMenuQueryTextEntered(query);
                        return true;
                    }


                    @Override
                    public boolean onQueryTextChange(String newText) {
                        Log.i("onQuryTxtChnge: You submitted query on search box","--"+newText+"--");
                        queryTextListener.onMenuQueryTextEntered(newText);
                        return true;
                    }
                });
            }
            super.onCreateOptionsMenu(menu, inflater);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mListener = (OnListItemSelectedListener) activity;

        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }


}
