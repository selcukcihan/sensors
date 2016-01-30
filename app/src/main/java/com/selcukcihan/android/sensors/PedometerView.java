package com.selcukcihan.android.sensors;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * Created by Selcuk on 30.1.2016.
 */
public class PedometerView extends View {
    private Paint paint;
    private Paint paintText;
    private int mValue = 0;
    private int mWidth;
    private int mHeight;

    public PedometerView(Context context) {
        super(context);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(ContextCompat.getColor(context, R.color.dark));

        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(ContextCompat.getColor(context, R.color.icons));
        paintText.setTextAlign(Paint.Align.CENTER);
    }
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mWidth / 2, mHeight / 2, Math.min(mWidth, mHeight) / 3, paint);

        int xPos = (mWidth / 2);
        int yPos = (int) ((mHeight / 2) - ((paintText.descent() + paintText.ascent()) / 2)) ;
        //((textPaint.descent() + textPaint.ascent()) / 2) is the distance from the baseline to the center.
        canvas.drawText(Integer.toString(mValue), xPos, yPos, paintText);
    }
    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldheight) {
        super.onSizeChanged(width, height, oldWidth, oldheight);

        mWidth = width;
        mHeight = height;
        paintText.setTextSize(Math.min(mWidth, mHeight) / 6);
    }

    public void refresh(int value) {
        mValue = value;
        this.invalidate();
    }
}
