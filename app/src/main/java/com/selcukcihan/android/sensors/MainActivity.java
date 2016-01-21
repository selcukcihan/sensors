package com.selcukcihan.android.sensors;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SensorManager smm;
    List<Sensor> sensors;
    ListView lv;
    private final HashMap<Integer, Integer> sensorMap = new HashMap<Integer, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Sensor[] values = initializeSensors();

        lv.setAdapter(new SensorAdapter(this, values, sensorMap));
    }

    private Sensor[] initializeSensors() {
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
        sensors = smm.getSensorList(Sensor.TYPE_ALL);
        List<Sensor> filteredSensors = new ArrayList<Sensor>();

        for(Sensor s : sensors) {
            if (sensorMap.containsKey(s.getType())) {
                filteredSensors.add(s);
            }
        }
        Sensor[]values = filteredSensors.toArray(new Sensor[filteredSensors.size()]);
        return values;
    }
}

