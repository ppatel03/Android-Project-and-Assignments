package com.mylab9application;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mylab9application.aboutme.AboutMeFragment;
import com.mylab9application.foldable.FoldableLayoutActivity;
import com.mylab9application.movie.MovieDataJson;
import com.mylab9application.recyclerview.RecyclerViewActivity;


public class MainActivity extends ActionBarActivity implements CoverPageFragment.OnButtonItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, CoverPageFragment.newInstance(0))
                    .commit();
        }
        Log.i("","insider MainActivity");

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onButtonItemClickListener(View v) {
        Log.i("inside Overridden Method ", "inside Overridden Method ");

        int id = v.getId();

        MovieDataJson movie = null;
        Intent intent = null;
        switch (id){
            case R.id.aboutMeFragmentButton:
                Log.i("Loading About me Fragment ","Loading About me Fragment ");

                getSupportFragmentManager().beginTransaction()
                     //   .setCustomAnimations(R.animator.slide_in_from_left,R.animator.slide_out_from_bottom)
                        .replace(R.id.container, AboutMeFragment.newInstance(1))
                        .addToBackStack(null)
                        .commit();
                break;


            case R.id.recyclerViewActivityButton:
                movie = new MovieDataJson();
                //invoking another activity
                intent = new Intent(this,RecyclerViewActivity.class);
                intent.putExtra("movie",movie);
                startActivity(intent);
                break;

            case R.id.foldableLayoutActivityButton:
                movie = new MovieDataJson();
                //invoking another activity
                intent = new Intent(this,FoldableLayoutActivity.class);
                intent.putExtra("movie",movie);
                startActivity(intent);
                break;

        }

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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
