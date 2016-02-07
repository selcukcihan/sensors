package com.selcukcihan.android.sensors;

import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class LuminescenceFragment extends SensorFragment {

    private ImageView mImageView;

    private GradientDrawable mGradientYellow;
    private GradientDrawable mGradientWhite;

    private double mMaxRange = 100;
    private int[] mYellow;
    private int[] mWhite;

    public LuminescenceFragment() {
    }

    @Override
    public View onAfterCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_luminescence, container, false);
        mImageView = (ImageView) view.findViewById(R.id.luminescence_circle);
        mGradientYellow = (GradientDrawable) mImageView.getDrawable();
        mMaxRange = Math.log(mSensor.getSensor().getMaximumRange());
        mRefreshText = false;

        mGradientWhite = (GradientDrawable)ResourcesCompat.getDrawable(getResources(), R.drawable.circle_white, null);

        return view;
    }

    @Override
    public void onRefresh(Object [] values) {
        double value = Math.log(((Float) values[0]).doubleValue());
        double ratio = value / mMaxRange;
        if (ratio < 0) { // 0 lux ==> value == -infinity ==> ratio -ve ==> ratio cannot be -ve
            ratio = 0;
        } else if (ratio == 0) {
            ratio = (Math.log(2) / mMaxRange) / 2;
        }

        if (ratio < 0.5) {
            float newRadius = (float)(mImageView.getWidth() * ratio);
            mGradientYellow.setGradientRadius(newRadius);
            mImageView.setImageDrawable(mGradientYellow);
        } else {
            ratio = ratio - 0.5f;
            if (ratio == 0) {
                ratio = 0.001f;
            }
            float newRadius = (float)(mImageView.getWidth() * ratio);
            mGradientWhite.setGradientRadius(newRadius);
            mImageView.setImageDrawable(mGradientWhite);
        }
    }
}
