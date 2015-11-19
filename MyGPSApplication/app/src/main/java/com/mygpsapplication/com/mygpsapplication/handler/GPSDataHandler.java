package com.mygpsapplication.com.mygpsapplication.handler;

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

import com.mygpsapplication.R;

/**
 * Created by prashant on 4/15/2015.
 */
public class GPSDataHandler {
    TextView latitudeText;
    TextView longitudeText;
    FragmentActivity fragmentActivity;

    public GPSDataHandler(FragmentActivity fragmentActivity){
        this.fragmentActivity = fragmentActivity;
    }

    public View getViewAndSetGPSLocationDetails(LayoutInflater inflater, ViewGroup container){

        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        latitudeText = (TextView) rootView.findViewById(R.id.latitude);
        longitudeText = (TextView) rootView.findViewById(R.id.logitude);

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
