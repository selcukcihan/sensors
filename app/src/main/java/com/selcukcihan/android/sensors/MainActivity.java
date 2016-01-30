package com.selcukcihan.android.sensors;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_SENSOR_TYPE = "com.selcukcihan.android.sensors.SENSOR_TYPE";
    public final static String EXTRA_SENSOR_DESCRIPTOR = "com.selcukcihan.android.sensors.SENSOR_DESCRIPTOR";

    SensorManager smm;
    SensorWrapper[] sensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeSensors();
        initializeGridView();
    }
    private void onItemClickHelper(AdapterView<?> parent, View view, int position, long id) {
        SensorWrapper sensor = MainActivity.this.sensors[position];
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(EXTRA_SENSOR_TYPE, Integer.toString(sensor.getType()));
        intent.putExtra(EXTRA_SENSOR_DESCRIPTOR, sensor.getSensorDescriptor());
        MainActivity.this.startActivity(intent);
    }
    private void initializeListView() {
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(new SensorAdapter(this, sensors));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.onItemClickHelper(parent, view, position, id);
            }
        });
    }

    private void initializeGridView() {
        GridView gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new TileAdapter(this, sensors));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.onItemClickHelper(parent, view, position, id);
            }
        });
    }

    private void initializeSensors() {
        HashMap<Integer, String> sensorMap = new HashMap<Integer, String>();
        String[] sensorsAndImages = getResources().getStringArray(R.array.supported_sensors);
        for(String simg : sensorsAndImages) {
            String[] pair = simg.split(":");
            int sensorId = Integer.parseInt(pair[0]);
            sensorMap.put(sensorId, pair[1]);
        }
        smm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> allSensors = smm.getSensorList(Sensor.TYPE_ALL);
        List<SensorWrapper> filteredSensors = new ArrayList<SensorWrapper>();

        for(Sensor s : allSensors) {
            if (sensorMap.containsKey(s.getType())) {
                filteredSensors.add(new SensorWrapper(this, s, sensorMap.get(s.getType())));
            }
        }
        sensors = filteredSensors.toArray(new SensorWrapper[filteredSensors.size()]);
    }
}
