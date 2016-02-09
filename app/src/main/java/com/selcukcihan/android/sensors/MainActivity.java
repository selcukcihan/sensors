package com.selcukcihan.android.sensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_SENSOR_TYPE = "com.selcukcihan.android.sensors.SENSOR_TYPE";
    public final static String EXTRA_SENSOR_DESCRIPTOR = "com.selcukcihan.android.sensors.SENSOR_DESCRIPTOR";

    private SensorContainer mSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSensors = new SensorContainer(this);
        initializeGridView();
    }
    private void onItemClickHelper(AdapterView<?> parent, View view, int position, long id) {
        SensorWrapper sensor = mSensors.getSensor(position);
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(EXTRA_SENSOR_TYPE, Integer.toString(sensor.getType()));
        intent.putExtra(EXTRA_SENSOR_DESCRIPTOR, sensor.getSensorDescriptor());
        MainActivity.this.startActivity(intent);
    }
    /*
    private void initializeListView() {
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(new SensorAdapter(this, mSensors));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.onItemClickHelper(parent, view, position, id);
            }
        });
    }*/

    private void initializeGridView() {
        GridView gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new TileAdapter(this, mSensors));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.onItemClickHelper(parent, view, position, id);
            }
        });
    }
}
