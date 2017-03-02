package com.projet.e4fi.notlate;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton buttonAdd;
    private RelativeLayout mainView;
    private LinearLayout clockList;
    private CustomFrameLayout frame;
    private Fragment clockSetter;
    private List<Clock> savedClocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainView = (RelativeLayout) findViewById(R.id.activity_main);
        clockList = (LinearLayout) findViewById(R.id.clock_list);
        buttonAdd = (FloatingActionButton) findViewById(R.id.add_clock_button);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("TAG", "onClick: button CLICKED");
                Clock clock = new Clock();

                FragmentManager fragmentManager = getFragmentManager();
                clockSetter = new ClockSetting();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.enter_grow, R.animator.exit_shrink, R.animator.enter_grow, R.animator.exit_shrink)
                        .addToBackStack(null)
                        .add(R.id.frame, clockSetter, "clockSetterFragment")
                        .commit();


//                frame = new CustomFrameLayout(getApplicationContext());
//                frame.setLayoutParams(new FrameLayout.LayoutParams(
//                        FrameLayout.LayoutParams.MATCH_PARENT,
//                        FrameLayout.LayoutParams.MATCH_PARENT));
//                frame.setId(View.generateViewId());
//                mainView.addView(frame, 1);
            }
        });





                /*
                FragmentManager fragmentManager = getFragmentManager();
                clockSetter = new ClockSetting();
                //fragmentManager.beginTransaction().add(frame.getId(), new ClockSetting())
                fragmentManager.beginTransaction().add(frame.getId(), clockSetter)
                .commit();
                toggleVisibility(clockList);
                //clockSetter = fragmentManager.findFragmentById(R.id.clockSetter);
                */


    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }
    }

    private static void toggleVisibility(View... views) {
        for (View view : views) {
            boolean isVisible = view.getVisibility() == View.VISIBLE;
            view.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
        }
    }
}
