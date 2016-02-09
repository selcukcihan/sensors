package com.selcukcihan.android.sensors;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class PressureFragment extends SensorFragment {

    private TextView mTextView;
    private BarChart mChart;
    private int mLastValue = 0;

    public PressureFragment() {
        // Required empty public constructor
    }

    private void initializeChart() {
        mChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(2);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceBetweenLabels(0);
        xAxis.setDrawGridLines(false);

        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisLeft().setAxisMaxValue(1500f);
        mChart.getAxisLeft().setAxisMinValue(0f);
        mChart.getAxisRight().setEnabled(false);

        LimitLine line = new LimitLine(1013.25f);
        line.setLabel("1 atm - 1013.25hPa");
        line.setTextSize(mChart.getAxisLeft().getTextSize() / 3);
        line.setLineWidth(1f);
        mChart.getAxisLeft().addLimitLine(line);

        // setting data
        //mSeekBarX.setProgress(10);
        //mSeekBarY.setProgress(100);

        // add a nice and smooth animation
        mChart.animateY(2500);

        mChart.getLegend().setEnabled(false);
    }

    @Override
    public View onBeforeCreateView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pressure, container, false);
        mChart = (BarChart) view.findViewById(R.id.pressure_chart);
        initializeChart();
        return view;
    }

    @Override
    public void onRefresh(Object [] values) {
        int value = ((Float)values[0]).intValue();
        if (mLastValue != value) {
            mLastValue = value;

            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
            yVals1.add(new BarEntry(value, 0));

            ArrayList<String> xVals = new ArrayList<String>();
            xVals.add("hPa");

            BarDataSet set1 = new BarDataSet(yVals1, "");
            set1.setColors(ColorTemplate.PASTEL_COLORS);
            set1.setDrawValues(false);

            ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(xVals, dataSets);

            mChart.setData(data);
            mChart.invalidate();
        }
    }
}
