package com.android.app.unittesting.staticf;

import static android.content.Context.SENSOR_SERVICE;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.BatteryManager;

public class IEmulatorUtil {
    public static boolean isEmulator(Context context){
        //检测传感器数量
        int sensorNumber = getSensorNumber(context);
        if(sensorNumber<20){
            return true;
        }
        return false;
    }
    /**
     * 获取传感器数量
     */
    public static int getSensorNumber(Context context) {
        SensorManager sm = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        return sm.getSensorList(Sensor.TYPE_ALL).size();
    }
    public static boolean checkPluggedudb(Context context){
        try {
            IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, filter);
            if (batteryStatus == null) return false;
            int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            return chargePlug == BatteryManager.BATTERY_PLUGGED_USB;//检测usb充电
        }catch (Throwable e){
            e.printStackTrace();
        }
        return false;
    }
}
