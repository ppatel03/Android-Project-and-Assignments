package com.findmybusapplication.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by prashant on 4/22/2015.
 */
public class BusFormData implements Serializable{
    private String from;
    private String to;
    private String date;
    private String time;
    private String additionalCriteria;
    private Date creationDate;

    public BusFormData(){

    }

    public BusFormData(String from, String to, String date, String time, String additionalCriteria, Date creationDate) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.additionalCriteria = additionalCriteria;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "BusFormData{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", additionalCriteria='" + additionalCriteria + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public String getAdditionalCriteria() {
        return additionalCriteria;
    }

    public void setAdditionalCriteria(String additionalCriteria) {
        this.additionalCriteria = additionalCriteria;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

}
