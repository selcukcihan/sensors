package com.selcukcihan.android.sensors;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class PedometerFragment extends SensorFragment {
    private PedometerView mPedometerView;

    @Override
    public View onBeforeCreateView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pedometer, container, false);

        mPedometerView = new PedometerView(getActivity());
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.pedometer_container);
        relativeLayout.addView(mPedometerView);
        return view;
    }

    @Override
    public void onRefresh(Object [] values) {
        int value = ((Float)values[0]).intValue();
        mPedometerView.refresh(value);
    }
}
