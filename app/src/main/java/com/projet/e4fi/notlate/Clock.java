package com.projet.e4fi.notlate;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Clock {
    final static int MONDAY = 0;
    final static int TUESDAY = 1;
    final static int WEDNESDAY = 2;
    final static int THURSDAY = 3;
    final static int FRIDAY = 4;
    final static int SATURDAY = 5;
    final static int SUNDAY = 6;

    private String name;
    private Date arrivalDate;
    private SimpleDateFormat dateFormat;
    private int arrivalHour;
    private int arrivalMinute;
    private int wakeUpTime;
    private boolean[] daysToRing;
    private String destination;
    private String selectedTransport;
    private String selectedAvoid;
    private String ringTone;
    private List<Action> actionsList;

    public void setDaysToRing(boolean[] daysToRing) {
        this.daysToRing = daysToRing;
    }

    public HashMap<String, Integer> actions;

    @SuppressLint("SimpleDateFormat")
    public Clock() {
        Calendar cal = Calendar.getInstance();
        arrivalDate = cal.getTime();
        dateFormat = new SimpleDateFormat();
        daysToRing = new boolean[7];
        //For string date : String date = dateFormat.format(myDate);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelectedTransport() {
        return selectedTransport;
    }

    public void setSelectedTransport(String selectedTransport) {
        this.selectedTransport = selectedTransport;
    }

    public String getSelectedAvoid() {
        return selectedAvoid;
    }

    public void setSelectedAvoid(String selectedAvoid) {
        this.selectedAvoid = selectedAvoid;
    }

    public int getArrivalHour() {
        return arrivalHour;
    }

    public void setArrivalHour(int arrivalHour) {
        this.arrivalHour = arrivalHour;
    }

    public int getArrivalMinute() {
        return arrivalMinute;
    }

    public void setArrivalMinute(int arrivalMinute) {
        this.arrivalMinute = arrivalMinute;
    }

    public String getRingTone() {
        return ringTone;
    }

    public void setRingTone(String ringTone) {
        this.ringTone = ringTone;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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
