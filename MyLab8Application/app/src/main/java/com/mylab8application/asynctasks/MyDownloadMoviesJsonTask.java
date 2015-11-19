package com.mylab8application.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.mylab8application.adapter.MyRecyclerAdapter;
import com.mylab8application.movie.MovieDataJson;

import java.lang.ref.WeakReference;

/**
 * Created by prashant on 3/27/2015.
 */
public class MyDownloadMoviesJsonTask extends AsyncTask<String,Void,MovieDataJson> {
    private final WeakReference<MyRecyclerAdapter> adapterWeakReference;
    private MovieDataJson movieData;

    public MyDownloadMoviesJsonTask(MyRecyclerAdapter recyclerAdapter, MovieDataJson movieData){
        adapterWeakReference = new WeakReference<MyRecyclerAdapter>(recyclerAdapter);
        this.movieData = movieData;
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
    protected MovieDataJson doInBackground(String... urls) {
        Log.i("Into thread background : MyDownloadMoviesJsonTask", "Into thread background : MyDownloadMoviesJsonTask");
        MovieDataJson threadMovieDataJson = new MovieDataJson();
        for(String url : urls){
            threadMovieDataJson.loadMoviesFromJsonUrl(url);
        }
        return threadMovieDataJson;
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param threadMovieDataJson The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(MovieDataJson threadMovieDataJson) {
        movieData.getMoviesList().clear();
        for(int i = 0; i < threadMovieDataJson.getSize(); i++){
            movieData.getMoviesList().add(threadMovieDataJson.getMoviesList().get(i));
        }

        if(adapterWeakReference != null){
            final MyRecyclerAdapter adapter = adapterWeakReference.get();
            if(adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
    }
}
