package com.selcukcihan.android.sensors;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;


public class DetailActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    private ShareActionProvider mShareActionProvider;
    private Intent mShareIntent;
    private String mShareString = "{0}{1}";
    private String mSensorName = "";
    private String mSensorValues = "";
    float []values;

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.details, popup.getMenu());
        popup.show();
    }

    private void initializeShareIntent() {
        mShareIntent = new Intent();
        mShareIntent.setAction(Intent.ACTION_SEND);
        mShareIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        mShareIntent.setType("text/plain");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(mShareIntent);
        }

        return true;
    }

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
        mSensorName = intent.getStringExtra(MainActivity.EXTRA_SENSOR_NAME);
        String sensorImage = intent.getStringExtra(MainActivity.EXTRA_SENSOR_IMAGE);

        //getSupportActionBar().setTitle(sensorName);

        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(mSensorName);
        ((ImageView)findViewById(R.id.sensor_toolbar_icon)).setImageResource(Integer.parseInt(sensorImage));

        //toolbar.inflateMenu(R.menu.details);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Integer.parseInt(sensorType));
        //((ImageView)findViewById(R.id.sensor_big)).setImageResource(Integer.parseInt(sensorImage));
        ((TextView)findViewById(R.id.sensor_name_vendor)).setText(sensor.getName() +  " {" + sensor.getVendor() + "}");
        //((TextView)findViewById(R.id.name)).setText(sensor.getName());
        //((TextView)findViewById(R.id.vendor)).setText(sensor.getVendor());

        mShareString = getResources().getString(R.string.share_data);
        initializeShareIntent();
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
        values = event.values;
        mSensorValues = Float.toString(values[0]);
        mShareIntent.removeExtra(Intent.EXTRA_TEXT); // Remove previously set values from the intent
        ((TextView)findViewById(R.id.reading1_value)).setText(Float.toString(values[0]));
        if (values.length > 1) {
            mSensorValues +=  " | " + Float.toString(values[1]);
            ((TextView)findViewById(R.id.reading2_value)).setText(Float.toString(values[1]));
            if (values.length > 2) {
                mSensorValues +=  " | " + Float.toString(values[2]);
                ((TextView)findViewById(R.id.reading3_value)).setText(Float.toString(values[2]));
            }
        }
        mShareIntent.putExtra(Intent.EXTRA_TEXT, String.format(mShareString, mSensorName, mSensorValues));
    }
}
