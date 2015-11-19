package com.mylab9application.proxy;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.mylab9application.R;
import com.mylab9application.adapter.MyBaseAdapter;
import com.mylab9application.adapter.MyRecyclerAdapter;
import com.mylab9application.asynctasks.MyDownloadMoviesDetailTask;
import com.mylab9application.asynctasks.MyDownloadMoviesDetailTaskForFoldableLayout;
import com.mylab9application.asynctasks.MyDownloadMoviesJsonTask;
import com.mylab9application.foldable.FoldableLayoutActivity;
import com.mylab9application.foldable.FoldableLayoutFragment;
import com.mylab9application.listitem.ListItem;
import com.mylab9application.movie.MovieDataJson;
import com.mylab9application.recyclerview.RecyclerViewFragment;
import com.mylab9application.utility.CacheHelper;
import com.alexvasilkov.foldablelayout.UnfoldableView;




import java.util.HashMap;

/**
 * Created by prashant on 2/5/2015.
 */
public class ViewAdapterProxy implements
        MyRecyclerAdapter.OnItemClickListener,
        RecyclerViewFragment.OnMenuQueryTextListener,
        MyBaseAdapter.IFoldableItemListener,
        View.OnClickListener{

    final FragmentActivity fragmentActivity;
    MyBaseAdapter myBaseAdapter;
    MyRecyclerAdapter myRecyclerAdapter;
    ImageView upButton;

    MovieDataJson movieData;
    RecyclerViewFragment.OnListItemSelectedListener mListener;
    FoldableLayoutFragment.OnListItemSelectedListener fmListener;

    private View mListTouchInterceptor;
    private View mDetailsLayout;
    public UnfoldableView mUnfoldableView;

    RecyclerViewFragment.OnMenuQueryTextListener queryTextListener;

    public ViewAdapterProxy(FragmentActivity fragmentActivity, MovieDataJson movieData,
                            RecyclerViewFragment.OnListItemSelectedListener mListener,
                            RecyclerViewFragment.OnMenuQueryTextListener queryTextListener){
        this.fragmentActivity = fragmentActivity;
        this.movieData = movieData;
        this.mListener = mListener;
        this.queryTextListener = queryTextListener;
    }

    public ViewAdapterProxy(FragmentActivity fragmentActivity, MovieDataJson movieData,
                            FoldableLayoutFragment.OnListItemSelectedListener fmListener,
                            RecyclerViewFragment.OnMenuQueryTextListener queryTextListener){
        this.fragmentActivity = fragmentActivity;
        this.movieData = movieData;
        this.fmListener = fmListener;
        this.queryTextListener = queryTextListener;
    }

    public MyRecyclerAdapter getMyRecyclerAdapter(){
        return this.myRecyclerAdapter;
    }

    public MovieDataJson getMovieDataJson(){
        return this.movieData;
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

        myRecyclerAdapter = new MyRecyclerAdapter(fragmentActivity.getApplicationContext(),movieData.getMoviesList());
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

    public View getViewUsingBaseAdapterForFoldable( LayoutInflater inflater, ViewGroup container){

        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_foldable_layout,container,false);
        ListView listView = (ListView)rootView.findViewById(R.id.foldableRecyclerView);

        //creating image cache for saving images from the internet
        CacheHelper.createCacheForImage();

        myBaseAdapter = new MyBaseAdapter(fragmentActivity,movieData.getMoviesList());
        Log.i("Calling Aysnc Task : MyDownloadMoviesJsonTask", "Calling Aysnc Task : MyDownloadMoviesJsonTask");

        //creating the asyn task
        MyDownloadMoviesJsonTask myDownloadMoviesJsonTask = new MyDownloadMoviesJsonTask(myRecyclerAdapter,this.movieData);
        String path = MovieDataJson.PATH_FOR_ALL_MOVIES_DATA;
        myDownloadMoviesJsonTask.execute(new String[]{path});

        listView.setAdapter(myBaseAdapter);
        myBaseAdapter.setIFoldableItemListener(this);

        Log.i("List adapter Attached", "List adapter Attached" + R.id.recyclerView);
        //listView.setOnItemClickListener(this);

        mListTouchInterceptor = (View)rootView.findViewById(R.id.touch_interceptor_view);
        mListTouchInterceptor.setClickable(false);

        mDetailsLayout = rootView.findViewById( R.id.foldableDetailView);
        mDetailsLayout.setVisibility(View.INVISIBLE);

        upButton = (ImageView)rootView.findViewById(R.id.upButton);
        upButton.setOnClickListener(this);

        mUnfoldableView =(UnfoldableView)rootView.findViewById( R.id.unfoldable_view);

        Bitmap glance = BitmapFactory.decodeResource(fragmentActivity.getResources(), R.drawable.unfold_glance);
        mUnfoldableView.setFoldShading(new GlanceFoldShading(fragmentActivity.getApplicationContext(), glance));

        mUnfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
                mDetailsLayout.setVisibility(View.VISIBLE);

            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(false);
                mDetailsLayout.setVisibility(View.INVISIBLE);
            }
        });

        return rootView;
    }


