package com.android.quizapp.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.FloatRange;
import androidx.core.content.ContextCompat;

import com.android.quizapp.R;

/**
 * Created by Aashis on 31,March,2024
 */
public class CircularProgressView extends View {
    private Paint progressPaint;
    private Paint backgroundPaint;
    private RectF rect;
    private float startAngle = -90f;
    private float maxAngle = 360f;
    public int maxProgress = 100;
    private float diameter = 0f;
    private float angle = 0f;

    public CircularProgressView(Context context) {
        super(context);
        init();
    }

    public CircularProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.STROKE);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.STROKE);

        rect = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        backgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.progress_background));
        drawCircle(maxAngle, canvas, backgroundPaint);
        drawCircle(angle, canvas, progressPaint);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        diameter = Math.min(width, height);
        updateRect();
    }

    private void updateRect() {
        float strokeWidth = backgroundPaint.getStrokeWidth();
        rect.set(strokeWidth, strokeWidth, diameter - strokeWidth, diameter - strokeWidth);
    }

    private void drawCircle(float angle, Canvas canvas, Paint paint) {
        canvas.drawArc(rect, startAngle, angle, false, paint);
    }

    private float calculateAngle(float progress) {
        return maxAngle / maxProgress * progress;
    }

    public void setProgress(@FloatRange(from = 0.0, to = 100.0) float progress) {
        angle = calculateAngle(progress);
        invalidate();
    }

    public void setProgressColor(int color) {
        progressPaint.setColor(color);
        invalidate();
    }

    public void setProgressBackgroundColor(int color) {
        backgroundPaint.setColor(color);
        invalidate();
    }

    public void setProgressWidth(float width) {
        progressPaint.setStrokeWidth(width);
        backgroundPaint.setStrokeWidth(width);
        updateRect();
        invalidate();
    }

    public void setRounded(boolean rounded) {
        progressPaint.setStrokeCap(rounded ? Paint.Cap.ROUND : Paint.Cap.BUTT);
        invalidate();
    }
}
