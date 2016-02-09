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
    protected boolean mRefreshText = true;

    private Integer mSensorType;
    private String mSensorDescriptor;

    private TextView mTextView;
    private View mView;
    private TextView mReadingsTextView;

    abstract View onBeforeCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    abstract void onRefresh(Object [] values);

    private void internalRefresh(Object [] values) {
        if (mRefreshText) {
            if (mTextView != null) {
                if (values.length > 0) {
                    mTextView.setText(String.format(mSensor.getDetailText(), values));
                } else {
                    mTextView.setText(mSensor.getDetailText());
                }
            }
            if (mReadingsTextView != null && values.length > 0) {
                String valuesString = TextUtils.join(" | ", values);
                mReadingsTextView.setText(valuesString);
            }
        }
    }

    public void refresh(Object [] values) {
        onRefresh(values);
        internalRefresh(values);
    }

    public String getRawReadings(Float [] values) {
        return String.format(mSensor.getRawFormat(), (Object)values);
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
        mView = onBeforeCreateView(inflater, container, savedInstanceState);
        mReadingsTextView = (TextView)mView.findViewById(R.id.readings);
        mTextView = (TextView) mView.findViewById(R.id.readings_detail);

        ((TextView) mView.findViewById(R.id.sensor_name_vendor)).setText(mSensor.getName() + " {" + mSensor.getVendor() + "}");

        internalRefresh(new Object[]{});

        return mView;
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
                fragment = new PlaceholderFragment();
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
