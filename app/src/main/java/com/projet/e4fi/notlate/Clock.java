package com.projet.e4fi.notlate;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.Calendar;

public class Clock {
    final static int MONDAY = 0;
    final static int TUESDAY = 1;
    final static int WEDNESDAY = 2;
    final static int THURSDAY = 3;
    final static int FRIDAY = 4;
    final static int SATURDAY = 5;
    final static int SUNDAY = 6;

    private int arrivalHour;
    private int arrivalMinute;
    private int wakeUpTime;
    private int ringTone;
    private boolean isActivated;
    private boolean[] daysToRing;
    private String destination;
    private String depart;
    private int selectedTransport;
    private int selectedAvoid;
    private ArrayList<Action> actionsList;

    @SuppressLint("SimpleDateFormat")
    public Clock() {
        Calendar cal = Calendar.getInstance();
        arrivalHour = cal.getTime().getHours();
        arrivalMinute = cal.getTime().getMinutes();
        isActivated = true;
        daysToRing = new boolean[7];
        destination = "";
        depart = "";
        selectedAvoid = 0;
        selectedTransport = 0;
        ringTone = 0;
        actionsList = new ArrayList<>();
    }

    public int getSelectedTransport() {
        return selectedTransport;
    }

    public void setSelectedTransport(int selectedTransport) {
        this.selectedTransport = selectedTransport;
    }

    public int getSelectedAvoid() {
        return selectedAvoid;
    }

    public void setSelectedAvoid(int selectedAvoid) {
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

    public int getWakeUpTime() {
        return wakeUpTime;
    }

    public void setWakeUpTime(int wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    public int getRingTone() {
        return ringTone;
    }

    public void setRingTone(int ringTone) {
        this.ringTone = ringTone;
    }

    public String getDestination() {
        return destination;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDaysToRing(boolean[] daysToRing) {
        this.daysToRing = daysToRing;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public ArrayList<Action> getActionsList() {
        return actionsList;
    }

    public void setActionsList(ArrayList<Action> actionsList) {
        this.actionsList = actionsList;
    }

    public boolean[] getDaysToRing() {
        return daysToRing;
    }
}
