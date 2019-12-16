package com.magi.mydemos.view;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.magi.mydemos.R;

public class ViewAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewAlphaAnimation:
                Animation alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha);
                view.startAnimation(alphaAnimation);
                break;
            case R.id.viewScaleAnimation:
                Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);
                view.startAnimation(scaleAnimation);
                break;
            case R.id.viewTranslateAnimation:
                Animation translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate);
                view.startAnimation(translateAnimation);
                break;
            case R.id.viewRotateAnimation:
                Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
                view.startAnimation(rotateAnimation);
                break;
        }
    }
}
