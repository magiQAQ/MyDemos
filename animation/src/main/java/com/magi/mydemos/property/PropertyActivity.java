package com.magi.mydemos.property;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;

import androidx.appcompat.app.AppCompatActivity;

import com.magi.mydemos.R;

public class PropertyActivity extends AppCompatActivity {

    private static final String TAG = "PropertyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);
    }

    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.btnValueAnimator:
//                ValueAnimator valueAnimator = ValueAnimator.ofInt(0,100);
//                valueAnimator.setInterpolator(new LinearInterpolator());
//                valueAnimator.setDuration(100);
//                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        float animatedFraction = animation.getAnimatedFraction();
//                        int animatedValue = (int)animation.getAnimatedValue();
//                        Log.d(TAG,"onAnimationUpdate:"+String.format("%.3f %d", animatedFraction, animatedValue));
//                    }
//                });
//                valueAnimator.start();
//                break;
            case R.id.viewAlphaAnimation:
                Animator alphaAnimator = AnimatorInflater.loadAnimator(this, R.animator.alpha);
                alphaAnimator.setTarget(view);
                alphaAnimator.start();
                break;
            case R.id.viewScaleAnimation:
                ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 3.0f).start();
                break;
            case R.id.viewTranslateAnimation:
                ViewPropertyAnimator viewTranslateAnimator = view.animate();
                viewTranslateAnimator.translationX(500f);
                viewTranslateAnimator.start();
                break;
            case R.id.viewSetAnimation:
                //第一种
//                ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(view, "rotation", 0, 720);
//                rotateAnimator.setDuration(5000);
//                ObjectAnimator moveAnimator = ObjectAnimator.ofFloat(view, "x", 0, 500);
//                moveAnimator.setDuration(5000);
//                AnimatorSet animatorSet = new AnimatorSet();
//                animatorSet.playTogether(rotateAnimator,moveAnimator);
//                animatorSet.start();

                //第二种
                view.animate().translationX(1000).setDuration(1000).start();
                view.animate().rotation(720).setDuration(1000).start();

                break;
        }
    }
}
