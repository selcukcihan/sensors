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
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;


public class DetailActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    float []values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        String sensorType = intent.getStringExtra(MainActivity.EXTRA_SENSOR_TYPE);
        String sensorName = intent.getStringExtra(MainActivity.EXTRA_SENSOR_NAME);
        String sensorImage = intent.getStringExtra(MainActivity.EXTRA_SENSOR_IMAGE);

        //getSupportActionBar().setTitle(sensorName);

        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(sensorName);
        ((ImageView)findViewById(R.id.sensor_toolbar_icon)).setImageResource(Integer.parseInt(sensorImage));

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Integer.parseInt(sensorType));
        //((ImageView)findViewById(R.id.sensor_big)).setImageResource(Integer.parseInt(sensorImage));
        ((TextView)findViewById(R.id.sensor_name_vendor)).setText(sensor.getName() +  " {" + sensor.getVendor() + "}");
        //((TextView)findViewById(R.id.name)).setText(sensor.getName());
        //((TextView)findViewById(R.id.vendor)).setText(sensor.getVendor());
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {  }

    public void onSensorChanged(SensorEvent event) {
        /*
        Float [] boxed = new Float[event.values.length];
        for (int i = 0; i < event.values.length; i++) {
            boxed[i] = new Float(event.values[i]);
        }
        String values = TextUtils.join("-", boxed);
        String formatted = getResources().getString(R.string.readings) + ": <" + values + ">";
        ((TextView)findViewById(R.id.sensor_raw_values)).setText(formatted);*/
        values = event.values;
        ((TextView)findViewById(R.id.reading1_value)).setText(Float.toString(values[0]));
        if (values.length > 1) {
            ((TextView)findViewById(R.id.reading2_value)).setText(Float.toString(values[1]));
            if (values.length > 2) {
                ((TextView)findViewById(R.id.reading3_value)).setText(Float.toString(values[2]));
            }
        }
    }
}
