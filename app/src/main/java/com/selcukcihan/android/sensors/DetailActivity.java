package com.selcukcihan.android.sensors;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class DetailActivity extends AppCompatActivity implements SensorEventListener {
    private SensorContainer mSensors;
    private SensorWrapper mSensor;
    private String mShareString = "{0}{1}";
    private String mSensorValues = "";
    private float []mValues;

    private SensorFragment mFragment;

    private Toolbar mToolbar;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private TextView mReadingsTextView;

    private ShareActionProvider mShareActionProvider;
    private Intent mShareIntent;
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

    private void renderMetaData() {
        TextView toolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(mSensor.getLocalizedName());
        ((ImageView) findViewById(R.id.sensor_toolbar_icon)).setImageResource(mSensor.getImageId());
        ((TextView) findViewById(R.id.sensor_name_vendor)).setText(mSensor.getName() + " {" + mSensor.getVendor() + "}");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mToolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mReadingsTextView = (TextView) findViewById(R.id.readings);

        Intent intent = getIntent();
        Integer sensorType = Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_SENSOR_TYPE));
        String sensorDescriptor = intent.getStringExtra(MainActivity.EXTRA_SENSOR_DESCRIPTOR);

        mSensors = new SensorContainer(this);
        mSensor = mSensors.findByType(sensorType);
        renderMetaData();

        mShareString = getResources().getString(R.string.share_data);
        initializeShareIntent();

        mFragment = SensorFragment.newInstance(sensorType, sensorDescriptor);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.detail_fragment, mFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_sensor:
                SensorDialogFragment dialog = SensorDialogFragment.newInstance(mSensor.getType());
                dialog.show(getSupportFragmentManager(), SensorDialogFragment.class.getSimpleName());
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
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
        String formattedString = String.format(mSensor.getRawFormat(), boxed);
        mReadingsTextView.setText(formattedString);

        mShareIntent.removeExtra(Intent.EXTRA_TEXT); // Remove previously set values from the intent
        mShareIntent.putExtra(Intent.EXTRA_TEXT, String.format(mShareString, mSensor.getLocalizedName(), mSensorValues));
    }
}
