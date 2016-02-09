package com.selcukcihan.android.sensors;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class PlaceholderFragment extends SensorFragment {

    public PlaceholderFragment() {
    }

    @Override
    public View onBeforeCreateView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_placeholder, container, false);
        return view;
    }

    @Override
    public void onRefresh(Object [] values) {
    }
}
