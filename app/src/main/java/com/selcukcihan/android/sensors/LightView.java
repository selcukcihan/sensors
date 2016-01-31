package com.selcukcihan.android.sensors;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * Created by Selcuk on 30.1.2016.
 */
public class LightView extends View {
    private Paint paint;
    private Paint paintTextWhite;
    private Paint paintTextBlack;
    private Paint paintText;
    private int mValue = 0;
    private int mWidth;
    private int mHeight;
    private float mRange;
    public float mMax = 100;

    public LightView(Context context, float range) {
        super(context);

        mRange = range;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //paint.setColor(Color.HSVToColor(new float[]{0, 0, 0}));

        paintTextWhite = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTextWhite.setColor(ContextCompat.getColor(context, R.color.icons));
        paintTextWhite.setTextAlign(Paint.Align.CENTER);

        paintTextBlack = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTextBlack.setColor(ContextCompat.getColor(context, R.color.primary_text));
        paintTextBlack.setTextAlign(Paint.Align.CENTER);

        paintText = paintTextWhite;
    }
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mWidth / 2, mHeight / 2, Math.min(mWidth, mHeight) / 3, paint);

        /*
        int xPos = (mWidth / 2);
        int yPos = (int) ((mHeight / 2) - ((paintText.descent() + paintText.ascent()) / 2)) ;
        //((textPaint.descent() + textPaint.ascent()) / 2) is the distance from the baseline to the center.
        canvas.drawText(Integer.toString(mValue), xPos, yPos, paintText);
        */
    }
    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldheight) {
        super.onSizeChanged(width, height, oldWidth, oldheight);

        mWidth = width;
        mHeight = height;
        paintTextWhite.setTextSize(Math.min(mWidth, mHeight) / 6);
        paintTextBlack.setTextSize(Math.min(mWidth, mHeight) / 6);

        paint.setShader(new RadialGradient(
                mWidth / 2, mHeight / 2,
                Math.min(mWidth, mHeight) / 2,
                new int[] { R.color.icons, R.color.dark },
                new float[] { 0, 0.3f },
                Shader.TileMode.CLAMP
        ));
    }

    public void refresh(int value) {
        mValue = value;
        if (mMax < value) {
            mMax = value;
        }
        if (value > (mMax / 2)) {
            paintText = paintTextBlack;
        } else {
            paintText = paintTextWhite;
        }

        float v = value / mMax;
        //paint.setColor(Color.HSVToColor(new float[]{0, 0, v}));

        this.invalidate();
    }
}
