package com.findmybusapplication.helper;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.findmybusapplication.bean.Bus;
import com.findmybusapplication.bean.BusFormData;
import com.findmybusapplication.businesslogic.BusDataComputation;
import com.findmybusapplication.data.BusData;
import com.findmybusapplication.jsonhandler.JsonBusDataParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prashant on 4/21/2015.
 */
public class GenericHelper {



    /**
     *
     * @return the current time
     */
    public static String getCurrentTime() {


        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        String timeInString = ""+hour+":"+minute;

        return timeInString;

    }

    /**
     *
     * @param usernameEditTextView
     * @param busNameEditTextView
     * @param latitudeText
     * @param longitudeText
     * @return the url if fields are valid
     */
    public static String validateGPSFieldsForUploadingLocationDataAndGetURL( EditText usernameEditTextView,
                                                                     AutoCompleteTextView busNameEditTextView,
                                                                     TextView latitudeText,TextView longitudeText){
        String url ="";
        boolean isValid = true;
        if(usernameEditTextView.getText() != null && !"".equals(usernameEditTextView.getText().toString())){
            String usernameEditTextViewString = usernameEditTextView.getText().toString();
            url+="username="+URLEncoder.encode(usernameEditTextViewString)+"&";
            Log.i("usernameEditTextView:",usernameEditTextViewString);
        }
        else{
            usernameEditTextView.setText( "Enter Name\n");
            usernameEditTextView.setTextColor(Color.RED);
            isValid = false;
        }

        if(busNameEditTextView.getText() != null && !"".equals(busNameEditTextView.getText().toString())){
            String busNameEditTextViewString = busNameEditTextView.getText().toString();
            url+="busname="+URLEncoder.encode(busNameEditTextViewString)+"&";
            Log.i("busNameEdit:",busNameEditTextViewString);
        }
        else{
            busNameEditTextView.setText( "Enter Bus Name\n");
            busNameEditTextView.setTextColor(Color.RED);
            isValid = false;
        }

        if(isValid){
            url += "latitude="+latitudeText.getText()+"&longitude="+longitudeText.getText();
            Log.i("Final Uri ",""+url);
             return (url);
        }
        else{
            return "";
        }
    }

    /**
     *
     * @param usernameEditTextView
     * @param busNameEditTextView
     * @param latitudeText
     * @param longitudeText
     * @return the url if fields are valid
     */
    public static String validateGPSFieldsForWaitForMeRequestAndGetURL( EditText timeEditTextView,
                                                                            EditText usernameEditTextView,
                                                                             AutoCompleteTextView busNameEditTextView,
                                                                             TextView latitudeText,TextView longitudeText){
        String url ="";
        boolean isValid = true;
        if(timeEditTextView.getText() != null && !"".equals(timeEditTextView.getText().toString())){
            String timeEditTextViewString = timeEditTextView.getText().toString();
            url+="time="+URLEncoder.encode(timeEditTextViewString)+"&";
            Log.i("usernameEditTextView:",timeEditTextViewString);
        }
        else{
            usernameEditTextView.setText( "Enter Time\n");
            usernameEditTextView.setTextColor(Color.RED);
            isValid = false;
        }


        if(usernameEditTextView.getText() != null && !"".equals(usernameEditTextView.getText().toString())){
            String usernameEditTextViewString = usernameEditTextView.getText().toString();
            url+="username="+URLEncoder.encode(usernameEditTextViewString)+"&";
            Log.i("usernameEditTextView:",usernameEditTextViewString);
        }
        else{
            usernameEditTextView.setText( "Enter Name\n");
            usernameEditTextView.setTextColor(Color.RED);
            isValid = false;
        }

        if(busNameEditTextView.getText() != null && !"".equals(busNameEditTextView.getText().toString())){
            String busNameEditTextViewString = busNameEditTextView.getText().toString();
            url+="busname="+URLEncoder.encode(busNameEditTextViewString)+"&";
            Log.i("busNameEdit:",busNameEditTextViewString);
        }
        else{
            busNameEditTextView.setText( "Enter Bus Name\n");
            busNameEditTextView.setTextColor(Color.RED);
            isValid = false;
        }

        if(isValid){
            url += "latitude="+latitudeText.getText()+"&longitude="+longitudeText.getText()+"&date="+new Date().getTime();
            Log.i("Final Uri ",""+url);
            return (url);
        }
        else{
            return "";
        }
    }

    /**
     *
     * @param busNameEditTextView
     * @return the url if fields are valid for tracking Bus
     */
    public static String validateGPSFieldsForViewingGPSLocationDataAndGetURL( EditText busNameEditTextView) {
        String url = "";
        boolean isValid = true;

        if (busNameEditTextView.getText() != null && !"".equals(busNameEditTextView.getText().toString())) {
            String busNameEditTextViewString = busNameEditTextView.getText().toString();
            url += "getLocationDataFor=" + URLEncoder.encode(busNameEditTextViewString);
            Log.i("busNameEdit:", busNameEditTextViewString);
        } else {
            busNameEditTextView.setText("Enter Bus Name\n");
            busNameEditTextView.setTextColor(Color.RED);
            isValid = false;
        }

        if(isValid){
            Log.i("Final Uri ",""+url);
            return (url);
        }
        else{
            return "";
        }

    }

