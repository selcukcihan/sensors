package com.selcukcihan.android.sensors;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_SENSOR_TYPE = "com.selcukcihan.android.sensors.SENSOR_TYPE";
    public final static String EXTRA_SENSOR_IMAGE = "com.selcukcihan.android.sensors.SENSOR_IMAGE";

    SensorManager smm;
    Sensor[] sensors;
    ListView lv;
    private final HashMap<Integer, Integer> sensorMap = new HashMap<Integer, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeSensors();
        lv.setAdapter(new SensorAdapter(this, sensors, sensorMap));
    }

    private void initializeSensors() {
        String[] sensorsAndImages = getResources().getStringArray(R.array.supported_sensors);
        for(String simg : sensorsAndImages) {
            String[] pair = simg.split(":");
            int sensorId = Integer.parseInt(pair[0]);
            String imageName = pair[1];
            int resourceId = getResources().getIdentifier(imageName, "mipmap", getPackageName());
            System.out.println(imageName + " - " + sensorId + " - " + resourceId);
            sensorMap.put(sensorId, resourceId);
        }

        smm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(EXTRA_SENSOR_TYPE, Integer.toString(MainActivity.this.sensors[position].getType())); //Optional parameters
                intent.putExtra(EXTRA_SENSOR_IMAGE, Integer.toString(MainActivity.this.sensorMap.get(MainActivity.this.sensors[position].getType()))); //Optional parameters
                MainActivity.this.startActivity(intent);
            }
        });
        List<Sensor> allSensors = smm.getSensorList(Sensor.TYPE_ALL);
        List<Sensor> filteredSensors = new ArrayList<Sensor>();

        for(Sensor s : allSensors) {
            if (sensorMap.containsKey(s.getType())) {
                filteredSensors.add(s);
            }
        }
        sensors = filteredSensors.toArray(new Sensor[filteredSensors.size()]);
    }
}

