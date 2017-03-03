package com.projet.e4fi.notlate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


class ActionsAdapter extends ArrayAdapter {
    public ActionsAdapter(@NonNull Context context, @NonNull ArrayList<Action> actions) {
        super(context, 0, actions);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Action action = (Action) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.action_element, parent, false);
        }
        TextView actionName = (TextView) convertView.findViewById(R.id.action_name);
        TextView actionDuration = (TextView) convertView.findViewById(R.id.action_duration);

        if (action != null) {
            actionName.setText(action.getName());
            actionDuration.setText(String.valueOf(action.getHours() + " H : " + String.valueOf(action.getHours() + " min")));
        }

        return convertView;
    }
}
