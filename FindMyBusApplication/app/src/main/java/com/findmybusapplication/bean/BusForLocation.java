package com.findmybusapplication.bean;

import java.util.ArrayList;
import java.util.List;

public class BusForLocation {
	private String busName;
	private String operatorName;
	private List<Double> timings = new ArrayList<Double>();	
	private Double nearestTiming ;
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
	public List<Double> getTimings() {
		return timings;
	}
	public void setTimings(List<Double> timings) {
		this.timings = timings;
	}
	public Double getNearestTiming() {
		return nearestTiming;
	}
	public void setNearestTiming(Double nearestTiming) {
		this.nearestTiming = nearestTiming;
	}
	public List<Double> getNextTimings() {
		return nextTimings;
	}
	public void setNextTimings(List<Double> nextTimings) {
		this.nextTimings = nextTimings;
	}
	
	
}
