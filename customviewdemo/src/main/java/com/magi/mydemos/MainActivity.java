package com.magi.mydemos;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RoundProgressBar roundProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roundProgressBar = findViewById(R.id.roundProgressBar);

        roundProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator progress = ObjectAnimator.ofInt(roundProgressBar, "progress", 0, 100);
                progress.setDuration(3000);
                progress.setInterpolator(new LinearInterpolator());
                progress.start();
            }
        });


    }
}
