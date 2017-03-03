package com.projet.e4fi.notlate;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ActionsSetterFragment extends Fragment {
    private ArrayList<Action> actionsList;
    private Button okButton;
    private ActionsAdapter actionsAdapter;
    private ListView listView;
    public addActionInterface addActionsToClock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View res = inflater.inflate(R.layout.set_actions_fragment, container, false);
        listView = (ListView) res.findViewById(R.id.actions_list);
        okButton = (Button) res.findViewById(R.id.button_ok);

        actionsList = new ArrayList<Action>();
        Action test = new Action("brosser dents", 0, 10);
        actionsList.add(test);

        actionsAdapter = new ActionsAdapter(getActivity().getApplicationContext(), actionsList);
        listView.setAdapter(actionsAdapter);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return res;
    }

    public interface addActionInterface {
        public void addActions(ArrayList<Action> actions);
    }
}
