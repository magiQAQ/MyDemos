package com.magi.mydemos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class RevealActivity extends AppCompatActivity {

    private static final String TAG = "RevealActivity";

    private View view;
    private CheckBox checkbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);
        view = findViewById(R.id.view);
        checkbox = findViewById(R.id.checkbox);
    }


    public void onClick(View view) {
        final boolean playAnimation = checkbox.isChecked();
        switch (view.getId()) {
            case R.id.buttonChangeVisibility:
                handleChangeVisibility(playAnimation);
        }
    }

    private void handleChangeVisibility(boolean playAnimation) {
        Log.d(TAG, "handleChangeVisibility called with: playAnimation =[" + playAnimation + "]");
        Log.d(TAG, "handleChangeVisibility:" + view.isShown());
        if (playAnimation) {
            if (view.isShown()) {
                revealExit();
            } else {
                revealEnter();
            }
        } else {
            if (view.isShown()) {
                view.setVisibility(View.INVISIBLE);
            } else {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    private void revealEnter() {
        int centerX = view.getWidth();
        int centerY = view.getHeight();
        int radius = (int) Math.hypot(centerX, centerY);
        Animator animator = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, 0, radius);
        animator.setDuration(1000);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
            }
        });
        animator.start();
    }

    private void revealExit() {
        int centerX = view.getWidth();
        int centerY = view.getHeight();
        int radius = (int) Math.hypot(centerX, centerY);
        Animator animator = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, radius, 0);
        animator.setDuration(1000);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });
        animator.start();
    }
}
