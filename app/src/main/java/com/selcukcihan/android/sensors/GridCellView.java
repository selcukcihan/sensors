package com.selcukcihan.android.sensors;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;


public class GridCellView extends RelativeLayout
{

    public GridCellView(final Context context)
    {
        super(context);
    }

    public GridCellView(final Context context, final AttributeSet attrs)
    {
        super(context, attrs);
    }

    public GridCellView(final Context context, final AttributeSet attrs, final int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
