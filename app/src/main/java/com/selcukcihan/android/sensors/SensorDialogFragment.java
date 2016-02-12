package com.selcukcihan.android.sensors;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.TextView;

/**
 * Created by SELCUKCI on 10.2.2016.
 */
public class SensorDialogFragment extends DialogFragment {
    private SensorWrapper mSensor;

    static SensorDialogFragment newInstance(Integer sensorType) {
        SensorDialogFragment f = new SensorDialogFragment();

        Bundle args = new Bundle();
        args.putInt(MainActivity.EXTRA_SENSOR_TYPE, sensorType);
        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        mSensor = (new SensorContainer(getContext())).findByType(getArguments().getInt(MainActivity.EXTRA_SENSOR_TYPE));

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_sensor_dialog, null);
        builder.setView(view);
        //builder.setMessage("Cihazınızdaki sensörleri listeler ve bu sensörlerden okunan değerleri gösterir.");
        builder.setTitle(mSensor.getLocalizedName());
        builder.setIcon(mSensor.getImageId());

        ((TextView) view.findViewById(R.id.sensor_about_text)).setText(mSensor.getSensor().toString());

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
