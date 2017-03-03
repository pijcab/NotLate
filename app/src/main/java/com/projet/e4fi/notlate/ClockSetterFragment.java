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

public class ClockSetterFragment extends Fragment {
    private TimePicker timer;
    private EditText destination;
    private Spinner toEvade;
    private Spinner transport;
    private Button okButton;
    private Activity mainActivity;


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
        actionsEdit = (ImageButton) res.findViewById(R.id.actions_edit_button);
        okButton = (Button) res.findViewById(R.id.button_ok);

        timer.setIs24HourView(true);
        timer.setCurrentHour(Calendar.HOUR_OF_DAY);

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

            }
        });
        return res;
    }

    @SuppressWarnings("deprecation")
    public void setDisplayedClock(Clock clock) {
        timer.setCurrentHour(clock.getArrivalDateHour());
        timer.setCurrentMinute(clock.getArrivalDateMinute());


    }


}
