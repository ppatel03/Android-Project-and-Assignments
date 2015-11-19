package com.mylab8application.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.mylab8application.movie.MovieDataJson;
import com.mylab8application.recyclerview.RecyclerViewFragment;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by prashant on 3/27/2015.
 */
public class MyDownloadMoviesDetailTask extends AsyncTask<String,Void,HashMap> {

    private  final WeakReference<RecyclerViewFragment.OnListItemSelectedListener> onListItemSelectedListenerWeakReference;
    private final int position;

    public MyDownloadMoviesDetailTask(RecyclerViewFragment.OnListItemSelectedListener onListItemSelectedListenerReference,
                                      int position) {
        this.position = position;
        this.onListItemSelectedListenerWeakReference = new
                WeakReference<RecyclerViewFragment.OnListItemSelectedListener>(onListItemSelectedListenerReference);
    }


    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param urls The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected HashMap doInBackground(String... urls) {
        HashMap movieMap = new HashMap();
        MovieDataJson threadMovieDataJson = new MovieDataJson();
        for(String url : urls){
            Log.i("Url passed for MyDownloadMoviesDetailTask is :",url);
            movieMap = threadMovieDataJson.loadDetailMovieDataFromJsonUrl(url);
        }
        return movieMap;
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param movieMap The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(HashMap movieMap) {
        RecyclerViewFragment.OnListItemSelectedListener onListItemSelectedListenerReference =
                onListItemSelectedListenerWeakReference.get();
        onListItemSelectedListenerReference.onListItemSelected(position, movieMap);
    }
}
