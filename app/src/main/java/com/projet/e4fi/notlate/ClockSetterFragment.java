package com.projet.e4fi.notlate;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;

public class ClockSetterFragment extends Fragment implements LocationListener {

    private Activity mainActivity;
    private Clock clockInstance;
    private TimePicker timer;
    private EditText destination;
    private EditText depart;
    private Spinner toEvade;
    private Spinner transport;
    private Spinner ringTone;
    private ToggleButton lundi;
    private ToggleButton mardi;
    private ToggleButton mercredi;
    private ToggleButton jeudi;
    private ToggleButton vendredi;
    private ToggleButton samedi;
    private ToggleButton dimanche;
    private Button okButton;
    private Button backButton;
    private TextView actionDuration;
    public notifyClockChangeToActivity notifyClockInterface;
    private int choose_sound;
    private AlarmDB myDatabase;
    private PendingIntent pendingintent;
    AlarmManager Alarm_Manager;
    Calendar heure_reveil;
    Calendar temps_total;
    Calendar heure_arrive;
    ArrayList<AlarmModel> arrayAlarmM;
    Context context;


    private int val = 0;
    int res = 0;
    private ImageButton actionsEdit;
    private ActionsSetterFragment actionSetter;
    private FragmentManager fragmentManager;

    public ClockSetterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = getActivity();
        fragmentManager = mainActivity.getFragmentManager();
        Alarm_Manager = (AlarmManager) mainActivity.getSystemService(ALARM_SERVICE);
        context = mainActivity.getApplicationContext();
        myDatabase = new AlarmDB(context);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            notifyClockInterface = (notifyClockChangeToActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View res = inflater.inflate(R.layout.fragment_clock_setting, container, false);

        //region finding all the Views
        timer = (TimePicker) res.findViewById(R.id.timePicker);
        timer.setIs24HourView(true);
        timer.setCurrentHour(Calendar.HOUR_OF_DAY);
        actionsEdit = (ImageButton) res.findViewById(R.id.actions_edit_button);
        okButton = (Button) res.findViewById(R.id.button_ok);
        backButton = (Button) res.findViewById(R.id.button_return);
        lundi = (ToggleButton) res.findViewById(R.id.lundi_button);
        mardi = (ToggleButton) res.findViewById(R.id.mardi_button);
        mercredi = (ToggleButton) res.findViewById(R.id.mercredi_button);
        jeudi = (ToggleButton) res.findViewById(R.id.jeudi_button);
        vendredi = (ToggleButton) res.findViewById(R.id.vendredi_button);
        samedi = (ToggleButton) res.findViewById(R.id.samedi_button);
        dimanche = (ToggleButton) res.findViewById(R.id.dimanche_button);
        toEvade = (Spinner) res.findViewById(R.id.to_evade);
        transport = (Spinner) res.findViewById(R.id.mean_transport);
        ringTone = (Spinner) res.findViewById(R.id.ring_tone);
        destination = (EditText) res.findViewById((R.id.Destination));
        depart = (EditText) res.findViewById((R.id.Depart));
        actionDuration = (TextView) res.findViewById((R.id.action_duration_value));
        //endregion

        setDisplayedClock();

        actionsEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionSetter = new ActionsSetterFragment();
                actionSetter.setClockInstance(clockInstance);
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.animator.enter_grow, R.animator.exit_shrink, R.animator.enter_grow, R.animator.exit_shrink)
                        .addToBackStack(null)
                        .add(R.id.actions_frame, actionSetter, "actionsSetterFragment")
                        .commit();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computeWakeUpTime();
                setOptionsToClock(clockInstance);
                //activateClock();
                notifyClockInterface.notififyClockListChange(clockInstance);
                fragmentManager.popBackStack();
                ((FloatingActionButton) mainActivity.findViewById(R.id.add_clock_button)).show();
                mainActivity.findViewById(R.id.stop_alarm).setClickable(true);
                mainActivity.findViewById(R.id.stop_alarm).animate().alpha(1.0f).setDuration(200);
            }

        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
                ((FloatingActionButton) mainActivity.findViewById(R.id.add_clock_button)).show();
                mainActivity.findViewById(R.id.stop_alarm).setClickable(true);
                mainActivity.findViewById(R.id.stop_alarm).animate().alpha(1.0f).setDuration(200);
            }
        });
        return res;
    }


    public void computeWakeUpTime() {
        String origin = depart.getText().toString();
        String dest = destination.getText().toString();
        String mode = transport.getSelectedItem().toString();
        int trajetDuration = -1;
        heure_arrive = Calendar.getInstance();
        temps_total = Calendar.getInstance();

        heure_arrive.set(Calendar.HOUR, timer.getCurrentHour());
        heure_arrive.set(Calendar.MINUTE, timer.getCurrentMinute());
        this.recupTempsTrajet(origin, dest, mode, getActivity().getApplicationContext());
        Log.e("res tmp traj", String.valueOf(res));
    }

    public void recupTempsTrajet(String origin, String dest, String mode, final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        origin = origin.replaceAll("\\s", "+");
        dest = dest.replaceAll("\\s", "+");
        if (mode.equals("Voiture")) {
            mode = "driving";
        }
        if (mode.equals("Vélo")) {
            mode = "bicycling";
        }
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origin + "&destinations=" + dest + "&mode=" + mode + "&language=fr-FR&key=AIzaSyArtd_4Nsl-7XN8dj4oZzJPQtHU0d1Be9U";
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.wtf("before res dans try", String.valueOf(res));
                            res = response.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration").getInt("value");
                            if (res != 0) {
                                temps_total = TransfromIntoCalendar(res);
                                Log.e("Heure trajet", String.valueOf((temps_total.get(Calendar.HOUR))));
                                Log.e("minute trajet", String.valueOf((temps_total.get(Calendar.HOUR))));
                                sumActions(temps_total);
                                heure_reveil = CalculHeureReveil(heure_arrive, temps_total);
                                SetAlarm(heure_reveil);
                                res = 0;
                            } else {
                                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                                        .setTitle("Erreur")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setMessage("La destination est inconnue")
                                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();
                            }
                            Log.wtf("after res dans try", String.valueOf(res));

                        } catch (JSONException e) {
                            Log.e("Dans le exception", "excdfs");
                            e.printStackTrace();
                            res = -1;

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("erreur");
            }
        });
        queue.add(stringRequest);
    }

    private void sumActions(Calendar cal_trajet) {
        int sum_hour = 0;
        int sum_minutes = 0;

        for (Action a : clockInstance.getActionsList()) {
            sum_hour += a.getHours();
            sum_minutes += a.getMinutes();
        }

        cal_trajet.add(Calendar.HOUR, sum_hour);
        cal_trajet.add(Calendar.MINUTE, sum_minutes);
    }

    private Calendar TransfromIntoCalendar(int temps) {
        int heure, minutes;
        Calendar calendar = Calendar.getInstance();

        heure = temps / 3600;
        minutes = temps / 60;

        Log.e("Heure TIC", String.valueOf(heure));
        Log.e("Minutes TIC", String.valueOf(minutes));

        calendar.set(Calendar.HOUR, heure);
        calendar.set(Calendar.MINUTE, minutes);

        return calendar;
    }

    private Calendar CalculHeureReveil(Calendar heure_arrive, Calendar temps_total) {
        Calendar heure_reveil = Calendar.getInstance();

        Log.e("heure trajet", String.valueOf(temps_total.get(Calendar.HOUR)));
        Log.e("minute trajet", String.valueOf(temps_total.get(Calendar.MINUTE)));

        Log.e("heure arrive", String.valueOf(heure_arrive.get(Calendar.HOUR)));
        Log.e("minutes arrive", String.valueOf(heure_arrive.get(Calendar.MINUTE)));


        int heures = heure_arrive.get(Calendar.HOUR) - temps_total.get(Calendar.HOUR);
        int minutes = heure_arrive.get(Calendar.MINUTE) - temps_total.get(Calendar.MINUTE);

        heure_reveil.set(Calendar.HOUR, heures);
        heure_reveil.set(Calendar.MINUTE, minutes);

        Log.e("heure reveil", String.valueOf(heure_reveil.get(Calendar.HOUR)));
        Log.e("minute reveil", String.valueOf(heure_reveil.get(Calendar.MINUTE)));

        return heure_reveil;
    }

    public void setOptionsToClock(Clock clock) {
        clock.setArrivalHour(timer.getCurrentHour());
        clock.setArrivalMinute(timer.getCurrentMinute());
        clock.setDestination(destination.getText().toString());
        clock.setDepart(depart.getText().toString());
        clock.setSelectedAvoid(toEvade.getSelectedItemPosition());
        clock.setSelectedTransport(transport.getSelectedItemPosition());
        clock.setRingTone(ringTone.getSelectedItemPosition());

        boolean[] tab = new boolean[7];
        if (lundi.isChecked()) {
            tab[Clock.MONDAY] = true;
        }
        if (mardi.isChecked()) {
            tab[Clock.TUESDAY] = true;
        }
        if (mercredi.isChecked()) {
            tab[Clock.WEDNESDAY] = true;
        }
        if (jeudi.isChecked()) {
            tab[Clock.THURSDAY] = true;
        }
        if (vendredi.isChecked()) {
            tab[Clock.FRIDAY] = true;
        }
        if (samedi.isChecked()) {
            tab[Clock.SATURDAY] = true;
        }
        if (dimanche.isChecked()) {
            tab[Clock.SUNDAY] = true;
        }
        clock.setDaysToRing(tab);

    }

    public void activateClock() {
        SetAlarm(heure_reveil);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public interface notifyClockChangeToActivity {
        void notififyClockListChange(Clock clock);
    }

    public TextView getActionDuration() {
        return actionDuration;
    }

    public Clock getClockInstance() {
        return clockInstance;
    }

    public void setClockInstance(Clock clockInstance) {
        this.clockInstance = clockInstance;
    }

    @SuppressWarnings("deprecation")
    public void setDisplayedClock() {
        timer.setCurrentHour(clockInstance.getArrivalHour());
        timer.setCurrentMinute(clockInstance.getArrivalMinute());
        destination.setText(clockInstance.getDestination());
        depart.setText(clockInstance.getDepart());
        ringTone.setSelection(clockInstance.getRingTone());
        boolean[] tab = clockInstance.getDaysToRing();
        lundi.setChecked(tab[Clock.MONDAY]);
        mardi.setChecked(tab[Clock.TUESDAY]);
        mercredi.setChecked(tab[Clock.WEDNESDAY]);
        jeudi.setChecked(tab[Clock.THURSDAY]);
        vendredi.setChecked(tab[Clock.FRIDAY]);
        samedi.setChecked(tab[Clock.SATURDAY]);
        dimanche.setChecked(tab[Clock.SUNDAY]);

        int sumHour = 0;
        int sumMinutes = 0;
        for (Action a : clockInstance.getActionsList()) {
            sumHour += a.getHours();
            sumMinutes += a.getMinutes();
        }

        actionDuration.setText(sumHour + ":" + sumMinutes);
        toEvade.setSelection(clockInstance.getSelectedAvoid());
        transport.setSelection(clockInstance.getSelectedTransport());


    }

    //region Fonction localisation fonctionelle mais bloqué par les permissions
    /*public void getLocation() {

        int permissionCheck = ContextCompat.checkSelfPermission(mainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (ContextCompat.checkSelfPermission(mainActivity.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions(mainActivity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        }

        LocationManager locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), false);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location == null) {
            Toast.makeText(mainActivity, "Check your provider", Toast.LENGTH_SHORT).show();
        } else {
            Log.i("New Location", "lat: " + location.getLatitude());
            Log.i("New Location", "lng: " + location.getLongitude());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("Location", "lat: " + location.getLatitude());
        Log.i("Location", "lng: " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }*/

    /*Spinner spinner = (Spinner)mainActivity.findViewById(R.id.ring_tone);
    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.sonnerie, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
    spinner.setAdapter(adapter);
    //On créer un OnClickListener
    spinner.setOnItemSelectedListener(this);

}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        choose_sound = (int) id;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/
    //endregion


    private PendingIntent createPendingIntent(Context context, AlarmModel AlarmM) {
        Intent intent = new Intent(context, Alarm_Receiver.class);
        intent.putExtra("ID", AlarmM.id);
        intent.putExtra("DATE", AlarmM.Date);
        intent.putExtra("HOUR", AlarmM.Hour);
        intent.putExtra("MINUTES", AlarmM.Minutes);
        intent.putExtra("ID_MUSIQUE", AlarmM.MusiqueId);
        intent.putExtra("extra", "alarme activee");
        intent.putExtra("musique choisie", clockInstance.getRingTone());
        Log.e("id musique", String.valueOf(clockInstance.getRingTone()));

        return PendingIntent.getBroadcast(mainActivity, AlarmM.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private int GetLastAlarmId(ArrayList<AlarmModel> List_AlarmM) {
        int size, last_id, id;
        if (List_AlarmM != null) {
            size = List_AlarmM.size() - 1;
            last_id = arrayAlarmM.get(size).id;
            id = last_id + 1;
        } else {
            id = 0;
        }

        return id;
    }

    private int GetPendingId(PendingIntent PI, ArrayList<AlarmModel> alarms) {
        ArrayList<PendingIntent> arrayPending = new ArrayList<>();
        int id = -2;

        Log.e("dans le pending", "getid");

        if (alarms != null) {
            for (AlarmModel alarm : alarms) {
                PendingIntent pIntent = createPendingIntent(context, alarm);
                /*arrayPending.add(pendingintent);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pIntent);*/

                if (pIntent.equals(PI)) {
                    id = alarm.id;
                } else id = -1;

            }
        }

        return id;
    }

    public void cancelAlarms(Context context) {
        AlarmDB myDB = new AlarmDB(context);

        ArrayList<AlarmModel> alarms = myDB.getAlarms();

        if (alarms != null) {
            for (AlarmModel alarm : alarms) {
                PendingIntent pIntent = createPendingIntent(context, alarm);

                AlarmManager alarmManager = (AlarmManager) mainActivity.getSystemService(ALARM_SERVICE);
                alarmManager.cancel(pIntent);
            }
        }
    }

    private java.util.Calendar SetUpCalendar(Calendar cal) {
        java.util.Calendar now_calendar = java.util.Calendar.getInstance(Locale.FRANCE);
        now_calendar.set(java.util.Calendar.HOUR, cal.get(java.util.Calendar.HOUR));
        now_calendar.set(java.util.Calendar.MINUTE, cal.get(java.util.Calendar.MINUTE));
        now_calendar.set(java.util.Calendar.SECOND, 0);
        now_calendar.set(java.util.Calendar.DAY_OF_WEEK, cal.get(java.util.Calendar.DAY_OF_WEEK));

        return now_calendar;
    }

    private void SetAlarm(Calendar given_calendar) {
        //On initialise le Calendar qui sera utilise pour configurer le reveil
        //Il contient les informations (jour, heures, minutes) auxquels doit sonner le reveil
        java.util.Calendar now_calendar = java.util.Calendar.getInstance(Locale.FRANCE);
        now_calendar = SetUpCalendar(given_calendar);

        //Si l'heure a laquelle doit sonner le reveil est deja passer, on initialise le reveil pour la semaine prochaine
        if (now_calendar.before(java.util.Calendar.getInstance())) {
            now_calendar.add(java.util.Calendar.DAY_OF_WEEK, 7);
        }

        String time = "Reveil active à " + now_calendar.get(Calendar.HOUR) + " : " + now_calendar.get(Calendar.MINUTE);
        Toast.makeText(context, time, Toast.LENGTH_LONG).show();

        //On recupere les alarmes deja stocke dans la base de donnees
        arrayAlarmM = myDatabase.getAlarms();

        //On récupère le dernier ID utilse les alarmes dans la BDD
        int _id = GetLastAlarmId(arrayAlarmM);

        //On instancie l'objet AlarmM et on remplit ses differents champs
        AlarmModel AlarmM = new AlarmModel();
        AlarmM.BuildAlarmModel(now_calendar, _id, choose_sound);

        //On ajoute la nouvelle alarme dans la BDD
        myDatabase.createAlarm(AlarmM);

        //on cree un pendingIntent
        pendingintent = createPendingIntent(mainActivity, AlarmM);

        //On planifie l'alarme
        Alarm_Manager.set(AlarmManager.RTC_WAKEUP, now_calendar.getTimeInMillis(), pendingintent);

    }


}
