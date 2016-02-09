package com.selcukcihan.android.sensors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class LightFragment extends SensorFragment {
    private LightView mLightView;

    @Override
    public View onBeforeCreateView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_light, container, false);

        mLightView = new LightView(getActivity(), mSensor.getSensor().getMaximumRange());
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.light_container);
        relativeLayout.addView(mLightView);
        return view;
    }

    @Override
    public void onRefresh(Object [] values) {
        int value = ((Float)values[0]).intValue();
        mLightView.refresh(value);
    }
}
