package com.selcukcihan.android.sensors;

import android.content.Context;
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
import java.util.HashMap;


public class DetailActivity extends AppCompatActivity implements SensorEventListener {
    private SensorContainer mSensors;
    private SensorWrapper mSensor;
    private ShareActionProvider mShareActionProvider;
    private Intent mShareIntent;
    private String mShareString = "{0}{1}";
    private String mSensorValues = "";
    private float []mValues;
    private Toolbar mToolbar;


    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private SensorFragment mFragment;

    /*
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.details, popup.getMenu());
        popup.show();
    }*/

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
/*
    private void renderMetaData() {
        TextView toolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(mSensor.getLocalizedName());
        ((ImageView)findViewById(R.id.sensor_toolbar_icon)).setImageResource(mSensor.getImageId());

        ((TextView)findViewById(R.id.sensor_name_vendor)).setText(mSensor.getName() + " {" + mSensor.getVendor() + "}");
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mToolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        Integer sensorType = Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_SENSOR_TYPE));
        String sensorDescriptor = intent.getStringExtra(MainActivity.EXTRA_SENSOR_DESCRIPTOR);

        mSensors = new SensorContainer(this);
        mSensor = mSensors.findByType(sensorType);
        //renderMetaData();

        mShareString = getResources().getString(R.string.share_data);
        initializeShareIntent();

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(mSensor.getIndex());
    }

    protected void onResume() {
        super.onResume();
        mSensors.registerListener(mSensor);
    }

    protected void onPause() {
        super.onPause();
        mSensors.unregisterListener();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {  }

    public void onSensorChanged(SensorEvent event) {
        mValues = event.values;

        Float [] boxed = new Float[mValues.length];
        for (int i = 0; i < mValues.length; i++) {
            boxed[i] = Float.valueOf(mValues[i]);
        }
        mSensorValues = TextUtils.join(" | ", boxed);
        if (mFragment != null) {
            mFragment.refresh(boxed);
        }

        mShareIntent.removeExtra(Intent.EXTRA_TEXT); // Remove previously set values from the intent
        mShareIntent.putExtra(Intent.EXTRA_TEXT, String.format(mShareString, mSensor.getLocalizedName(), mSensorValues));
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {
        private HashMap<Integer, SensorFragment> mFragments;
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            mFragments = new HashMap<Integer, SensorFragment>();
        }

        private void renderMetaData() {
            if (mToolbar != null) {
                ((ImageView) findViewById(R.id.sensor_toolbar_icon)).setImageResource(mSensor.getImageId());
                TextView toolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
                toolbarTitle.setText(mSensor.getLocalizedName());
            }
        }

        @Override
        public Fragment getItem(int position) {
            SensorFragment fragment = SensorFragment.newInstance(mSensor.getType(), mSensor.getSensorDescriptor());
            mFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            return mSensors.count();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mSensor = mSensors.getSensor(position);
            mFragment = mFragments.get(position);
            renderMetaData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
