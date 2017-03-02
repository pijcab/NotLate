package com.projet.e4fi.notlate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Clock {
    private Date arrivalDate;
    private int wakeUpTime;
    private boolean[] daysToRing;
    private String destination;
    private String city;
    private String postalCode;
    private List<Action> actionsList;
    public final static int MONDAY = 0;
    public final static int TUESDAY = 1;
    public final static int WEDNESDAY = 2;
    public final static int THURSDAY = 3;
    public final static int FRIDAY = 4;
    public final static int SATURDAY = 5;

    public void setDaysToRing(boolean[] daysToRing) {
        this.daysToRing = daysToRing;
    }

    public final static int SUNDAY = 6;


    public enum meanOfTransport {Null, Car, Bicycle, Foot}

    public enum toAvoid {Null, Toll, Highway, Ferry}

    public HashMap<String, Integer> actions;

    public Clock() {
        arrivalDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy HH:mm");
        daysToRing = new boolean[7];
        //For string date : String date = dateFormat.format(myDate);

    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public List<Action> getActionsList() {
        return actionsList;
    }

    public void setActionsList(List<Action> actionsList) {
        this.actionsList = actionsList;
    }

    public boolean[] getDaysToRing() {
        return daysToRing;
    }
}
