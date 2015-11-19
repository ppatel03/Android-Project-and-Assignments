package com.mylab6application;

import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prashant on 2/27/2015.
 */
public class ActionBarCallBack implements ActionMode.Callback {
    int position;
    MovieDataJson movieData;
    MyRecyclerAdapter myRecyclerAdapter;

    public ActionBarCallBack(int position, MovieDataJson movieData, MyRecyclerAdapter myRecyclerAdapter) {
        this.position = position;
        this.movieData = movieData;
        this.myRecyclerAdapter = myRecyclerAdapter;
    }

    /**
     * Called when action mode is first created. The menu supplied will be used to
     * generate action buttons for the action mode.
     *
     * @param mode ActionMode being created
     * @param menu Menu used to populate action buttons
     * @return true if the action mode should be created, false if entering this
     * mode should be aborted.
     */
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_context_bar,menu);
        return true;
    }

    /**
     * Called to refresh an action mode's action menu whenever it is invalidated.
     *
     * @param mode ActionMode being prepared
     * @param menu Menu used to populate action buttons
     * @return true if the menu or action mode was updated, false otherwise.
     */
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        Map<String,?> movieMap = movieData.getItem(position);
        mode.setTitle((String)movieMap.get("name"));
        return false;
    }

    /**
     * Called to report a user click on an action button.
     *
     * @param mode The current ActionMode
     * @param item The item that was clicked
     * @return true if this callback handled the event, false if the standard MenuItem
     * invocation should continue.
     */
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        int id = item.getItemId();
        List<Map<String, ?>> movieList = movieData.getMoviesList();
        switch (id) {
            case R.id.item_delete:
                movieList.remove(position);
                myRecyclerAdapter.notifyItemRemoved(position);
                mode.finish();
                break;
            case R.id.item_duplicate:
                Map<String, ?> movieMap = movieList.get(position);
                Log.i("Item Duplicated position is ",""+position);
                movieList.add(position+1, movieMap);
                myRecyclerAdapter.notifyItemInserted(position+1);
                mode.finish();
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * Called when an action mode is about to be exited and destroyed.
     *
     * @param mode The current ActionMode being destroyed
     */
    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
