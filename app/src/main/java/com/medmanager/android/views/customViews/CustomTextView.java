package com.medmanager.android.views.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.support.v7.widget.AppCompatTextView;

import com.medmanager.android.R;

/**
 * Created by ILENWABOR DAVID on 25/03/2018.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    private int circleColor, lineColor, circleTextColor;
    private String circleText;
    private Paint paint;

    public CustomTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        paint = new Paint();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CustomTextView, 0, 0);
        try {
            circleColor = typedArray.getInteger(R.styleable.CustomTextView_circleColor, 0);
            lineColor = typedArray.getInteger(R.styleable.CustomTextView_lineColor, 0);
            circleText = typedArray.getString(R.styleable.CustomTextView_circleText);
            circleTextColor = typedArray.getInteger(R.styleable.CustomTextView_circleTextColor, 0);
        }
        finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int viewWidthHalf = this.getMeasuredWidth()/2;
        int viewHeightHalf = this.getMeasuredHeight()/2;
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(lineColor);

        canvas.drawLine(getMeasuredWidth(), 0, getMeasuredWidth(), 0, paint);

        paint.setColor(circleColor);
        canvas.drawCircle(viewWidthHalf, viewHeightHalf, 10, paint);

        paint.setColor(circleTextColor);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(25);
        canvas.drawText(circleText,viewWidthHalf, viewHeightHalf, paint);
    }

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        invalidate();
        requestLayout();
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        invalidate();
        requestLayout();
    }

    public int getCircleTextColor() {
        return circleTextColor;
    }

    public void setCircleTextColor(int circleTextColor) {
        this.circleTextColor = circleTextColor;
        invalidate();
        requestLayout();
    }

    public String getCircleText() {
        return circleText;
    }

    public void setCircleText(String circleText) {
        this.circleText = circleText;
        invalidate();
        requestLayout();
    }
}
