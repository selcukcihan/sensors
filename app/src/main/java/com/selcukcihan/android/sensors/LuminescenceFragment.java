package com.selcukcihan.android.sensors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class LuminescenceFragment extends SensorFragment {

    private TextView mTextView;
    private BarChart mChart;

    public LuminescenceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onAfterCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_luminescence, container, false);
        //mChart = (BarChart) view.findViewById(R.id.luminescence_chart);
        return view;
    }

    @Override
    public void onRefresh(Object [] values) {

    }
}
