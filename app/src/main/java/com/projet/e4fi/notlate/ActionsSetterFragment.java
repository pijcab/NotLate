package com.projet.e4fi.notlate;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ActionsSetterFragment extends Fragment {
    private ArrayList<Action> actionsList;
    private Clock clockInstance;
    private Button okButton;
    private Button returnButton;
    private FloatingActionButton addButton;
    private ActionsAdapter actionsAdapter;
    private ListView actionsListView;
    private FragmentManager fragmentManager;
    private Activity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = getActivity();
        fragmentManager = mainActivity.getFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View res = inflater.inflate(R.layout.set_actions_fragment, container, false);
        actionsListView = (ListView) res.findViewById(R.id.actions_list);
        okButton = (Button) res.findViewById(R.id.button_ok);
        returnButton = (Button) res.findViewById(R.id.button_return);
        addButton = (FloatingActionButton) res.findViewById(R.id.add_action);

        actionsList = clockInstance.getActionsList();
        if (actionsList.size() == 0) {  // Populate list for testing purposes
            Action b = new Action("Déjeuner", 0, 5);
            Action a = new Action("Brosser les dents", 0, 5);
            actionsList.add(a);
            actionsList.add(b);
        }

        actionsAdapter = new ActionsAdapter(getActivity().getApplicationContext(), actionsList);
        actionsListView.setAdapter(actionsAdapter);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clockInstance.setActionsList(actionsList);
                fragmentManager.popBackStack();

                ClockSetterFragment clockSetter = new ClockSetterFragment();
                clockSetter.setClockInstance(clockInstance);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionsList.add(0, new Action());
                actionsAdapter.notifyDataSetChanged();
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });

        return res;
    }


    public Clock getClockInstance() {
        return clockInstance;
    }

    public void setClockInstance(Clock clockInstance) {
        this.clockInstance = clockInstance;
    }
}
