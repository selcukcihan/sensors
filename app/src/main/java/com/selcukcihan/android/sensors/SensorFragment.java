package com.selcukcihan.android.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Selcuk on 30.1.2016.
 */
public abstract class SensorFragment extends Fragment {
    protected static final String ARG_PARAM1 = "param1";
    protected static final String ARG_PARAM2 = "param2";

    protected SensorWrapper mSensor;

    private Integer mSensorType;
    private String mSensorDescriptor;
    private TextView mTextView;

    abstract View onAfterCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    abstract void onRefresh(Object [] values);

    public void refresh(Object [] values) {
        if (mTextView != null) {
            mTextView.setText(String.format(mSensor.getDetailText(), values));
        }
        onRefresh(values);
    }

    public String getRawReadings(Float [] values) {
        return String.format(mSensor.getRawFormat(), values);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSensorType = getArguments().getInt(ARG_PARAM1);
            mSensorDescriptor = getArguments().getString(ARG_PARAM2);
            mSensor = new SensorWrapper(this.getContext(), mSensorType, mSensorDescriptor);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() { super.onDetach(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = onAfterCreateView(inflater, container, savedInstanceState);
        mTextView = (TextView) view.findViewById(R.id.readings_detail);
        return view;
    }

    public static SensorFragment newInstance(Integer sensorType, String sensorDescriptor) {
        SensorFragment fragment = null;
        switch (sensorType) {
            case Sensor.TYPE_PRESSURE:
                fragment = new PressureFragment();
                break;
            case Sensor.TYPE_STEP_COUNTER:
                fragment = new PedometerFragment();
                break;
            case Sensor.TYPE_LIGHT:
                fragment = new LuminescenceFragment();
                break;
            default:
                break;
        }
        if (fragment != null) {
            Bundle args = new Bundle();
            args.putInt(ARG_PARAM1, sensorType);
            args.putString(ARG_PARAM2, sensorDescriptor);
            fragment.setArguments(args);
        }
        return fragment;
    }
}
