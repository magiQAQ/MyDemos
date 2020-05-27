package com.magi.mydemos.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TestBezier extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path path = new Path();

    public TestBezier(Context context) {
        super(context);
        init();
    }

    public TestBezier(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestBezier(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TestBezier(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        path.moveTo(100, 100);
        path.lineTo(300, 300);

        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        path.quadTo(500, 100, 700, 300);
        path.moveTo(300, 300);
        path.cubicTo(200, 300, 400, 0, 800, 800);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }
}
