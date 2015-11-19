package com.findmybusapplication.gpshandler;

import android.animation.ValueAnimator;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.findmybusapplication.R;
import com.findmybusapplication.aysnctasks.MyTrackingBusLocationDetailAsyncTask;
import com.findmybusapplication.aysnctasks.MyUpdateBusLocationDetailAsyncTask;
import com.findmybusapplication.aysnctasks.MyUpdateWaitForMeDetailAsyncTask;
import com.findmybusapplication.bean.Bus;
import com.findmybusapplication.bean.BusForLocation;
import com.findmybusapplication.constants.GenericConstants;
import com.findmybusapplication.constants.GoogleMapConstants;
import com.findmybusapplication.data.BusData;
import com.findmybusapplication.helper.GenericHelper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class GPSDataHandler implements View.OnClickListener{

    EditText timeEditTextView;
    EditText usernameEditTextView;
    AutoCompleteTextView busNameEditTextView;
    TextView latitudeText;
    ImageView locationShareImageView;
    ImageView trackLocationImageView;
    ImageView waitForMeSubmitButtonView;
    TextView longitudeText;
    FragmentActivity fragmentActivity;
    GoogleMap googleMap;
    GoogleMap trackGoogleMap;
    AutoCompleteTextView trackBusNameEditTextView;
    Marker now;
    BusData busData;
    ProgressBar progressBar;
    ProgressBar trackProgressBar;
    TextView progressBarTextView;
    TextView trackProgressBarTextView;

    public GPSDataHandler(FragmentActivity fragmentActivity,BusData busData){
        this.fragmentActivity = fragmentActivity;
        this.busData = busData;
    }

    /**
     *
     * @param inflater
     * @param container
     * @param rootView
     * @return the view to share the bus location
     */
    public View getViewAndSetGPSLocationDetailsForShareLocation(LayoutInflater inflater, ViewGroup container,
                                                                View rootView){


        locationShareImageView = (ImageView)rootView.findViewById(R.id.locationShareImageView);
        locationShareImageView.setOnClickListener(this);

        usernameEditTextView = (EditText) rootView.findViewById(R.id.usernameEditTextView);
        busNameEditTextView = (AutoCompleteTextView) rootView.findViewById(R.id.busNameEditTextView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progressBarTextView = (TextView) rootView.findViewById(R.id.progressBarTextView);


        //setting the data for the dropdown
        Map <String,Bus> busDetailData = busData.getBusDataMap();
        Set<String> busNames = busDetailData.keySet();
        Object[] busNamesArray = (Object[]) busNames.toArray();
        ArrayAdapter adapter = new ArrayAdapter(this.fragmentActivity,
                android.R.layout.simple_spinner_dropdown_item,busNamesArray);
        busNameEditTextView.setAdapter(adapter);

        latitudeText = (TextView) rootView.findViewById(R.id.latitude);
        longitudeText = (TextView) rootView.findViewById(R.id.longitude);
        googleMap = ((MapFragment)fragmentActivity.getFragmentManager().findFragmentById(R.id.google_map)).getMap();

        if(googleMap != null){
            //default location
            LatLng defaultLocation = new LatLng(GoogleMapConstants.DEFAULT_LATITUDE,GoogleMapConstants.DEFAULT_LONGITUDE);
            googleMap.addMarker(new MarkerOptions().position(defaultLocation).title("Your Bus is Here"));
            googleMap.getUiSettings().setZoomControlsEnabled(true);

        }

        LocationManager locationManager = (LocationManager)fragmentActivity.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0 ,0 , locationListener);
        return rootView;
    }

    /**
     *
     * @param inflater
     * @param container
     * @param rootView
     * @return the view to request wait for me
     */
    public View getViewAndSetWaitForMeLocation(LayoutInflater inflater, ViewGroup container,
                                                                View rootView){


        waitForMeSubmitButtonView = (ImageView)rootView.findViewById(R.id.waitForMeSubmitButtonView);
        waitForMeSubmitButtonView.setOnClickListener(this);

        timeEditTextView = (EditText) rootView.findViewById(R.id.waitTimeEditTextView);
        usernameEditTextView = (EditText) rootView.findViewById(R.id.usernameEditTextView);
        busNameEditTextView = (AutoCompleteTextView) rootView.findViewById(R.id.busNameEditTextView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progressBarTextView = (TextView) rootView.findViewById(R.id.progressBarTextView);


        //setting the data for the dropdown
        Map <String,Bus> busDetailData = busData.getBusDataMap();
        Set<String> busNames = busDetailData.keySet();
        Object[] busNamesArray = (Object[]) busNames.toArray();
        ArrayAdapter adapter = new ArrayAdapter(this.fragmentActivity,
                android.R.layout.simple_spinner_dropdown_item,busNamesArray);
        busNameEditTextView.setAdapter(adapter);

        latitudeText = (TextView) rootView.findViewById(R.id.latitude);
        longitudeText = (TextView) rootView.findViewById(R.id.longitude);
        googleMap = ((MapFragment)fragmentActivity.getFragmentManager().findFragmentById(R.id.google_map)).getMap();

        if(googleMap != null){
            //default location
            LatLng defaultLocation = new LatLng(GoogleMapConstants.DEFAULT_LATITUDE,GoogleMapConstants.DEFAULT_LONGITUDE);
            googleMap.addMarker(new MarkerOptions().position(defaultLocation).title("You are Here"));
            googleMap.getUiSettings().setZoomControlsEnabled(true);

        }

        LocationManager locationManager = (LocationManager)fragmentActivity.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0 ,0 , locationListener);
        return rootView;
    }


    /**
     *
     * @param inflater
     * @param container
     * @param rootView
     * @return the view to get the live bus location
     */
    public View getViewAndTrackBusGPSForShareLocation(LayoutInflater inflater, ViewGroup container,
                                                                View rootView){

        trackLocationImageView = (ImageView)rootView.findViewById(R.id.trackLocationShareImageView);
        trackLocationImageView.setOnClickListener(this);
        trackBusNameEditTextView = (AutoCompleteTextView) rootView.findViewById(R.id.trackBusNameEditTextView);
        trackProgressBar = (ProgressBar) rootView.findViewById(R.id.trackProgressBar);
        trackProgressBarTextView = (TextView) rootView.findViewById(R.id.trackProgressBarTextView);
        trackGoogleMap = ((MapFragment)fragmentActivity.getFragmentManager().findFragmentById(R.id.track_google_map)).getMap();

        //setting the data for the dropdown
        Map <String,Bus> busDetailData = busData.getBusDataMap();
        Set<String> busNames = busDetailData.keySet();
        Object[] busNamesArray = (Object[]) busNames.toArray();
        ArrayAdapter adapter = new ArrayAdapter(this.fragmentActivity,
                android.R.layout.simple_spinner_dropdown_item,busNamesArray);
        trackBusNameEditTextView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.locationShareImageView :

                String url = GenericHelper.validateGPSFieldsForUploadingLocationDataAndGetURL(  usernameEditTextView,
                         busNameEditTextView, latitudeText, longitudeText);

                if(!"".equals(url)){
                    MyUpdateBusLocationDetailAsyncTask myUpdateBusLocationDetailAsyncTask = new
                            MyUpdateBusLocationDetailAsyncTask(progressBar,this.fragmentActivity.getApplicationContext(),
                            progressBarTextView);
                    String path = GenericConstants.PATH_FOR_SERVER+url;
                    myUpdateBusLocationDetailAsyncTask.execute(new String[]{path});
                }
                break;

            case R.id.waitForMeSubmitButtonView :

                String waitUrl = GenericHelper.validateGPSFieldsForWaitForMeRequestAndGetURL(timeEditTextView, usernameEditTextView,
                        busNameEditTextView, latitudeText, longitudeText);

                if(!"".equals(waitUrl)){
                    MyUpdateWaitForMeDetailAsyncTask myUpdateBusLocationDetailAsyncTask = new
                            MyUpdateWaitForMeDetailAsyncTask(progressBar,this.fragmentActivity.getApplicationContext(),
                            progressBarTextView);
                    String path = GenericConstants.PATH_FOR_SERVER+waitUrl;
                    Log.i("Wait : ",path);

                    myUpdateBusLocationDetailAsyncTask.execute(new String[]{path});
                }
                break;

            case R.id.trackLocationShareImageView :
                String trackURL = GenericHelper.validateGPSFieldsForViewingGPSLocationDataAndGetURL(trackBusNameEditTextView);

                if(!"".equals(trackURL)){
                    MyTrackingBusLocationDetailAsyncTask myTrackingBusLocationDetailAsyncTask = new
                            MyTrackingBusLocationDetailAsyncTask(trackProgressBar,this.fragmentActivity.getApplicationContext(),
                            trackProgressBarTextView,this.fragmentActivity,trackGoogleMap);
                    String trackPath = GenericConstants.PATH_FOR_SERVER+trackURL;

                    myTrackingBusLocationDetailAsyncTask.execute(new String[]{trackPath});
                }

        }
    }


    class  MyLocationListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            if(location != null){

                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();

                latitudeText.setText(latitude.toString());
                longitudeText.setText(longitude.toString());

                if(googleMap != null){
                    LatLng locationOnMap = new LatLng(latitude, longitude);
                    if(now != null){
                        now.remove();
                    }
                    now = googleMap.addMarker(new MarkerOptions()
                           .position(locationOnMap)
                           .title("This is my Bus")
                           .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(locationOnMap,
                            GoogleMapConstants.ZOOM_SCALE);
                    googleMap.animateCamera(cameraUpdate);
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

}