/*
    @Override

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("insider ItemClick-------","insider ItemClick");
        //do not know why it is final
        final HashMap<String, ?> movieMap = (HashMap<String, ?>) myBaseAdapter.getItemAtPos(position);
        String jsonId = (String) movieMap.get("id");

        //use this id to get the details of the movie in the form of json
        String path = MovieDataJson.PATH_FOR_ALL_MOVIES_DATA+MovieDataJson.PATH_FOR_MOVIE_DETAIL+jsonId;


        mUnfoldableView.unfold((View)view,mDetailsLayout);
        /*
        MyDownloadMoviesDetailTaskForFoldableLayout myDownloadMoviesDetailTask = new MyDownloadMoviesDetailTaskForFoldableLayout(
                fmListener,position);
        myDownloadMoviesDetailTask.execute(new String[]{path});
        */
    // }

    @Override
    public void onMyRecyclerItemClick(View view, int position) {
        Log.i("","insider ItemClick");
        //do not know why it is final
        final HashMap<String, ?> movieMap = (HashMap<String, ?>) myRecyclerAdapter.getItemAtPosition(position);
        String id = (String) movieMap.get("id");

        //use this id to get the details of the movie in the form of json
        String path = MovieDataJson.PATH_FOR_ALL_MOVIES_DATA+MovieDataJson.PATH_FOR_MOVIE_DETAIL+id;
        MyDownloadMoviesDetailTask myDownloadMoviesDetailTask = new MyDownloadMoviesDetailTask(
                mListener,position);
        myDownloadMoviesDetailTask.execute(new String[]{path});


       /*
            mUnfoldableView.unfold(view,mDetailsLayout);
            MyDownloadMoviesDetailTaskForFoldableLayout myDownloadMoviesDetailTask = new MyDownloadMoviesDetailTaskForFoldableLayout(
                  fmListener,position);
            myDownloadMoviesDetailTask.execute(new String[]{path});
        */
    }

    @Override
    public void onMyRecyclerItemLongClick(View view, int position) {

    }


    @Override
    public void onMenuQueryTextEntered(String newText) {
        //creating the asyn task
        if(newText == null || "".equalsIgnoreCase(newText)){
            newText = "0.0";
        }
        Log.i("newText is ",newText);
        MyDownloadMoviesJsonTask myDownloadMoviesJsonTask = new MyDownloadMoviesJsonTask(
                myRecyclerAdapter,movieData);
        String path = MovieDataJson.PATH_FOR_ALL_MOVIES_DATA+
                MovieDataJson.PATH_FOR_RATING+newText;
        myDownloadMoviesJsonTask.execute(new String[]{path});
    }


    @Override
    public void onFoldableItemClick(View v, int position) {

        mUnfoldableView.unfold(v,mDetailsLayout);


    }


    @Override
    public void onClick(View v) {
        if (mUnfoldableView != null &&
                (mUnfoldableView.isUnfolded() ||
                        mUnfoldableView.isUnfolding())) {
            mUnfoldableView.foldBack();
        }
    }
}
