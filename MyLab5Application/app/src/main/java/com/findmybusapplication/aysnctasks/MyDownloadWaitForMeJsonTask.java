package com.findmybusapplication.aysnctasks;

import android.os.AsyncTask;
import android.util.Log;

//import com.findmybusapplication.adapter.MyBaseAdapterForFoldable;
import com.findmybusapplication.adapter.MyBaseAdapterForFoldable;
import com.findmybusapplication.data.BusData;
import com.findmybusapplication.helper.GenericHelper;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

/**
 * Created by prashant on 3/27/2015.
 */

public class MyDownloadWaitForMeJsonTask extends AsyncTask<String,Void,String> {
    private final WeakReference<MyBaseAdapterForFoldable> adapterWeakReference;
    private BusData busData;

    public MyDownloadWaitForMeJsonTask(MyBaseAdapterForFoldable recyclerAdapter, BusData busData){
        adapterWeakReference = new WeakReference<MyBaseAdapterForFoldable>(recyclerAdapter);
        this.busData = busData;
    }

    @Override
    protected String doInBackground(String... urls) {
        Log.i("", "Into thread background : MyDownloadWaitForMeJsonTask");
        String outputStringFromUrl = "";
        for(String url : urls){
            outputStringFromUrl = GenericHelper.getOutputFromUrl(url);
        }
        Log.i("", "Into thread background : MyDownloadWaitForMeJsonTask"+outputStringFromUrl);

        return outputStringFromUrl;
    }


    @Override
    protected void onPostExecute(String jsonString) {
        try{
            busData.getWaitForMeRequestList().clear();
            JSONArray jsonArray = new JSONArray(jsonString);
            List<Map<String,?>> waitRequestList = new ArrayList<Map<String,?>>();
            for(int i = 0 ; i < jsonArray.length();i++){
                Map dataMap = new HashMap();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String latitudeString = (String)jsonObject.get("latitude");
                Double busLatitude = Double.parseDouble(latitudeString);
                String longitudeString = (String)jsonObject.get("longitude");
                Double busLongitude = Double.parseDouble(longitudeString);
                String dateString = (String)jsonObject.get("date");
                Long date = Long.parseLong(dateString);
                String username = (String)jsonObject.get("username");
                String busName = (String)jsonObject.get("busname");
                String time = (String)jsonObject.get("time");
                String description = "User "+username+"  has requested to wait Bus = "+busName+" for "+
                        time+" mins. This Request was Created during "+(new Date(date));
                //setting the  Bus location
                LatLng busLocation = new LatLng(busLatitude,busLongitude);
                description+=". He is waiting at "+busLocation.toString();
                dataMap.put("description",description);
                dataMap.put("name",username);
                waitRequestList.add(dataMap);
                Log.i("Title",(String)dataMap.get("name"));
                Log.i("Description",(String)dataMap.get("description"));

            }
            for(Map<String,?> map : waitRequestList){
                busData.getWaitForMeRequestList().add(map);

            }

        }
        catch (Exception e){
            Log.e("onPostExecute"," Exception after retreiving JSON String", e);
        }

        if(adapterWeakReference != null){
            final MyBaseAdapterForFoldable adapter = adapterWeakReference.get();
            if(adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
    }
}
