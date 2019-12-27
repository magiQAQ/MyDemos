package com.magi.mydemos;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class TestView extends View {

    private String stringTest = "magi";
    private Paint paint;
    private static final String INSTANCE = "instance";

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TestView);
        boolean booleanTest = typedArray.getBoolean(R.styleable.TestView_test_boolean, false);
        int integerTest = typedArray.getInt(R.styleable.TestView_test_integer, -1);
        float dimensionTest = typedArray.getDimension(R.styleable.TestView_test_dimension, 0f);
        int enumTest = typedArray.getInt(R.styleable.TestView_test_enum, 1);
        int count = typedArray.getIndexCount();

        for (int i = 0; i < count; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.TestView_test_string:
                    stringTest = typedArray.getString(R.styleable.TestView_test_string);
                    break;
            }
        }

        //String stringTest = typedArray.getString(R.styleable.TestView_test_string);
        Log.e("TestView", "booleanTest:" + booleanTest + "\n"
                + "integerTest:" + integerTest + "\n"
                + "dimensionTest:" + dimensionTest + "\n"
                + "enumTest:" + enumTest + "\n"
                + "stringTest:" + stringTest);

        typedArray.recycle();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.rgb(0XFF, 0, 0));
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //MeasureSpec.getMode()有三种情况
        //1.MeasureSpec.EXACTLY -> 精确尺寸，控件的宽高指定大小或者为match_parent
        //2.MeasureSpec.AT_MOST -> 最大尺寸，控件的宽高为WRAP_CONTENT，控件大小一般随着控件的子空间或内容进行变化，
        // 但是控件尺寸不能超过父控件允许的最大尺寸
        //3.MeasureSpec.UNSPECIFIED -> 未指定尺寸,需要多少就给多少,比如ScrollView的高度

        //先测量宽度
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width;
        //EXACTLY表示用户确定的大小
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int needWidth = measureWidth() + getPaddingLeft() + getPaddingRight();
            //遇到wrap_content之类的情况时
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(needWidth, widthSize);
            } else {//MeasureSpec.UNSPECIFIED
                width = needWidth;
            }
        }

        //再测量高度
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height;
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int needHeight = measureHeight() + getPaddingTop() + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(heightSize, needHeight);
            } else {
                height = needHeight;
            }
        }
        setMeasuredDimension(width, height);
    }

    private int measureWidth() {
        return 0;
    }

    private int measureHeight() {
        return 0;
    }

    private static final String KEY_TEXT = "key_text";
    private boolean isFirstDraw = true;

    @Override
    protected void onDraw(Canvas canvas) {
        int radius = Math.min(getWidth(), getHeight()) / 2 - (int) paint.getStrokeWidth() / 2;
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, paint);
        paint.setStrokeWidth(1);
        canvas.drawLine(0, 0, getWidth(), getHeight(), paint);
        canvas.drawLine(getWidth(), 0, 0, getHeight(), paint);
        paint.setTextSize(50f);
        canvas.drawText(stringTest, 0, stringTest.length(), 0, paint.getTextSize(), paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        stringTest = "12345";
        invalidate();
        return super.onTouchEvent(event);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TEXT, stringTest);
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            Parcelable parcelable = bundle.getParcelable(INSTANCE);
            super.onRestoreInstanceState(parcelable);
            stringTest = bundle.getString(KEY_TEXT);
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
