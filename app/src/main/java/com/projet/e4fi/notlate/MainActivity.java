package com.projet.e4fi.notlate;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton buttonAdd;
    private RelativeLayout mainView;
    private ListView clockList;
    private CustomFrameLayout frame;
    private ClockSetterFragment clockSetter;
    private ArrayList<Clock> savedClocks;
    private ClocksAdapter clocksAdapter;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();

        mainView = (RelativeLayout) findViewById(R.id.activity_main);
        clockList = (ListView) findViewById(R.id.clock_list);
        buttonAdd = (FloatingActionButton) findViewById(R.id.add_clock_button);

        Clock test = new Clock();
        test.setName("TEST");
        test.setArrivalDate(Calendar.getInstance().getTime());
        Clock test2 = new Clock();
        test2.setName("TEST 2");
        test2.setArrivalDate(Calendar.getInstance().getTime());

        savedClocks = new ArrayList<Clock>();
        savedClocks.add(test);
        savedClocks.add(test2);

        clocksAdapter = new ClocksAdapter(getApplicationContext(), savedClocks);
        clockList.setAdapter(clocksAdapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("TAG", "onClick: button CLICKED");
                buttonAdd.hide();
                Clock clock = new Clock();

                clockSetter = new ClockSetterFragment();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.enter_grow, R.animator.exit_shrink, R.animator.enter_grow, R.animator.exit_shrink)
                        .addToBackStack(null)
                        .add(R.id.fragmentFrame, clockSetter, "clockSetterFragment")
                        .commit();


//                frame = new CustomFrameLayout(getApplicationContext());
//                frame.setLayoutParams(new FrameLayout.LayoutParams(
//                        FrameLayout.LayoutParams.MATCH_PARENT,
//                        FrameLayout.LayoutParams.MATCH_PARENT));
//                frame.setId(View.generateViewId());
//                mainView.addView(frame, 1);
            }
        });

        clockList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clockSetter = new ClockSetterFragment();
                clockSetter.setDisplayedClock((Clock) clockList.getItemAtPosition(position));

                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.enter_grow, R.animator.exit_shrink, R.animator.enter_grow, R.animator.exit_shrink)
                        .addToBackStack(null)
                        .add(R.id.fragmentFrame, clockSetter, "clockSetterFragment")
                        .commit();
            }
        });

                /*
                FragmentManager fragmentManager = getFragmentManager();
                clockSetter = new ClockSetterFragment();
                //fragmentManager.beginTransaction().add(frame.getId(), new ClockSetterFragment())
                fragmentManager.beginTransaction().add(frame.getId(), clockSetter)
                .commit();
                toggleVisibility(clockList);
                //clockSetter = fragmentManager.findFragmentById(R.id.clockSetter);
                */


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

    public ArrayList<Clock> getSavedClocks() {
        return savedClocks;
    }

    public void setSavedClocks(ArrayList<Clock> savedClocks) {
        this.savedClocks = savedClocks;
    }

    private static void toggleVisibility(View... views) {
        for (View view : views) {
            boolean isVisible = view.getVisibility() == View.VISIBLE;
            view.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
        }
    }
}
