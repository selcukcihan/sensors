package com.selcukcihan.android.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;


public class TileAdapter extends BaseAdapter {
    private Context mContext;
    private final SensorWrapper[] mValues;

    public TileAdapter(Context context, SensorWrapper[] values) {
        mContext = context;
        mValues = values;
    }

    public int getCount() {
        return mValues.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.grid_cell_sensor, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.sensor_label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.sensor_icon);

        textView.setText(mValues[position].getLocalizedName());
        imageView.setImageResource(mValues[position].getImageId());

        return rowView;
    }


}
