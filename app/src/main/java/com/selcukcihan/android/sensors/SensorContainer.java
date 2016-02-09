package com.selcukcihan.android.sensors;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by SELCUKCI on 9.2.2016.
 */
public class SensorContainer {
    private final Context mContext;
    private final SensorManager mSensorManager;
    private SensorWrapper[] mSensors;
    public SensorContainer(Context context) {
        mContext = context;
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        populateSensors();
    }

    public void registerListener(SensorWrapper sensor) {
        this.unregisterListener();
        mSensorManager.registerListener((SensorEventListener)mContext, sensor.getSensor(), SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterListener() {
        mSensorManager.unregisterListener((SensorEventListener)mContext);
    }

    private void populateSensors() {
        HashMap<Integer, String> sensorMap = new HashMap<Integer, String>();
        String[] sensorsAndImages = mContext.getResources().getStringArray(R.array.supported_sensors);
        for(String simg : sensorsAndImages) {
            String[] pair = simg.split(":");
            int sensorId = Integer.parseInt(pair[0]);
            sensorMap.put(sensorId, pair[1]);
        }
        List<Sensor> allSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        List<SensorWrapper> filteredSensors = new ArrayList<SensorWrapper>();

        Integer i = 0;
        for(Sensor s : allSensors) {
            if (sensorMap.containsKey(s.getType())) {
                filteredSensors.add(new SensorWrapper(mContext, s, sensorMap.get(s.getType()), i));
                i++;
            }
        }
        mSensors = filteredSensors.toArray(new SensorWrapper[filteredSensors.size()]);
    }

    public SensorWrapper getSensor(int position) {
        return mSensors[position];
    }

    public int count() {
        return mSensors.length;
    }

    public SensorWrapper findByType(Integer sensorType) {
        for (SensorWrapper s : mSensors) {
            if (s.getType() == sensorType) {
                return s;
            }
        }
        return null;
    }
}
