package com.magi.mydemos;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class RoundProgressBar extends View {

    private static final String INSTANCE = "instance";
    private static final String KEY_PROGRESS = "key_progress";
    private int radius;
    private int color;
    private int lineWidth;

    private Paint paint;
    private int textSize;
    private int progress;

    public RoundProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        color = typedArray.getColor(R.styleable.RoundProgressBar_color, context.getResources().getColor(R.color.colorAccent));
        radius = (int) typedArray.getDimension(R.styleable.RoundProgressBar_radius, 0f);
        lineWidth = (int) typedArray.getDimension(R.styleable.RoundProgressBar_lineWidth, dp2px(2f));
        textSize = (int) typedArray.getDimension(R.styleable.RoundProgressBar_android_textSize, dp2px(20f));
        progress = typedArray.getInt(R.styleable.RoundProgressBar_android_progress, 0);
        typedArray.recycle();
        initPaint();
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

    private float dp2px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        //先画底部的圆
        paint.setStrokeWidth(lineWidth * 1.0f / 4);
        int cx = getWidth() / 2 + getPaddingLeft() / 2 - getPaddingRight() / 2;
        int cy = getHeight() / 2 + getPaddingTop() / 2 - getPaddingBottom() / 2;
        int r = radius != 0 ? radius : Math.min(getWidth() - getPaddingLeft() - getPaddingRight(),
                getHeight() - getPaddingTop() - getPaddingBottom()) / 2 - lineWidth / 2;
        canvas.drawCircle(cx, cy, r, paint);
        //再画表示进度的弧度
        RectF rectF = new RectF(getPaddingLeft() + lineWidth / 2,
                getPaddingTop() + lineWidth / 2,
                getWidth() - getPaddingRight() - lineWidth / 2,
                getHeight() - getPaddingBottom() - lineWidth / 2);
        float sweep = progress * 360f / 100;
        paint.setStrokeWidth(lineWidth);
        canvas.drawArc(rectF, -90, sweep, false, paint);
        //最后画文字
        String text = progress + "%";
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int textHeight = rect.height();
        canvas.drawText(text, cx, cy + textHeight / 2, paint);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_PROGRESS, progress);
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            Parcelable parcelable = bundle.getParcelable(INSTANCE);
            super.onRestoreInstanceState(parcelable);
            progress = bundle.getInt(KEY_PROGRESS);
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
