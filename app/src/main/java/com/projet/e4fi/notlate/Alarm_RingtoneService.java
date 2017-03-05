package com.projet.e4fi.notlate;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class Alarm_RingtoneService extends Service {


    MediaPlayer media_player;
    Boolean isRunning = false;
    int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        //Recuperer la valeur de l'extra string
        String state = intent.getExtras().getString("extra");

        Log.e("Ringtone extra vaut :", state);

        NotificationManager notification_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // On créer un intent pour communiquer avec la class MainAlarme
        Intent intent_notification_to_alarm_activity = new Intent(this.getApplicationContext(), MainActivity.class);
        //On créer un pending intent
        PendingIntent pending_intent_to_alarm_activity = PendingIntent.getActivity(this, 0, intent_notification_to_alarm_activity, 0);

        //Récupération de l'id de la musique choisie
        Integer musique_choisi_id = intent.getExtras().getInt("musique choisie");
        Log.e("Musique id : ", musique_choisi_id.toString());

        //On spécifie les parametres pour notre notofications
        //NotificationCompat permet apparament de show la notif même sur le lock screen
        NotificationCompat.Builder notification_popup = new NotificationCompat.Builder(this);
        notification_popup.setContentTitle("Alarme !");
        notification_popup.setContentText("Cliquer pour pouvoir désactiver");
        notification_popup.setContentIntent(pending_intent_to_alarm_activity);
        notification_popup.setSmallIcon(R.drawable.alarm_icon2);
        notification_popup.setAutoCancel(true);


        //Permet de convertir l'extra string en une valeur startId à 1 ou 0
        assert state != null;
        switch (state) {
            case "alarme activee":
                startId = 1;
                Log.e("Start id :", state);
                break;
            case "alarme desactivee":
                startId = 0;
                Log.e("Start id :", state);
                break;
            default:
                startId = 0;
                break;
        }

        //Si il n'y a pas de musique et qu'on appuie sur le boutton "ok"
        //On joue la musique
        if (!this.isRunning && startId == 1) {
            Log.e("Pas de musique", "Commencer une musique");

            this.isRunning = true;
            this.startId = 0;

            //Initialise la commande des notifs
            notification_manager.notify(0, notification_popup.build());
            //List<String> liste_musique = new ArrayList<String>();
            String liste_musique[] = {"zelda", "mario", "dragon_ball_z_ost", "samba_de_janeiro"};

            switch (musique_choisi_id) {

                case 0:
                    media_player = MediaPlayer.create(this, R.raw.zelda);
                    media_player.start();
                    break;
                case 1:
                    media_player = MediaPlayer.create(this, R.raw.mario);
                    media_player.start();
                    break;
                case 2:
                    media_player = MediaPlayer.create(this, R.raw.dragon_ball_z_ost);
                    media_player.start();
                    break;
                case 3:
                    media_player = MediaPlayer.create(this, R.raw.samba_de_janeiro);
                    media_player.start();
                    break;
                default:
                    media_player = MediaPlayer.create(this, R.raw.zelda);
                    media_player.start();
                    break;
            }

        }
        //Si il y a une musique qui est joué et qu'on appuie sur le bouton "desactivee"
        //On arrête la musique
        else if (this.isRunning && startId == 0) {
            Log.e("Deja une musique", "Arreter une musique");
            //On arrete la musique
            media_player.stop();
            media_player.reset();
            this.isRunning = false;
            this.startId = 0;
            if (notification_manager != null)
                notification_manager.cancel(0);

        }
        //Si il n'y a pas de musique de jouer et qu'on appuie sur "desactivee"
        //On ne fait rien
        else if (!this.isRunning && startId == 0) {
            this.isRunning = false;
            this.startId = 0;
        }
        //Si il y a une musique et qu'on appuie sur "ok"
        //On ne fait rien
        else if (this.isRunning && startId == 1) {
            this.isRunning = true;
            this.startId = 1;
        } else {
            Log.e("Dans le else", "IDK");
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
        Log.e("Dans le destroy", "OLE OLE");
        super.onDestroy();
        this.isRunning = false;
        Toast.makeText(this, "on Destroy called", Toast.LENGTH_SHORT).show();
    }
}
