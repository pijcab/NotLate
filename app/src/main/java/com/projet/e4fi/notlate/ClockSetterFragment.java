package com.projet.e4fi.notlate;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class ClockSetterFragment extends Fragment {
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
    private TextView actionDuration;
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            addClockInterface = (addClockToActivity) activity;
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
        depart = (EditText) res.findViewById((R.id.Depart));
        actionDuration = (TextView) res.findViewById((R.id.action_duration));


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
                activateClock();
                addClockInterface.addClock(clockInstance);
                fragmentManager.popBackStack();
                ((FloatingActionButton) mainActivity.findViewById(R.id.add_clock_button)).show();
            }

        });
        return res;
    }

    public void computeWakeUpTime() {
    }

    public void setOptionsToClock(Clock clock) {
        clock.setArrivalHour(timer.getCurrentHour());
        clock.setArrivalMinute(timer.getCurrentMinute());
        clock.setDestination(destination.getText().toString());
        clock.setDepart(depart.getText().toString());
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
//    ringTone.setSelection();

        lundi.setChecked(clockInstance.getDaysToRing()[clockInstance.MONDAY]);
        mardi.setChecked(clockInstance.getDaysToRing()[clockInstance.TUESDAY]);
        mercredi.setChecked(clockInstance.getDaysToRing()[clockInstance.WEDNESDAY]);
        jeudi.setChecked(clockInstance.getDaysToRing()[clockInstance.THURSDAY]);
        vendredi.setChecked(clockInstance.getDaysToRing()[clockInstance.FRIDAY]);
        samedi.setChecked(clockInstance.getDaysToRing()[clockInstance.SATURDAY]);
        dimanche.setChecked(clockInstance.getDaysToRing()[clockInstance.SUNDAY]);

        boolean[] tab = clockInstance.getDaysToRing();

        lundi.setChecked(tab[0]);
        mardi.setChecked(tab[1]);
        mercredi.setChecked(tab[2]);
        jeudi.setChecked(tab[3]);
        vendredi.setChecked(tab[4]);
        samedi.setChecked(tab[5]);
        dimanche.setChecked(tab[6]);
//        toEvade.setSelection(clockInstance.getSelectedAvoid());
//        transport.setSelection(clockInstance.getSelectedTransport());

    }


}
