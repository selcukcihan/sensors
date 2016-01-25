package com.selcukcihan.android.sensors;

import android.content.Context;
import android.hardware.Sensor;

public class SensorWrapper {
    private final Context mContext;
    private final Sensor mSensor;
    private final String mSensorDescriptor;

    private Integer mImageId = null;
    private String mLocalizedName = null;

    public SensorWrapper(Context context, Sensor sensor, String sensorDescriptor) {
        mContext = context;
        mSensor = sensor;
        mSensorDescriptor = sensorDescriptor;
    }

    public int getType() {
        return mSensor.getType();
    }

    public String getName() {
        return mSensor.getName();
    }

    public int getImageId() {
        if (mImageId == null) {
            mImageId = mContext.getResources().getIdentifier(mSensorDescriptor, "mipmap", mContext.getPackageName());
        }
        return mImageId;
    }

    public String getLocalizedName() {
        if (mLocalizedName == null) {
            Integer stringId = mContext.getResources().getIdentifier(mSensorDescriptor + "_string", "string", mContext.getPackageName());
            mLocalizedName = capitalizeWords(mContext.getResources().getString(stringId));
        }
        return mLocalizedName;
    }

    private String capitalizeWords(String input) {
        final StringBuilder result = new StringBuilder(input.length());
        String[] words = input.split("\\s");
        for (int i = 0, l = words.length; i < l; ++i) {
            if (i > 0) result.append(" ");
            result.append(Character.toUpperCase(words[i].charAt(0)))
                    .append(words[i].substring(1));
        }
        return result.toString();
    }
}
