package com.magi.mydemos.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class TouchPullView extends View {
    //圆的画笔
    private Paint circlePaint;
    //圆的半径
    private float circleRadius = 40;
    //圆的中心点坐标
    private float circlePointX;
    private float circlePointY;
    //拖动的进度
    private float progress;
    //可拖动的高度
    private float dragHeight = 600;
    //目标宽度
    private int targetWidth = 200;
    //贝塞尔曲线的路径和画笔
    private Path bezierPath = new Path();
    private Paint bezierPaint;
    //中心点最终高度,决定控制点的Y坐标
    private int targetGravityHeight = 10;
    //角度变换 0~135度
    private int tangentAngle = 120;


    public TouchPullView(Context context) {
        super(context);
        init();
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        //初始化圆的画笔
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置抗锯齿
        circlePaint.setAntiAlias(true);
        //设置防抖动
        circlePaint.setDither(true);
        //设置填充方式
        circlePaint.setStyle(Paint.Style.FILL);
        //颜色
        circlePaint.setColor(0xFF000000);


        //初始化贝塞尔路径的画笔
        bezierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bezierPaint.setAntiAlias(true);
        bezierPaint.setDither(true);
        bezierPaint.setStyle(Paint.Style.FILL);
        bezierPaint.setColor(0xFF0000FF);
    }

    /**
     * 测量时触发
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //先测量宽度
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        //再测量高度
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = (int) ((dragHeight * progress + 0.5f) + getPaddingStart() + getPaddingEnd());
        int height = (int) ((dragHeight * progress + 0.5f) + getPaddingTop() + getPaddingBottom());

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(width, widthSize);
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            height = Math.min(height, heightSize);
        }

        setMeasuredDimension(width, height);
    }

    /**
     * 当大小改变时触发
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //当高度变化时进行路径更新
        updatePathLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画Bezier曲线
        canvas.drawPath(bezierPath, bezierPaint);
        //画圆
        canvas.drawCircle(circlePointX, circlePointY, circleRadius, circlePaint);
    }

    /**
     * 设置进度
     */
    public void setProgress(float progress) {
        Log.i("progress", String.valueOf(progress));
        this.progress = progress;
        requestLayout();
    }

    /**
     * 更新路径等相关操作
     */
    private void updatePathLayout() {
        //获取进度
        final float progress = this.progress;
        //获取可绘制区域高度宽度
        final float w = getValueByLine(getWidth(), targetWidth, progress);
        final float h = getValueByLine(0, dragHeight, progress);
        // x对称轴的参数
        final float cPointX = getWidth() / 2f;
        // 圆的半径
        final float cRadius = circleRadius;
        // 圆的圆心坐标
        final float cPointY = h - cRadius;
        // 控制点结束的Y的值
        final float endControlY = targetGravityHeight;
        // 更新圆的坐标
        circlePointX = cPointX;
        circlePointY = cPointY;

        final Path path = bezierPath;
        //重置
        path.reset();
        path.moveTo(0, 0);

        //左边部分的结束点和控制点
        float leftEndPointX, leftEndPointY;
        float leftControlPointX, leftControlPointY;

        //角度换算成弧度
        double radian = Math.toRadians(getValueByLine(0, tangentAngle, progress));
        float x = (float) (Math.sin(radian) * cRadius);
        float y = (float) (Math.sin(radian) * cRadius);

        leftEndPointX = cPointX - x;
        leftEndPointY = cPointY + y;

        leftControlPointY = getValueByLine(0, endControlY, progress);
        float tHeight = leftEndPointY - leftControlPointY;
        float tWidth = (float) (tHeight / Math.tan(radian));
        leftControlPointX = leftEndPointX - tWidth;

        //左侧bezier曲线
        path.quadTo(leftControlPointX, leftControlPointY, leftEndPointX, leftEndPointY);
        //path链接到画布右边
        path.lineTo(cPointX + (cPointX - leftEndPointX), leftEndPointY);
        //根据左侧的点,通过对称换算出右侧的点
        path.quadTo(cPointX + (cPointX - leftControlPointX), leftControlPointY, w, 0);
    }

    /**
     * 通过进度得到实际的值
     */
    private float getValueByLine(float start, float end, float progress) {
        return start + (end - start) * progress;
    }
}
