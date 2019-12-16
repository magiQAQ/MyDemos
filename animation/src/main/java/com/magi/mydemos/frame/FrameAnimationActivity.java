package com.magi.mydemos.frame;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.magi.mydemos.R;

public class FrameAnimationActivity extends AppCompatActivity {

    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);

        View view= findViewById(R.id.view);
        animationDrawable = (AnimationDrawable) view.getBackground();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnStart:
                animationDrawable.start();
                break;
            case R.id.btnStop:
                animationDrawable.stop();
                break;
        }
    }
}
