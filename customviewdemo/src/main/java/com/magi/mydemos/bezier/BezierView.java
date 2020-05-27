package com.magi.mydemos.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class BezierView extends View {
    private Path bezier = new Path();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public BezierView(Context context) {
        super(context);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        //初始化画笔
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);


    }

    private void initBezier() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
