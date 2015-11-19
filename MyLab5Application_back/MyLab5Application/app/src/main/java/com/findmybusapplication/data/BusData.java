package com.findmybusapplication.data;

import android.content.Context;

import com.findmybusapplication.bean.Bus;
import com.findmybusapplication.bean.BusForLocation;
import com.findmybusapplication.bean.BusFormData;
import com.findmybusapplication.jsonhandler.JsonBusDataParser;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by prashant on 4/22/2015.
 */
public class BusData implements Serializable {
    private Map<String,Bus> busDataMap;
    private Map<String,List<BusForLocation>> stopLocationMap;
    private List<Bus> selectedBusList;
    private BusFormData busFormData;
    private List<BusFormData> savedBusFormDataList;
    private boolean isSaveSearchEnabled = false;
    private BusFormData selectedSavedFormData ;

    public boolean isSaveSearchEnabled() {
        return isSaveSearchEnabled;
    }

    public void setSaveSearchEnabled(boolean isSaveSearchEnabled) {
        this.isSaveSearchEnabled = isSaveSearchEnabled;
    }

    public void setSavedBusFormDataList(List<BusFormData> savedBusFormDataList) {
        this.savedBusFormDataList = savedBusFormDataList;
    }

    public BusFormData getSelectedSavedFormData() {
        return selectedSavedFormData;
    }

    public void setSelectedSavedFormData(BusFormData selectedSavedFormData) {
        this.selectedSavedFormData = selectedSavedFormData;
    }



    public List<BusFormData> getSavedBusFormDataList() {
        return savedBusFormDataList;
    }


    public BusFormData getBusFormData() {
        return busFormData;
    }

    public void setBusFormData(BusFormData busFormData) {
        this.busFormData = busFormData;
    }

    public List<Bus> getSelectedBusList() {
        return selectedBusList;
    }

    public void setSelectedBusList(List<Bus> selectedBusList) {
        this.selectedBusList = selectedBusList;
    }


    public Map<String, Bus> getBusDataMap() {
        return busDataMap;
    }

    public void setBusDataMap(Map<String, Bus> busDataMap) {
        this.busDataMap = busDataMap;
    }

    public Map<String, List<BusForLocation>> getStopLocationMap() {
        return stopLocationMap;
    }

    public void setStopLocationMap(Map<String, List<BusForLocation>> stopLocationMap) {
        this.stopLocationMap = stopLocationMap;
    }

    public void generateBusData(Context context){
        JsonBusDataParser jsonBusDataParser = new JsonBusDataParser();
        stopLocationMap = jsonBusDataParser.getStopLocationData(context);
        busDataMap = jsonBusDataParser.getBusDataMap(context);
        savedBusFormDataList = jsonBusDataParser.getListOfSavedSearchData(context);
    }

}
