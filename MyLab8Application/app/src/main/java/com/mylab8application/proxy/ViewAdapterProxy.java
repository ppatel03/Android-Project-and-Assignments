package com.mylab8application.proxy;



import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylab8application.R;
import com.mylab8application.adapter.MyRecyclerAdapter;
import com.mylab8application.asynctasks.MyDownloadMoviesDetailTask;
import com.mylab8application.asynctasks.MyDownloadMoviesJsonTask;
import com.mylab8application.movie.MovieDataJson;
import com.mylab8application.recyclerview.RecyclerViewFragment;
import com.mylab8application.utility.CacheHelper;

import java.util.HashMap;

/**
 * Created by prashant on 2/5/2015.
 */
public class ViewAdapterProxy implements MyRecyclerAdapter.OnItemClickListener{
     final FragmentActivity fragmentActivity;
     MyRecyclerAdapter myRecyclerAdapter;
     MovieDataJson movieData;
     RecyclerViewFragment.OnListItemSelectedListener mListener;

    public ViewAdapterProxy(FragmentActivity fragmentActivity, MovieDataJson movieData,
                     RecyclerViewFragment.OnListItemSelectedListener mListener){
        this.fragmentActivity = fragmentActivity;
        this.movieData = movieData;
        this.mListener = mListener;
    }


    public View getViewUsingBaseAdapter( LayoutInflater inflater, ViewGroup container){

        View rootView = null;
            rootView = inflater.inflate(R.layout.fragment_recycler_view,container,false);
            RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.fragmentActivity);
            recyclerView.setLayoutManager(linearLayoutManager);

            //creating image cache for saving images from the internet
            CacheHelper.createCacheForImage();

            myRecyclerAdapter = new MyRecyclerAdapter(fragmentActivity,movieData.getMoviesList());
            Log.i("Calling Aysnc Task : MyDownloadMoviesJsonTask", "Calling Aysnc Task : MyDownloadMoviesJsonTask");

            //creating the asyn task
            MyDownloadMoviesJsonTask myDownloadMoviesJsonTask = new MyDownloadMoviesJsonTask(myRecyclerAdapter,this.movieData);
            String path = MovieDataJson.PATH_FOR_ALL_MOVIES_DATA;
            myDownloadMoviesJsonTask.execute(new String[]{path});

            recyclerView.setAdapter(myRecyclerAdapter);
            Log.i("List adapter Attached", "List adapter Attached" + R.id.recyclerView);
            myRecyclerAdapter.setOnItemClickListener(this);



            return rootView;
    }





    @Override
    public void onMyRecyclerItemClick(View view, int position) {
        Log.i("insider ItemClick-------","insider ItemClick");
        //do not know why it is final
        final HashMap<String, ?> movieMap = (HashMap<String, ?>) myRecyclerAdapter.getItemAtPosition(position);
        String id = (String) movieMap.get("id");

        //use this id to get the details of the movie in the form of json
        String path = id+".json";
        MyDownloadMoviesDetailTask myDownloadMoviesDetailTask = new MyDownloadMoviesDetailTask(
                mListener,position);
        myDownloadMoviesDetailTask.execute(new String[]{path});
    }

    @Override
    public void onMyRecyclerItemLongClick(View view, int position) {

    }


}
