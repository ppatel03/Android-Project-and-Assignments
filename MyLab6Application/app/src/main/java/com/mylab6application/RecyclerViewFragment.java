package com.mylab6application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;

import java.util.HashMap;

/**
 * Created by prashant on 2/20/2015.
 */
public class RecyclerViewFragment extends Fragment {

    private static final String ARGS_OPTION = "argument_option";
    private OnListItemSelectedListener mListener;
    private RecyclerView recyclerView = null;
    private MovieDataJson movieDataJson;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    public interface OnListItemSelectedListener {
        public void onListItemSelected(int position, HashMap<String, ?> movie);
    }

    public static Fragment newInstance(int option) {
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
        //View aboutMeView = inflater.inflate(R.layout.fragment_master_data_flow, container,false);
        setRetainInstance(true);
        Intent intent = getActivity().getIntent();
        MovieDataJson movieData = (MovieDataJson) intent.getSerializableExtra("movie");
        MyRecyclerAdapterProxy recyclerViewAdapterProxy = new MyRecyclerAdapterProxy(getActivity(), movieData, mListener);

        // to load which view
        int option = getArguments().getInt(ARGS_OPTION);
        View rootView = recyclerViewAdapterProxy.getViewUsingBaseAdapter(inflater, container,option);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        movieDataJson = movieData;
        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu.findItem(R.id.action_search) == null) {
            inflater.inflate(R.menu.menu_actionview, menu);
        }

        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if (search != null) {
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    int position = movieDataJson.findFirst(query);
                    if (position >= 0) {
                        recyclerView.scrollToPosition(position);
                    }
                    return true;
                }


                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Activity activity) {
        Log.i("RecyclerViewFragment ", " inside onAttach");
        super.onAttach(activity);
        try {
            mListener = (OnListItemSelectedListener) activity;
            Log.i("RecyclerViewFragment ", " mListener is " + (mListener == null));

        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }


}
