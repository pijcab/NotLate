package com.projet.e4fi.notlate;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private RelativeLayout mainView;
    private Button button2;
    private LinearLayout list;
    private CustomFrameLayout frame;
    private Fragment clockSetter;
    private List<Clock> savedClocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainView = (RelativeLayout) findViewById(R.id.activity_main);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        list = (LinearLayout) findViewById(R.id.list);

        frame = new CustomFrameLayout(this);
        frame.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        frame.setId(View.generateViewId());
        mainView.addView(frame, 1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                clockSetter = new ClockSetting();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.enter_grow, R.animator.exit_shrink)
                        .add(frame.getId(), clockSetter)
                        .commit();
                /*
                FragmentManager fragmentManager = getFragmentManager();
                clockSetter = new ClockSetting();
                //fragmentManager.beginTransaction().add(frame.getId(), new ClockSetting())
                fragmentManager.beginTransaction().add(frame.getId(), clockSetter)
                .commit();
                toggleVisibility(list);
                //clockSetter = fragmentManager.findFragmentById(R.id.clockSetter);
                */
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (frame.isActivated())
            super.onBackPressed();
    }

    private static void toggleVisibility(View... views) {
        for (View view : views) {
            boolean isVisible = view.getVisibility() == View.VISIBLE;
            view.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
        }
    }
}
