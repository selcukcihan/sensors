package com.selcukcihan.android.sensors;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by SELCUKCI on 10.2.2016.
 */
public class AboutDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Cihazınızdaki sensörleri listeler ve bu sensörlerden okunan değerleri gösterir.");
        builder.setTitle("Sensörler Uygulaması");
        builder.setIcon(R.drawable.ic_launcher_sensors);
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
