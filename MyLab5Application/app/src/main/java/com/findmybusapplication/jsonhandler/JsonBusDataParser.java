package com.findmybusapplication.jsonhandler;

import android.content.Context;
import android.util.Log;

import com.findmybusapplication.bean.Bus;
import com.findmybusapplication.bean.BusForLocation;
import com.findmybusapplication.bean.BusFormData;
import com.findmybusapplication.businesslogic.BusDataComputation;
import com.findmybusapplication.constants.FileNameConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonBusDataParser {

    /**
     *
     * @param fileName
     * @return
     */
    public  String getJsonString(String fileName, Context context) {
        String json = "";
        BufferedReader br ;
        try {
            InputStream is =(context.getAssets().open(fileName));
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     *
     * @param context
     * @return the data containing data
     */
    public  Map<String,List<BusForLocation>> getStopLocationData(Context context){
        Map<String,List<BusForLocation>> stopLocationMap = new HashMap<String, List<BusForLocation>>();

        try{
            String jsonString = getJsonString(FileNameConstants.BUS_STOPS_FILE_NAME, context);

            JSONArray jsonArray = new JSONArray(jsonString);

            for(int i = 0 ; i <jsonArray.length(); i++){

                JSONObject jsonLocationObj = (JSONObject)jsonArray.get(i);
                String locationName = (String)jsonLocationObj.get("locationName");
                JSONArray jsonStopsObjArray = (JSONArray) jsonLocationObj.get("buses");

                List<BusForLocation> busForLocationList  = new ArrayList<BusForLocation>();

                for (int j = 0 ; j < jsonStopsObjArray.length() ; j ++){
                    BusForLocation busForLocation = new BusForLocation();
                    JSONObject jsonBusForLocationObj = (JSONObject)jsonStopsObjArray.get(j);

                    String busName = (String)jsonBusForLocationObj.get("busName");
                    String operatorName = (String) jsonBusForLocationObj.get("operatorName");

                    List<Double> busTimings = new ArrayList<Double>();
                    JSONArray jsonTimingsArray = (JSONArray) jsonBusForLocationObj.get("timings");

                    for(int k = 0 ; k < jsonTimingsArray.length() ; k++){
                        busTimings.add((Double)jsonTimingsArray.get(k));
                    }

                    busForLocation.setTimings(busTimings);
                    busForLocation.setBusName(busName);
                    busForLocation.setOperatorName(operatorName);

                    busForLocationList.add(busForLocation);
                }

                stopLocationMap.put(locationName, busForLocationList);
            }

            //System.out.println(stopLocationMap.get("East Genesse and Westcott").get(1).getOperatorName());
            //System.out.println(stopLocationMap.get("East Genesse and Westcott").get(1).getTimings());



        }
        catch(Exception e){
            e.printStackTrace();
        }

        return stopLocationMap;
    }

    /**
     *
     * @return the Bus Detail Data from Json file
     */
    public  Map<String,Bus> getBusDataMap(Context context){
        Map<String,Bus> busDataMap = new HashMap<String, Bus>();

        try{
            String jsonString = getJsonString(FileNameConstants.BUS_DATA_FILE_NAME,context);

            JSONArray jsonArray = new JSONArray(jsonString);

            for(int i = 0 ; i <jsonArray.length(); i++){

                JSONObject jsonLocationObj = (JSONObject)jsonArray.get(i);
                String busKey = (String)jsonLocationObj.get("busKey");
                JSONArray jsonBusDetailsArray = (JSONArray) jsonLocationObj.get("busDetails");

                Bus bus  = new Bus();

                for (int j = 0 ; j < jsonBusDetailsArray.length() ; j ++){

                    JSONObject jsonBusDetailsObj = (JSONObject)jsonBusDetailsArray.get(j);

                    String busName = (String)jsonBusDetailsObj.get("busName");
                    String operatorName = (String) jsonBusDetailsObj.get("operatorName");
                    String description =  (String)jsonBusDetailsObj.get("busDescription");
                    Double ratings = (Double)jsonBusDetailsObj.get("busRatings");
                    String busImage = (String) jsonBusDetailsObj.get("busImage");
                    Double costPerStop = (Double) jsonBusDetailsObj.get("costPerStop");
                    String doNotOperate = (String) jsonBusDetailsObj.get("doNotOperate");

                    List<String> busStops = new ArrayList<String>();
                    JSONArray jsonStopsArray = (JSONArray) jsonBusDetailsObj.get("busStops");

                    for(int k = 0 ; k < jsonStopsArray.length() ; k++){
                        busStops.add((String)jsonStopsArray.get(k));
                    }

                    bus.setBusStops(busStops);
                    bus.setBusName(busName);
                    bus.setOperatorName(operatorName);
                    bus.setBusDescription(description);
                    bus.setBusImage(busImage);
                    bus.setBusRatings(ratings);
                    bus.setCostPerStop(costPerStop);
                    bus.setBusStops(busStops);
                    bus.setDoNotOperate(doNotOperate);

                }

                busDataMap.put(busKey, bus);
            }

            //System.out.println(busDataMap.get("James Street-Ultimate Transit").getOperatorName());
            //	System.out.println(busDataMap.get("SU Westcott-Centro").getBusStops());



        }
        catch(Exception e){
            e.printStackTrace();
        }

        return busDataMap;
    }

    /**
     *
     * @param jsonInputString
     * @param currentFormData
     * @return the Json String
     */
    public String getFinalSavedDataInJsonFormat(String jsonInputString,BusFormData currentFormData){
        String jsonOutputString = "";
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("from",currentFormData.getFrom());
            jsonObject.put("to",currentFormData.getTo());
            jsonObject.put("date",currentFormData.getDate());
            jsonObject.put("time",currentFormData.getTime());
            jsonObject.put("sortingCriteria",currentFormData.getAdditionalCriteria());
            jsonObject.put("createdDate", System.currentTimeMillis());

            if(!"".equals(jsonInputString)){
                JSONArray jsonArray = new JSONArray(jsonInputString);
                jsonArray.put(jsonObject);
                jsonOutputString =  jsonArray.toString();
            }
            else{
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(jsonObject);
                jsonOutputString = jsonArray.toString();
                Log.i("First JSON", " : "+jsonOutputString);
            }

        }
        catch (Exception e){
            Log.e("JsonBusDataParser","jsonOutputString"+jsonOutputString,e);
        }
        return jsonOutputString;
    }

    /**
     *
     * @param context
     * @return the list of saved Bus form data
     */
    public List<BusFormData> getListOfSavedSearchData(Context context){
        List<BusFormData> busFormDataList = new ArrayList<BusFormData>();
        String jsonInputString = "";
        try{
            File traceFile = new File((context).getExternalFilesDir(null), "data.json");
            if (!traceFile.exists()){
                return busFormDataList;
            }
            else{
                BufferedReader br = new BufferedReader(new FileReader(traceFile));
                String currentLine = null;
                while( (currentLine = br.readLine()) != null){
                    jsonInputString += currentLine;
                }

                Log.i("Saved JSON"," : "+jsonInputString);

                if(!"".equals(jsonInputString)){
                    JSONArray jsonArray = new JSONArray(jsonInputString);
                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        BusFormData busFormData = new BusFormData();
                        busFormData.setFrom((String)jsonObject.get("from"));
                        busFormData.setTo((String) jsonObject.get("to"));
                        busFormData.setDate((String) jsonObject.get("date"));
                        busFormData.setTime((String) jsonObject.get("time"));
                        busFormData.setAdditionalCriteria((String) jsonObject.get("sortingCriteria"));
                        busFormData.setCreationDate(new Date((Long)jsonObject.get("createdDate")));
                        busFormDataList.add(busFormData);
                    }

                    Log.i("busFormDataList : ",busFormDataList.toString());

                }
            }
        }
        catch (Exception e){
            Log.e("saveSearchData","GenericHelper",e);
        }
        return  busFormDataList;
    }
	
	
	
	/*
	public static void main(String[] args) {

		/*BusForLocation busForLocation1 = new BusForLocation();
		busForLocation1.busName = "James Street";
		busForLocation1.operatorName = "Ultimate Transit";
		busForLocation1.timings.add("12.20");  
		busForLocation1.timings.add("1.20");  
		busForLocation1.timings.add("2.10");  
		busForLocation1.timings.add("3.20");  

		BusForLocation busForLocation2 = new BusForLocation();
		busForLocation2.busName = "SU Westcott";
		busForLocation2.operatorName = "Centro";
		busForLocation2.timings.add("12.30");  
		busForLocation2.timings.add("1.30");  
		busForLocation2.timings.add("2.20");  
		busForLocation2.timings.add("3.40");  

		List<BusForLocation> busLocationList1 = new ArrayList<BusForLocation>();
		busLocationList1.add(busForLocation1);	busLocationList1.add(busForLocation2);

		List<BusForLocation> busLocationList2 = new ArrayList<BusForLocation>();
		busLocationList2.add(busForLocation2);	

		locationMap.put("Westcott", busLocationList1);
		locationMap.put("Centro", busLocationList2);	

		Map<String,List<String>> testJson = new HashMap<String,List<String>>();
		testJson.put("Westcott", busForLocation1.timings);
		testJson.put("Centro", busForLocation2.timings);


		JSONObject jsonObj =new JSONObject(testJson);


		//System.out.println(testJson.toString());


		JsonBusDataParser jsonBusDataParser = new JsonBusDataParser();
		Map<String,List<BusForLocation>> locationMap = jsonBusDataParser.getStopLocationData();
		Map<String,Bus> busDataMap = jsonBusDataParser.getBusDataMap();

		
		BusDataComputation busDataHandler = new BusDataComputation();
		List<Bus> selectedBuses = busDataHandler.getBusLists("East Genesse and Westcott","College Place",
				new Double(11.10),new java.sql.Date(System.currentTimeMillis()),"Timings" );
		System.out.println("The selected buses according to Cost are : ");
		for(Bus bus: selectedBuses){
			System.out.println(bus.getBusName() + " and they will arrive at your source at "+bus.getNextTimings());
		}
	}
    */
}
