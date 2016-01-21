package com.selcukcihan.android.sensors;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    float []values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String sensorType = intent.getStringExtra(MainActivity.EXTRA_SENSOR_TYPE);
        String sensorImage = intent.getStringExtra(MainActivity.EXTRA_SENSOR_IMAGE);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Integer.parseInt(sensorType));
        ((ImageView)findViewById(R.id.sensor_big)).setImageResource(Integer.parseInt(sensorImage));
        ((TextView)findViewById(R.id.header)).setText(sensor.getName());
        ((TextView)findViewById(R.id.vendor)).setText(sensor.getVendor());
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {  }

    public void onSensorChanged(SensorEvent event) {
        values = event.values;
        ((TextView)findViewById(R.id.reading1_value)).setText(Float.toString(values[0]));
        ((TextView)findViewById(R.id.reading2_value)).setText(Float.toString(values[1]));
        ((TextView)findViewById(R.id.reading3_value)).setText(Float.toString(values[2]));
    }
}
