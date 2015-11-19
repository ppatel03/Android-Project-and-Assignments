package com.mylab8application.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylab8application.movie.MovieDataJson;
import com.mylab8application.proxy.ViewAdapterProxy;

import java.util.HashMap;

/**
 * Created by prashant on 2/20/2015.
 */
public class RecyclerViewFragment extends Fragment {

    private  static final String ARGS_OPTION = "argument_option";
    private OnListItemSelectedListener mListener;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    public interface OnListItemSelectedListener{
        public void onListItemSelected(int position, HashMap<String, ?> movie);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View aboutMeView = inflater.inflate(R.layout.fragment_recycler_view, container,false);
        setRetainInstance(true);
        Intent intent = getActivity().getIntent();
        MovieDataJson movieData = (MovieDataJson)intent.getSerializableExtra("movie");
        ViewAdapterProxy recyclerViewAdapterProxy = new ViewAdapterProxy(getActivity(),movieData,mListener);
        View rootView =  recyclerViewAdapterProxy.getViewUsingBaseAdapter(inflater, container);
        return rootView;
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
