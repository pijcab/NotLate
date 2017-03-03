package com.projet.e4fi.notlate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


class ClocksAdapter extends ArrayAdapter {
    ClocksAdapter(@NonNull Context context, @NonNull ArrayList<Clock> clocks) {
        super(context, 0, clocks);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Clock clock = (Clock) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.clock_element, parent, false);
        }
        TextView clockName = (TextView) convertView.findViewById(R.id.clock_name);
        TextView arrivalHour = (TextView) convertView.findViewById(R.id.clock_time);

        if (clock != null) {
            clockName.setText(clock.getName());
            arrivalHour.setText(clock.getArrivalDateString());
        }
        return convertView;
    }
}