    /**
     *
     * @param fromEditText
     * @param toEditText
     * @param dataPickerEditText
     * @param timePickerEditText
     * @param busData
     * @param sortingCriteriaValue
     * @return do necessary validations of the text and get the List of selected Buses
     */
    public static List<Bus> getSelectedBusesFromInputs(EditText fromEditText, EditText toEditText,
                                                       EditText dataPickerEditText, EditText timePickerEditText,
                                                       BusData busData, String sortingCriteriaValue){

        //storing form data
        BusFormData busFormData = new BusFormData();

        List<Bus> selectedBusList = new ArrayList<Bus>();
        String from ="", to ="",dateString = "",time ="",validationString ="";
        Double timeInDouble = 0.0;

        java.sql.Date date = null;
        boolean searchForBuses = true;
        if(fromEditText.getText() != null && !"".equals(fromEditText.getText().toString())){
            from = fromEditText.getText().toString();
            Log.i("From:",from);
            busFormData.setFrom(from);
        }
        else {
            fromEditText.setText( "Enter From Field\n");
            fromEditText.setTextColor(Color.RED);
            searchForBuses = false;
        }
        if(toEditText.getText() != null && !"".equals(toEditText.getText().toString())){
            to = toEditText.getText().toString();
            Log.i("To:",to);
            busFormData.setTo(to);
        }
        else {
            toEditText.setText("Enter To Field");
            toEditText.setTextColor(Color.RED);
            searchForBuses = false;
        }

        if(dataPickerEditText.getText() != null && !"".equals(dataPickerEditText.getText().toString() )){
            dateString = dataPickerEditText.getText().toString();
            busFormData.setDate(dateString);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try{
                Date utilDate = sdf.parse(dateString);
                date = new java.sql.Date(utilDate.getTime());
                Log.i("Date:",dateString);
            }
            catch (Exception e){
                Log.i("Parsing Date", e.toString());
                dataPickerEditText.setText("Enter Correct Date");
                dataPickerEditText.setTextColor(Color.RED);
                searchForBuses = false;

            }
        }
        else {
            dataPickerEditText.setText("Please Enter Date");
            dataPickerEditText.setTextColor(Color.RED);
            searchForBuses = false;
        }

        if(timePickerEditText.getText() != null && !"".equals(timePickerEditText.getText().toString())){
            busFormData.setTime(timePickerEditText.getText().toString());
            time = timePickerEditText.getText().toString().replace(":",".");
            timeInDouble = Double.parseDouble(time);
            Log.i("Time:",time);
        }
        else {
            timePickerEditText.setText("Please Enter Date");
            timePickerEditText.setTextColor(Color.RED);
            searchForBuses = false;
        }

        Log.i("Criteria:",sortingCriteriaValue);
        busFormData.setAdditionalCriteria(sortingCriteriaValue);

        //storing the busFormdata
        busData.setBusFormData(busFormData);

        if(searchForBuses){
            BusDataComputation busDataComputation = new BusDataComputation(busData.getStopLocationMap(),
                    busData.getBusDataMap());
            selectedBusList = busDataComputation.getBusLists(from,to,timeInDouble,date,sortingCriteriaValue);
        }
        return selectedBusList;
    }

    /**
     *
     * @param busData
     * @param context
     */
    public static void saveSearchData(BusData busData, Context context){
        BusFormData currentFormData = busData.getBusFormData();
        String jsonInputString = "";
        String jsonOutputString ="";
        try{
            File traceFile = new File((context).getExternalFilesDir(null), "data.json");
            if (!traceFile.exists()){
                traceFile.createNewFile();
            }
            else{
                BufferedReader br = new BufferedReader(new FileReader(traceFile));
                String currentLine = null;
                while( (currentLine = br.readLine()) != null){
                    jsonInputString += currentLine;
                }
            }
            JsonBusDataParser jsonBusDataParser = new JsonBusDataParser();
            jsonOutputString = jsonBusDataParser.getFinalSavedDataInJsonFormat(jsonInputString,currentFormData);
            BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile, false /*do not append*/));
            writer.write(jsonOutputString);
            writer.close();

            MediaScannerConnection.scanFile(context,
                    new String[]{traceFile.toString()},
                    null,
                    null);
        }
        catch (Exception e){
            Log.e("saveSearchData","GenericHelper",e);
        }
    }

    /**
     *
     * @param url
     * @return the output String from the url
     */
    public static String getOutputFromUrl(String url) {
        String json=null, line;

        InputStream stream = getHttpConnection(url);
        if (stream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder out = new StringBuilder();
            try {
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                reader.close();
                json = out.toString();
            } catch (IOException ex) {
                Log.d("MyDebugMsg", "IOException in downloadJSON()");
                ex.printStackTrace();
            }
        }
        return json;
    }

    /**
     *
     * @param urlString
     * @return
     */
    public static InputStream getHttpConnection(String urlString) {
        InputStream stream = null;
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        }  catch (UnknownHostException e1) {
            Log.d("MyDebugMsg", "UnknownHostexception in getHttpConnection()");
            e1.printStackTrace();
        } catch (Exception ex) {
            Log.d("MyDebugMsg", "Exception in getHttpConnection()");
            ex.printStackTrace();
        }
        return stream;
    }



}
