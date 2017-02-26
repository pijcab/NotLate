package com.projet.e4fi.notlate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Clock {
    public Date arrivalDate;
    public String destination;
    public String city;
    public String postalCode;

    public enum meanOfTransport {Null, Car, Bicycle, Foot}

    public enum toAvoid {Null, Toll, Highway, Ferry}

    public HashMap<String, Integer> actions;

    public Clock() {
        arrivalDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy HH:mm");
        //For string date : String date = dateFormat.format(myDate);

        actions = new HashMap<String, Integer>();
        /*
        Sur l'IHM l'appui sur l'ajout d'une action propose des activités standards du style :
        brosser les dents, se laver avec un durée prédeterminée. L'utilisateur peut ajouter ses
        propres activités en saisissant une durée.
         */
    }
}
