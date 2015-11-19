package com.findmybusapplication.bean;

import java.io.Serializable;
import java.util.List;

public class Bus implements Serializable{
	private String busName;
	private String operatorName;
	private String busDescription;
	private Double busRatings;
	private String busImage;	
	private List<String> busStops;	
	private Double costPerStop;
	private String doNotOperate;
	
	//data to be varied
	private Double reachingTimeAtStop;
	private Double arrivalTimeAtStop;
	private Double totalTripCost;
	private Double differenceInTime;
	private List<Double> nextTimings;

	

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public List<String> getBusStops() {
		return busStops;
	}

	public void setBusStops(List<String> busStops) {
		this.busStops = busStops;
	}

	

	public String getBusDescription() {
		return busDescription;
	}

	public void setBusDescription(String busDescription) {
		this.busDescription = busDescription;
	}

	

	public String getBusImage() {
		return busImage;
	}

	public void setBusImage(String busImage) {
		this.busImage = busImage;
	}

	public Double getCostPerStop() {
		return costPerStop;
	}

	public void setCostPerStop(Double costPerStop) {
		this.costPerStop = costPerStop;
	}

	public Double getBusRatings() {
		return busRatings;
	}

	public void setBusRatings(Double busRatings) {
		this.busRatings = busRatings;
	}

	public String getDoNotOperate() {
		return doNotOperate;
	}

	public void setDoNotOperate(String doNotOperate) {
		this.doNotOperate = doNotOperate;
	}

	public Double getReachingTimeAtStop() {
		return reachingTimeAtStop;
	}

	public void setReachingTimeAtStop(Double reachingTimeAtStop) {
		this.reachingTimeAtStop = reachingTimeAtStop;
	}

	public Double getArrivalTimeAtStop() {
		return arrivalTimeAtStop;
	}

	public void setArrivalTimeAtStop(Double arrivalTimeAtStop) {
		this.arrivalTimeAtStop = arrivalTimeAtStop;
	}

	public Double getTotalTripCost() {
		return totalTripCost;
	}

	public void setTotalTripCost(Double totalTripCost) {
		this.totalTripCost = totalTripCost;
	}

	public Double getDifferenceInTime() {
		return differenceInTime;
	}

	public void setDifferenceInTime(Double differenceInTime) {
		this.differenceInTime = differenceInTime;
	}

	public List<Double> getNextTimings() {
		return nextTimings;
	}

	public void setNextTimings(List<Double> nextTimings) {
		this.nextTimings = nextTimings;
	}

	
	
}
