package com.snail.antifake.jni;

import android.content.Context;
import android.util.Log;

public class EmulatorDetectUtil {
    static {
        Log.d("EmulatorDetectUtil","static emulator_check");
        System.loadLibrary("emulator_check");
    }
    public static native boolean detect();
    public static boolean isEmulator()  {
        Log.d("EmulatorDetectUtil","static isEmulator");
        return detect();
    }
}
