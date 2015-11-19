package com.findmybusapplication.businesslogic;

import android.content.Context;
import android.util.Log;

import com.findmybusapplication.bean.Bus;
import com.findmybusapplication.bean.BusForLocation;
import com.findmybusapplication.constants.CriteriaConstants;
import com.findmybusapplication.jsonhandler.JsonBusDataParser;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


class BusRatingsComparator implements Comparator<Bus>{

	/**
	 * 
	 * ratings sorted according to descending order
	 * 
	 */
	@Override
	public int compare(Bus b1, Bus b2) {
		// TODO Auto-generated method stub
		Double difference = (b2.getBusRatings() - b1.getBusRatings());
		if(difference < 0){
			return -1;
		}
		else if (difference == 0){
			return 0;
		}
		else{
			return 1;
		}
	}
	
}

class BusTripCostComparator implements Comparator<Bus>{

	/**
	 * 
	 * Trip Cost sorted according to descending order
	 * 
	 */
	@Override
	public int compare(Bus b1, Bus b2) {
		// TODO Auto-generated method stub
		Double difference = (b1.getTotalTripCost() - b2.getTotalTripCost());
		if(difference < 0){
			return -1;
		}
		else if (difference == 0){
			return 0;
		}
		else{
			return 1;
		}	}
	
}

class BusTripTimeComparator implements Comparator<Bus>{

	/**
	 * 
	 * Trip Time sorted according to descending order
	 * 
	 */
	@Override
	public int compare(Bus b1, Bus b2) {
		// TODO Auto-generated method stub
		Double difference = (b1.getDifferenceInTime() - b2.getDifferenceInTime());
		if(difference < 0){
			return -1;
		}
		else if (difference == 0){
			return 0;
		}
		else{
			return 1;
		}	}
	
}

public class BusDataComputation {

    private Map<String,List<BusForLocation>> stopLocationMap ;
    private Map<String,Bus> busDataMap ;

    public BusDataComputation(Map<String,List<BusForLocation>> stopLocationMap,
                              Map<String,Bus> busDataMap){
        this.stopLocationMap = stopLocationMap;
        this.busDataMap = busDataMap;
    }

	/**
	 * 
	 * @param busForLocationList
	 * @param arrivalTime
	 * @return
	 */
	public List<BusForLocation> getEarliestPossibleBusList(List<BusForLocation> busForLocationList, Double arrivalTime){
		List<BusForLocation> eligibleBusForLocationList = new ArrayList<BusForLocation>();

		for(BusForLocation busForLocation : busForLocationList){
			List<Double> timings = busForLocation.getTimings();
			List<Double> nextTimings = new ArrayList<Double>();
			for(Double timing : timings){
				if(timing >= arrivalTime  ){
					nextTimings.add(timing);
					
				}
			}
			if(nextTimings.size() > 0){
				busForLocation.setNearestTiming(nextTimings.get(0));
				eligibleBusForLocationList.add(busForLocation);

			}
			busForLocation.setNextTimings(nextTimings);
		}
		
		return eligibleBusForLocationList;
	}
	
	/**
	 * 
	 * @param sortType
	 * @param selectedBuses
	 */
	public void sortAccordingToCriteria(String sortType, List<Bus> selectedBuses){
		if(sortType != null){
			switch(sortType){
			case CriteriaConstants.COST_CRITERIA: Collections.sort(selectedBuses, new BusTripCostComparator()); break;
			case CriteriaConstants.RATINGS_CRITERIA :Collections.sort(selectedBuses, new BusRatingsComparator()); break;
			case CriteriaConstants.TIME_CRITERIA : Collections.sort(selectedBuses, new BusTripTimeComparator()); break;
			}
		}
		
	}
	

	/**
	 * 
	 * @param from
	 * @param to
	 * @param bus
	 * @return
	 */
	public Double calculateBusCost(String from, String to, Bus bus){
		Double cost = 0.0;
		List<String> stops = bus.getBusStops();
		int indexFrom = stops.indexOf(from);
		int indexTo = stops.indexOf(to);
		
		int differenceBetweenStops = Math.abs(indexFrom - indexTo);
		
		cost = bus.getCostPerStop()*differenceBetweenStops;
		
		return cost;
	}
	
