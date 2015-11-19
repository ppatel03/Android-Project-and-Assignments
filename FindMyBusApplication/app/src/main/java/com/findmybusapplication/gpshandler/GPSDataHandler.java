package com.findmybusapplication.gpshandler;

import android.animation.ValueAnimator;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/*
import com.findmybusapplication.R;
import com.findmybusapplication.constants.GoogleMapConstants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
*/


public class GPSDataHandler {
    /*
    TextView latitudeText;
    TextView longitudeText;
    FragmentActivity fragmentActivity;
    GoogleMap googleMap;
    Marker now;

    public GPSDataHandler(FragmentActivity fragmentActivity){
        this.fragmentActivity = fragmentActivity;
    }

    public View getViewAndSetGPSLocationDetails(LayoutInflater inflater, ViewGroup container){

        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        latitudeText = (TextView) rootView.findViewById(R.id.latitude);
        longitudeText = (TextView) rootView.findViewById(R.id.logitude);
        googleMap = ((MapFragment)fragmentActivity.getFragmentManager().findFragmentById(R.id.google_map)).getMap();

        if(googleMap != null){
            //default location
            LatLng defaultLocation = new LatLng(GoogleMapConstants.DEFAULT_LATITUDE,GoogleMapConstants.DEFAULT_LONGITUDE);
            googleMap.addMarker(new MarkerOptions().position(defaultLocation).title("This is my Bus"));
            googleMap.getUiSettings().setZoomControlsEnabled(true);

        }

        LocationManager locationManager = (LocationManager)fragmentActivity.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0 ,0 , locationListener);
        return rootView;
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
*/
}


