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
    private Button backButton;
    private TextView actionDuration;
    public notifyClockChangeToActivity notifyClockInterface;

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
                activateClock();
                notifyClockInterface.notififyClockListChange(clockInstance);
                fragmentManager.popBackStack();
                ((FloatingActionButton) mainActivity.findViewById(R.id.add_clock_button)).show();
            }

        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


}
