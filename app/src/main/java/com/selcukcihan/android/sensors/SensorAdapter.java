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

public class SensorAdapter extends ArrayAdapter<Sensor> {
    private final Context context;
    private final Sensor[] values;
    private final HashMap<Integer, Integer> sensorMap;

    public SensorAdapter(Context _context, Sensor[] values, HashMap<Integer, Integer> sensorMap) {

        super(_context, 0, values);
        this.context = _context;
        this.values = values;
        this.sensorMap = sensorMap;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_sensor, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.sensor_label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.sensor_icon);
        textView.setText(values[position].getName());

        // Change icon based on name
        String s = values[position].toString();

        System.out.println(s);
        imageView.setImageResource(this.sensorMap.get(values[position].getType()));

        return rowView;
    }
}