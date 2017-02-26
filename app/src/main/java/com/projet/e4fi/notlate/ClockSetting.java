package com.projet.e4fi.notlate;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ClockSetting extends Fragment {

    public ClockSetting() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialize resources used by your fragment except those related to the user interface.

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clock_setting, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        /*
        Allocate “expensive” resources (in terms of battery life, monetary cost, etc.),
        such as registering for location updates, sensor updates, etc.
        */
    }

    @Override
    public void onPause() {
        super.onPause();
        /*
        Release “expensive” resources.
        Commit any changes that should be persisted beyond the current user session.
        */
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
