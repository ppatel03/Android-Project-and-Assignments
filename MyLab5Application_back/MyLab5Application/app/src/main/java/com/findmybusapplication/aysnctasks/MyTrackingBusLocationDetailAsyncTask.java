package com.findmybusapplication.aysnctasks;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.findmybusapplication.constants.GenericConstants;
import com.findmybusapplication.constants.GoogleMapConstants;
import com.findmybusapplication.helper.GenericHelper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by prashant on 3/27/2015.
 */
public class MyTrackingBusLocationDetailAsyncTask extends AsyncTask<String,Integer,String> {

    private  final ProgressBar progressBar;
    private final TextView textView;
    GoogleMap googleMap;
    FragmentActivity fragmentActivity;

    private  final Context context;


    public MyTrackingBusLocationDetailAsyncTask(ProgressBar progressBar, Context context,
                                                TextView textView,FragmentActivity fragmentActivity, GoogleMap googleMap) {
        this.progressBar =(progressBar);
        this.context = (context);
        this.textView = textView;
        this.fragmentActivity = fragmentActivity;
        this.googleMap = googleMap;
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
        publishProgress(20);
        SystemClock.sleep(1000);
        publishProgress(30);
        SystemClock.sleep(1000);
        publishProgress(40);
        SystemClock.sleep(1000);
        publishProgress(60);
        SystemClock.sleep(1000);
        publishProgress(70);

        String outputString = "";
        for(String url : urls){
            Log.i("","Url passed  is :"+url);
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

        class  MyLocationListener implements LocationListener {

            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {

                    Double latitude = location.getLatitude();
                    Double longitude = location.getLongitude();


                    if (googleMap != null) {
                        LatLng locationOnMap = new LatLng(latitude, longitude);
                        googleMap.addMarker(new MarkerOptions()
                                .position(locationOnMap)
                                .title("You are Here")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                       // CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(locationOnMap,
                          //      GoogleMapConstants.ZOOM_SCALE);
                        //googleMap.animateCamera(cameraUpdate);
                    }

                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        }

        try{
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            String latitudeString = (String)jsonObject.get("latitude");
            Double busLatitude = Double.parseDouble(latitudeString);
            String longitudeString = (String)jsonObject.get("longitude");
            Double busLongitude = Double.parseDouble(longitudeString);
            String username = (String)jsonObject.get("username");
            String busName = (String)jsonObject.get("busname");


            //setting the  Bus location
            LatLng busLocation = new LatLng(busLatitude,busLongitude);
            googleMap.addMarker(new MarkerOptions().position(busLocation)
                    .title(" Your " + busName + " is Bus is Here and Its location is provided by "+username)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(busLocation,
                    GoogleMapConstants.ZOOM_SCALE);
            googleMap.animateCamera(cameraUpdate);

            LocationManager locationManager = (LocationManager)fragmentActivity.getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new MyLocationListener();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0 ,0 , locationListener);



        }
        catch (Exception e){
            Log.e("onPostExecute"," Exception after retreiving JSON String", e);
        }

    }

    @Override
    protected void onProgressUpdate(final Integer... values) {
        textView.setText("Fetching Bus Location Data : "+Integer.toString(values[0])+"%");
        progressBar.setProgress(values[0]);
    }
}
