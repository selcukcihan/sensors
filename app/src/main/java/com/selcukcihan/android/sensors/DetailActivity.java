package com.selcukcihan.android.sensors;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class DetailActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private SensorWrapper mSensor;
    private ShareActionProvider mShareActionProvider;
    private Intent mShareIntent;
    private String mShareString = "{0}{1}";
    private String mSensorValues = "";
    private TextView mReadingsTextView;
    private float []mValues;


    private SensorWrapper[] mSensors;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

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

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        Intent intent = getIntent();
        Integer sensorType = Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_SENSOR_TYPE));
        String sensorDescriptor = intent.getStringExtra(MainActivity.EXTRA_SENSOR_DESCRIPTOR);
        ArrayList<Integer> sensorsList = intent.getIntegerArrayListExtra(MainActivity.EXTRA_SENSOR_LIST);
        mSensors = new SensorWrapper[sensorsList.size()];
        int i = 0;
        for(Integer id : sensorsList) {
            Sensor sensor = mSensorManager.getDefaultSensor(sensorType);
            mSensors[i] = new SensorWrapper(this, sensor, id.toString());
            if (id == Integer.parseInt(sensorDescriptor)) {
                mSensor = mSensors[i];
            }
            i++;
        }

        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(mSensor.getLocalizedName());
        ((ImageView)findViewById(R.id.sensor_toolbar_icon)).setImageResource(mSensor.getImageId());

        ((TextView)findViewById(R.id.sensor_name_vendor)).setText(sensor.getName() + " {" + sensor.getVendor() + "}");

        mReadingsTextView = (TextView)findViewById(R.id.readings);

        mShareString = getResources().getString(R.string.share_data);
        initializeShareIntent();


        Fragment fragment = SensorFragment.newInstance(sensorType, sensorDescriptor);
        if (fragment != null) {
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.detail_fragment, fragment);
            fragmentTransaction.commit();
        }

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor.getSensor(), SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {  }

    public void onSensorChanged(SensorEvent event) {
        mValues = event.values;

        Float [] boxed = new Float[mValues.length];
        for (int i = 0; i < mValues.length; i++) {
            boxed[i] = new Float(mValues[i]);
        }
        mSensorValues = TextUtils.join(" | ", boxed);

        SensorFragment fragment = (SensorFragment)getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
        String rawReadings = mSensorValues;
        if (fragment != null) {
            fragment.refresh(boxed);
            rawReadings = fragment.getRawReadings(boxed);
        }

        mReadingsTextView.setText(rawReadings);
        mShareIntent.removeExtra(Intent.EXTRA_TEXT); // Remove previously set values from the intent
        mShareIntent.putExtra(Intent.EXTRA_TEXT, String.format(mShareString, mSensor.getLocalizedName(), mSensorValues));
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ScreenSlidePageFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
