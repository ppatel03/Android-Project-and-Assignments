package com.mylab8application.asynctasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.mylab8application.utility.MyUtility;
import java.lang.ref.WeakReference;

//static import. Java is awesome
import static com.mylab8application.utility.CacheHelper.mImgMemoryCache;

/**
 * Created by prashant on 3/27/2015.
 */
public class MyDownloadMovieImageTask extends AsyncTask<String,Void,Bitmap>{
    private final WeakReference<ImageView> imageViewWeakReference;

    public MyDownloadMovieImageTask(ImageView imageView) {
        this.imageViewWeakReference = new WeakReference<ImageView>(imageView);
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
    protected Bitmap doInBackground(String... urls) {
        Bitmap bitmap = null;

        for(String url : urls){
            bitmap = MyUtility.downloadImage(url);
            if(bitmap != null){
               mImgMemoryCache.put(url,bitmap);
            }
        }

        return bitmap;
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param bitmap The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(imageViewWeakReference != null && bitmap != null ){
            final ImageView imageView = imageViewWeakReference.get();

            if(imageView != null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }


}
