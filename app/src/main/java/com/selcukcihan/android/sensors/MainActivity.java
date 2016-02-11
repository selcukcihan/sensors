package com.selcukcihan.android.sensors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_SENSOR_TYPE = "com.selcukcihan.android.sensors.SENSOR_TYPE";
    public final static String EXTRA_SENSOR_DESCRIPTOR = "com.selcukcihan.android.sensors.SENSOR_DESCRIPTOR";

    private SensorContainer mSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSensors = new SensorContainer(this);
        initializeGridView();
    }

    private void onItemClickHelper(AdapterView<?> parent, View view, int position, long id) {
        SensorWrapper sensor = mSensors.getSensor(position);
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(EXTRA_SENSOR_TYPE, Integer.toString(sensor.getType()));
        intent.putExtra(EXTRA_SENSOR_DESCRIPTOR, sensor.getSensorDescriptor());
        MainActivity.this.startActivity(intent);
    }

    private void initializeGridView() {
        GridView gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new TileAdapter(this, mSensors));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.onItemClickHelper(parent, view, position, id);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                AboutDialogFragment dialog = new AboutDialogFragment();
                dialog.show(getSupportFragmentManager(), "AboutDialogFragment");
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