	/**
	 * 
	 * @param arrivalTime
	 * @param reachingTime
	 * @return
	 */
	public Double calculateBusTimeDifference(Double arrivalTime, Double reachingTime){
		return reachingTime - arrivalTime;
	}

	/**
	 * 
	 * @param from
	 * @param to
	 * @param arrivalTime
	 * @param date
	 * @param sortType
	 * @return the List of buses according to different critieria inputs
	 */
	public List<Bus> getBusLists(String from, String to, Double arrivalTime,
			Date date, String sortType){
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
		
		String day = simpleDateFormat.format(date).toUpperCase();

	    //System.out.println("Todays DAY "+simpleDateFormat.format(date).toUpperCase());

		List<Bus> selectedBuses = new ArrayList<Bus>();

        /*
		JsonBusDataParser jsonBusDataParser = new JsonBusDataParser();
		Map<String,List<BusForLocation>> stopLocationMap = jsonBusDataParser.getStopLocationData(context);
		Map<String,Bus> busDataMap = jsonBusDataParser.getBusDataMap(context);
        */

		List<BusForLocation> fromBusForLocationList = stopLocationMap.get(from);
		List<BusForLocation> toBusForlocationList = stopLocationMap.get(to);
        if(!(fromBusForLocationList == null || toBusForlocationList == null)){
            List<BusForLocation> eligibleBusForLocationListFrom = getEarliestPossibleBusList(fromBusForLocationList, arrivalTime);
            //List<BusForLocation> eligibleBusForLocationListTo = getEarliestPossibleBusList(toBusForlocationList, arrivalTime);

            for(BusForLocation busForLocationFrom : eligibleBusForLocationListFrom){
                String busKeyFrom = busForLocationFrom.getBusName()+"-"+busForLocationFrom.getOperatorName();
                Double arrivalTimeAtStop = busForLocationFrom.getNearestTiming();
                int busSequenceNumber = busForLocationFrom.getTimings().indexOf(arrivalTimeAtStop);

                for(BusForLocation busForLocationTo : toBusForlocationList){

                    String busKeyTo = busForLocationTo.getBusName()+"-"+busForLocationTo.getOperatorName();

                    if(busKeyFrom != null && busKeyFrom.endsWith(busKeyTo) &&
                            busDataMap.containsKey(busKeyFrom)){

                        Bus bus = busDataMap.get(busKeyFrom);

                        //check if bus operates on same day
                        if(bus.getDoNotOperate() != null &&
                                !bus.getDoNotOperate().toUpperCase().contains(day)){
                            bus.setArrivalTimeAtStop(arrivalTimeAtStop);

                            //find out the reachingTimeAtStop
                            Double reachingTimeAtStop = 0.0;
                            if(busSequenceNumber != -1){
                                reachingTimeAtStop = busForLocationTo.getTimings().get(busSequenceNumber);
                            }


                            bus.setReachingTimeAtStop(reachingTimeAtStop);
                            bus.setNextTimings(busForLocationFrom.getNextTimings());

                            bus.setTotalTripCost(calculateBusCost(from, to, bus));
                            bus.setDifferenceInTime(calculateBusTimeDifference(arrivalTimeAtStop, reachingTimeAtStop));


                            //Log display
                            Log.i("getBusLists"," Reaching Time for " + bus.getBusName() + " is " + reachingTimeAtStop +
                                    " and total cost is " + bus.getTotalTripCost() +
                                    " and time difference is " + bus.getDifferenceInTime() + "" +
                                    " and ratings are " + bus.getBusRatings());

                            selectedBuses.add(bus);
                        }

                    }
                }
            }

            sortAccordingToCriteria(sortType, selectedBuses);

        }
	
		return selectedBuses;

	}

}
