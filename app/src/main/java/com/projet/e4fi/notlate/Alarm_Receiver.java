package com.projet.e4fi.notlate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Alarm_Receiver extends BroadcastReceiver {

    public static String WAKE = "C'est l'heure !";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("Dans le receiver", "C'est un debut");

        //Permet de savoir sur quel boutton l'utilisateur a appuyer
        String get_string_key = intent.getExtras().getString("extra");

        Log.e("Quelle est la clé ?", get_string_key);

        //On va récupérer l'extra long de l'intent
        //Pour savoir quelle musique à choisi l'utilisateur
        Integer get_sound_choice = intent.getExtras().getInt("musique choisie");
        Log.e("La musique choisie est:", get_sound_choice.toString());

        //On creer un Intent pour la classe Alarm_RingtoneService
        Intent Service_intent = new Intent(context, Alarm_RingtoneService.class);

        Service_intent.putExtra("extra", get_string_key);

        //On passe l'extra du Receiver au RingtoneService
        Service_intent.putExtra("musique choisie", get_sound_choice);

        context.startService(Service_intent);


    }
}
