package com.selcukcihan.android.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SensorAdapter extends ArrayAdapter<SensorWrapper> {
    private final Context context;
    private final SensorWrapper[] values;

    public SensorAdapter(Context _context, SensorWrapper[] values) {

        super(_context, 0, values);
        this.context = _context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_sensor, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.sensor_label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.sensor_icon);
        textView.setText(values[position].getName());

        imageView.setImageResource(values[position].getImageId());

        return rowView;
    }
}