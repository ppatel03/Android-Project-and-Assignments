package com.mylab6application;


import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prashant on 2/5/2015.
 */
public class MyRecyclerAdapterProxy implements MyRecyclerAdapter.OnItemClickListener {
    final FragmentActivity fragmentActivity;
    MyRecyclerAdapter myRecyclerAdapter;
    MovieDataJson movieData;
    RecyclerViewFragment.OnListItemSelectedListener mListener;

    MyRecyclerAdapterProxy(FragmentActivity fragmentActivity, MovieDataJson movieData,
                           RecyclerViewFragment.OnListItemSelectedListener mListener) {
        this.fragmentActivity = fragmentActivity;
        this.movieData = movieData;
        this.mListener = mListener;
    }


    public View getViewUsingBaseAdapter(LayoutInflater inflater, ViewGroup container, int option) {

        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = null;
        GridLayoutManager gridLayoutManager = null;
        switch(option){
            case 0 :
                linearLayoutManager = new LinearLayoutManager(this.fragmentActivity);
                recyclerView.setLayoutManager(linearLayoutManager);
                myRecyclerAdapter = new MyRecyclerAdapter(fragmentActivity, movieData.getMoviesList(),0);
                break;
            case 1:
                gridLayoutManager = new GridLayoutManager(this.fragmentActivity,4);
                recyclerView.setLayoutManager(gridLayoutManager);
                myRecyclerAdapter = new MyRecyclerAdapter(fragmentActivity, movieData.getMoviesList(),1);
                break;

            default:
                linearLayoutManager = new LinearLayoutManager(this.fragmentActivity);
                recyclerView.setLayoutManager(linearLayoutManager);
                myRecyclerAdapter = new MyRecyclerAdapter(fragmentActivity, movieData.getMoviesList(),4);
        }

        recyclerView.setAdapter(myRecyclerAdapter);
        Log.i("List adapter Attached", "List adapter Attached" + R.id.recyclerView);
        myRecyclerAdapter.setOnItemClickListener(this);
        return rootView;
    }


    @Override
    public void onMyRecyclerItemClick(View view, int position) {
        Log.i("insider ItemClick", "insider ItemClick");
        //do not know why it is final
        final HashMap<String, ?> movieMap = (HashMap<String, ?>) myRecyclerAdapter.getItemAtPosition(position);
        mListener.onListItemSelected(position, movieMap);

    }

    @Override
    public void onMyRecyclerItemLongClick(View view, int position) {
        fragmentActivity.startActionMode(new ActionBarCallBack(position, movieData, myRecyclerAdapter));
    }

    @Override
    public void onOverFlowButtonClick(View view, final int position) {
        PopupMenu popup = new PopupMenu(fragmentActivity, view);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                List<Map<String, ?>> movieList = movieData.getMoviesList();
                switch (id) {
                    case R.id.item_delete:
                        movieList.remove(position);
                        myRecyclerAdapter.notifyItemRemoved(position);
                        return true;
                    case R.id.item_duplicate:
                        Map<String, ?> movieMap = movieList.get(position);
                        movieList.add(position + 1, movieMap);
                        Log.i("Clicked on Duplicate . Position is ",""+position+1);
                        myRecyclerAdapter.notifyItemInserted(position+1);
                        return true;
                    default:
                        return false;
                }
            }
        });
        inflater.inflate(R.menu.menu_context_bar, popup.getMenu());
        popup.show();
    }


}
