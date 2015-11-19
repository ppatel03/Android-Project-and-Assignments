package com.mylab6application;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Toast;

import java.util.HashMap;


public class FragmentDetailViewActivity extends ActionBarActivity {
    private static final String MASTER_DATA = "master_data";
    private Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_detail_view);

        //replacing actionbar as toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

       toolbar.inflateMenu(R.menu.menu_toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.recycle_icon_toolbar:
                        Toast.makeText(getApplicationContext(), "Clicked on Recycler Bin", Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.action_search_toolbar:
                        Toast.makeText(getApplicationContext(), "Clicked on Search Icon", Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.action_settings_toolbar:
                        Toast.makeText(getApplicationContext(), "Clicked on Settings", Toast.LENGTH_LONG).show();
                        return true;
                    default:
                        break;

                }

                return false;
            }
        });


        if (savedInstanceState == null) {
            Intent intent = getIntent();
            HashMap<String, ?> movieMap = (HashMap<String, ?>) intent.getSerializableExtra("moviemap");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_detail_activity_frame, FragmentDetailView.newInstance(movieMap, MASTER_DATA))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Clicked on Settings", Toast.LENGTH_LONG).show();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.recycle_icon) {
            Toast.makeText(getApplicationContext(), "Clicked on Recycler Bin", Toast.LENGTH_LONG).show();
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_fragment_detail_view, container, false);
            return rootView;
        }
    }
}
