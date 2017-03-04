package com.projet.e4fi.notlate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity implements ClockSetterFragment.notifyClockChangeToActivity {
    private boolean isNew;
    private FloatingActionButton buttonAdd;
    private ListView clockList;
    private ClockSetterFragment clockSetter;
    private ArrayList<Clock> savedClocks;
    private ClocksAdapter clocksAdapter;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();

        isNew = false;
        clockList = (ListView) findViewById(R.id.clock_list);
        buttonAdd = (FloatingActionButton) findViewById(R.id.add_clock_button);

        savedClocks = new ArrayList<Clock>();
        clocksAdapter = new ClocksAdapter(getApplicationContext(), savedClocks);
        clockList.setAdapter(clocksAdapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAdd.hide();
                isNew = true;

                Clock newClock = new Clock();
                clockSetter = new ClockSetterFragment();
                clockSetter.setClockInstance(newClock);

                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.enter_grow, R.animator.exit_shrink, R.animator.enter_grow, R.animator.exit_shrink)
                        .addToBackStack(null)
                        .add(R.id.fragmentFrame, clockSetter, "clockSetterFragment")
                        .commit();
            }
        });

        clockList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                buttonAdd.hide();
                isNew = false;

                clockSetter = new ClockSetterFragment();
                clockSetter.setClockInstance((Clock) clockList.getItemAtPosition(position));

                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.enter_grow, R.animator.exit_shrink, R.animator.enter_grow, R.animator.exit_shrink)
                        .addToBackStack(null)
                        .add(R.id.fragmentFrame, clockSetter, "clockSetterFragment")
                        .commit();
            }
        });
        clockList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Supprimer l'alarme")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Etes-vous sûr de supprimer cet alarme ?")
                        .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                savedClocks.remove(position);
                                clocksAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        int count = fragmentManager.getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else if (count <= 1) {
            fragmentManager.popBackStack();
            buttonAdd.show();
        } else {
            fragmentManager.popBackStack();
        }
    }

    @Override
    public void notififyClockListChange(Clock clock) {
        if (isNew)
            savedClocks.add(0, clock); //permet l'ajout a la liste des clocks si le reveil n'existait pas deja
        clocksAdapter.notifyDataSetChanged();
    }
//    private static void toggleVisibility(View... views) {
//        for (View view : views) {
//            boolean isVisible = view.getVisibility() == View.VISIBLE;
//            view.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
//        }
//    }
}
