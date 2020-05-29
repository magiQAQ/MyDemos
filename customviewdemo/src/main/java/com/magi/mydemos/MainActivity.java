package com.magi.mydemos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.magi.mydemos.bezier.TouchPullView;

public class MainActivity extends AppCompatActivity {

    private static final float TOUCH_MOVE_MAX_Y = 600;
    private FrameLayout rootLayout;
    private TouchPullView touchPullView;
    private float touchMoveStartY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootLayout = findViewById(R.id.root_layout);
        touchPullView = findViewById(R.id.touch_pull_view);
        rootLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        touchMoveStartY = motionEvent.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float y = motionEvent.getY();
                        if (y >= touchMoveStartY) {
                            float moveSize = y - touchMoveStartY;
                            float progress = moveSize >= TOUCH_MOVE_MAX_Y ? 1 : moveSize / TOUCH_MOVE_MAX_Y;
                            touchPullView.setProgress(progress);
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        touchPullView.release();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}
