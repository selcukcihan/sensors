package com.selcukcihan.android.sensors;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by SELCUKCI on 10.2.2016.
 */
public class AboutDialogFragment extends DialogFragment {
    /*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Cihazınızdaki sensörleri listeler ve bu sensörlerden okunan değerleri gösterir.");
        // Create the AlertDialog object and return it
        return builder.create();
    }*/


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

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_about_dialog, null);
        builder.setView(view);
        String version = " - v";
        try {
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            version += pInfo.versionName;
        } catch (PackageManager.NameNotFoundException nnfe) {
            version += BuildConfig.VERSION_NAME;
        }

        builder.setTitle(getResources().getString(R.string.app_name) + version);
        builder.setIcon(R.drawable.ic_launcher_sensors);

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
