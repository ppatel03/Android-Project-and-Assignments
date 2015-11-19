package com.mylab9application.foldable;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.mylab9application.R;
import com.mylab9application.recyclerview.FragmentDetailView;

import java.util.HashMap;

public class FoldableLayoutActivity extends ActionBarActivity implements
        FoldableLayoutFragment.OnListItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foldable_layout);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.foldableLayoutActivity, FoldableLayoutFragment.newInstance(0))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_foldable_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemSelected(int position, HashMap<String, ?> movie) {
        {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.foldableLayoutActivity, FragmentDetailView.newInstance(movie))
                    .addToBackStack(null)
                    .commit();

        }
    }




}
