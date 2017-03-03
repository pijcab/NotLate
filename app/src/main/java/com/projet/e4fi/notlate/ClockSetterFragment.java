package com.projet.e4fi.notlate;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class ClockSetterFragment extends Fragment implements ActionsSetterFragment.addActionInterface {
    private Activity mainActivity;
    private Clock tmpClock;
    private TimePicker timer;
    private EditText destination;
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
    public addClockToActivity addClockInterface;


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

    }

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View res = inflater.inflate(R.layout.fragment_clock_setting, container, false);

        timer = (TimePicker) res.findViewById(R.id.timePicker);
        timer.setIs24HourView(true);
        timer.setCurrentHour(Calendar.HOUR_OF_DAY);
        actionsEdit = (ImageButton) res.findViewById(R.id.actions_edit_button);
        okButton = (Button) res.findViewById(R.id.button_ok);
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


        actionsEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("TAG", "onClick: Button CLICKED");

                actionSetter = new ActionsSetterFragment();
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
                tmpClock = new Clock();
                computeWakeUpTime();
                setOptionsToClock(tmpClock);
                activateClock();
                addClockInterface.addClock(tmpClock);
            }

        });
        return res;
    }

    @Override
    public void addActions(ArrayList<Action> actions) {

    }

    public void computeWakeUpTime() {
    }

    public void setOptionsToClock(Clock clock) {
        clock.setArrivalHour(timer.getCurrentHour());
        clock.setArrivalMinute(timer.getCurrentMinute());
        clock.setDestination(destination.getText().toString());
        clock.setSelectedAvoid(toEvade.getSelectedItem().toString());
        clock.setSelectedTransport(transport.getSelectedItem().toString());
        clock.setRingTone(ringTone.getSelectedItem().toString());

        boolean[] tab = new boolean[7];
        if (lundi.isChecked()) {
            tab[0] = true;
        }
        if (mardi.isChecked()) {
            tab[1] = true;
        }
        if (mercredi.isChecked()) {
            tab[2] = true;
        }
        if (jeudi.isChecked()) {
            tab[3] = true;
        }
        if (vendredi.isChecked()) {
            tab[4] = true;
        }
        if (samedi.isChecked()) {
            tab[5] = true;
        }
        if (dimanche.isChecked()) {
            tab[6] = true;
        }

        clock.setDaysToRing(tab);

    }

    public void activateClock() {

    }

    public interface addClockToActivity {
        void addClock(Clock clock);
    }

    @SuppressWarnings("deprecation")
    public void setDisplayedClock(Clock clock) {
        timer.setCurrentHour(clock.getArrivalHour());
        timer.setCurrentMinute(clock.getArrivalMinute());
        destination.setText(clock.getDestination());
//    ringTone.setSelection();

        lundi.setChecked(clock.getDaysToRing()[Clock.MONDAY]);
        mardi.setChecked(clock.getDaysToRing()[Clock.TUESDAY]);
        mercredi.setChecked(clock.getDaysToRing()[Clock.WEDNESDAY]);
        jeudi.setChecked(clock.getDaysToRing()[Clock.THURSDAY]);
        vendredi.setChecked(clock.getDaysToRing()[Clock.FRIDAY]);
        samedi.setChecked(clock.getDaysToRing()[Clock.SATURDAY]);
        dimanche.setChecked(clock.getDaysToRing()[Clock.SUNDAY]);

//        toEvade.setSelection(clock.getSelectedAvoid());
//        transport.setSelection(clock.getSelectedTransport());

    }


}
