package com.findmybusapplication.aysnctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.findmybusapplication.constants.GenericConstants;
import com.findmybusapplication.helper.GenericHelper;

/**
 * Created by prashant on 3/27/2015.
 */
public class MyUpdateWaitForMeDetailAsyncTask extends AsyncTask<String,Integer,String> {

    private  final ProgressBar progressBar;
    private final TextView textView;

    private  final Context context;


    public MyUpdateWaitForMeDetailAsyncTask(ProgressBar progressBar, Context context, TextView textView) {
        this.progressBar =(progressBar);
        this.context = (context);
        this.textView = textView;
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
    protected String doInBackground(String... urls) {
        SystemClock.sleep(1000);
        publishProgress(10);
        SystemClock.sleep(1000);
        publishProgress(30);
        SystemClock.sleep(1000);
        publishProgress(40);
        SystemClock.sleep(1000);
        publishProgress(70);

        String outputString = "";
        for(String url : urls){
            Log.i("","Url passed for MyDownloadMoviesDetailTask is :"+url);
            outputString = GenericHelper.getOutputFromUrl(url);
        }
        SystemClock.sleep(1000);
        publishProgress(100);
        return outputString;
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param result The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(String result) {
        if(GenericConstants.SUCCESS_STATUS.equalsIgnoreCase(result)){

            textView.setText("Your Wait Request Saved Successfully");
            SystemClock.sleep(1000);
            publishProgress(100);

        }
        else{
            textView.setText("Upload Error");

        }
    }

    @Override
    protected void onProgressUpdate(final Integer... values) {
        textView.setText("Progress : "+Integer.toString(values[0])+"%");
        progressBar.setProgress(values[0]);
    }
}
