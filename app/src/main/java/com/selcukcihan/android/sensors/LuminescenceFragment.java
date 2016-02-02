package com.selcukcihan.android.sensors;

import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class LuminescenceFragment extends SensorFragment {

    private ImageView mImageView;
    private GradientDrawable mGradient;
    private double mMaxRange = 100;

    public LuminescenceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onAfterCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_luminescence, container, false);
        mImageView = (ImageView) view.findViewById(R.id.luminescence_circle);
        mGradient = (GradientDrawable) mImageView.getDrawable();
        mMaxRange = Math.log(mSensor.getSensor().getMaximumRange());
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

        float newRadius = (float)(mImageView.getWidth() * ratio);
        mGradient.setGradientRadius(newRadius);
    }
}
