package com.android.app.unittesting.staticf;

import static android.content.Context.SENSOR_SERVICE;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Utils {
    private static String getSystemProperty(String name) throws Exception {

        Class systemPropertyClazz = Class.forName("android.os.SystemProperties");

        return (String) systemPropertyClazz.getMethod("get", new Class[]{String.class})

                .invoke(systemPropertyClazz, new Object[]{name});

    }

    public static boolean checkEmulator() {

        try {

            boolean goldfish = getSystemProperty("ro.hardware").contains("goldfish");

            boolean emu = getSystemProperty("ro.kernel.qemu").length() > 0;

            boolean sdk = getSystemProperty("ro.product.model").equals("sdk");

            if (emu || goldfish || sdk) {

                return true;

            }

        } catch (Exception e) {

        }

        return false;

    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        String src = Build.MODEL;
        if (TextUtils.isEmpty(src)) {
            src = "";
        }
        return src;
    }

    /**
     * 获取获取手机厂商
     *
     * @return
     */
    public static String getPhoneMANUFACTURER() {
        String src = Build.MANUFACTURER;
        if (TextUtils.isEmpty(src)) {
            src = "";
        }
        return src;
    }

    public static boolean isEmulator2(Activity mContext) {
        String url = "tel:" + "123456";
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_DIAL);
        // 是否可以处理跳转到拨号的 Intent
        boolean canResolveIntent = intent.resolveActivity(mContext.getPackageManager()) != null;
        Log.v("generic",Build.FINGERPRINT);
        Log.v("Build.FINGERPRINT",Build.FINGERPRINT);
        Log.v("Build.MODEL",Build.MODEL);
        Log.v("Build.SERIAL",Build.SERIAL);
        Log.v("Build.MANUFACTURER",Build.MANUFACTURER);
        Log.v("Build.BRAND",Build.BRAND);
        Log.v("Build.DEVICE",Build.DEVICE);
        Log.v("Build.PRODUCT",Build.PRODUCT);



        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.SERIAL.equalsIgnoreCase("unknown")
                || Build.SERIAL.equalsIgnoreCase("android")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT)
                || ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE))
                .getNetworkOperatorName().toLowerCase().equals("android")
                || !canResolveIntent;
    }

    public static void isEmulator3(Activity mContext) {
        checkMac();
        Log.v("检测usb充电"," "+checkPluggedudb(mContext));
        Log.v("光传感器"," "+notHasLightSensorManager(mContext));
      //  Log.v("蓝牙是否有效来判断 需要权限"," "+notHasBlueTooth());
        Log.v("CPU"," "+checkIsNotRealPhone());
        chuliq();
        qudaoxinxi();
        jidai();
        try {
            Log.v("ro.hardware"," "+getSystemProperty("ro.hardware"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isSupportCameraFlash = mContext.getPackageManager().hasSystemFeature("android.hardware.camera.flash");//是否支持闪光灯
        Log.v("是否支持闪光灯"," "+isSupportCameraFlash);
        //checkEmulator();
        //Log.v("模拟器的特征"," "+checkEmulator());
    }

    /**
     * 检测 mac 地址
     * @return
     */
    public static void checkMac(){
        try {
            Enumeration networkInterfaces;
            String str = null;
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
                if (networkInterface != null) {
                    byte[] hardwareAddress;
                    byte[] bArr = new byte[0];
                    hardwareAddress = networkInterface.getHardwareAddress();
                    if (!(hardwareAddress == null || hardwareAddress.length == 0)) {
                        String str2;
                        StringBuilder stringBuilder = new StringBuilder();
                        Log.v("checkMac","hardwareAddress null");
                    }else{
                        Log.v("checkMac","hardwareAddress >0");
                    }
                }
            }
        }catch (Throwable e){
            e.printStackTrace();
        }

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
    public static void chuliq(){
        String productBoard = CommandUtil.getSingleInstance().getProperty("ro.product.board");
        if(!TextUtils.isEmpty(productBoard)){
            Log.v("处理器 productBoard 信息",productBoard);
        }else{
            Log.v("处理器 productBoard 信息","null");
        }
       // if (productBoard == null | "".equals(productBoard)) ++suspectCount;

        String boardPlatform = CommandUtil.getSingleInstance().getProperty("ro.board.platform");
        if(!TextUtils.isEmpty(boardPlatform)){
            Log.v("处理器 boardPlatform 信息",boardPlatform);
        }else{
            Log.v("处理器 boardPlatform 信息","null");
        }
      //  if (boardPlatform == null | "".equals(boardPlatform)) ++suspectCount;

      /*  if (productBoard != null && boardPlatform != null && !productBoard.equals(boardPlatform))
            ++suspectCount;*/
    }
    public static void qudaoxinxi(){
        String buildFlavor = CommandUtil.getSingleInstance().getProperty("ro.build.flavor");
        if(!TextUtils.isEmpty(buildFlavor)){
            Log.v("渠道信息",buildFlavor);
        }else{
            Log.v("渠道信息 信息","null");
        }

    }
    public static void jidai(){
        String buildFlavor = CommandUtil.getSingleInstance().getProperty("gsm.version.baseband");
        if(!TextUtils.isEmpty(buildFlavor)&&buildFlavor.contains("1.0.0.0")){
            Log.v("基带信息",buildFlavor);
        }else{
            Log.v("基带信息 信息","null");
        }

    }

   /* public boolean devicesjidai(){
        try {
            String productBoard = CommandUtil.getSingleInstance().getProperty("ro.product.board");
            if (productBoard == null | "".equals(productBoard)) ++suspectCount;

            String boardPlatform = CommandUtil.getSingleInstance().getProperty("ro.board.platform");
            if (boardPlatform == null | "".equals(boardPlatform)) ++suspectCount;

            if (productBoard != null && boardPlatform != null && !productBoard.equals(boardPlatform))
                ++suspectCount;
        } catch (Exception e) {
            value = null;
        }
    }*/

    /**
     * 判断是否存在光传感器来判断是否为模拟器
     * 部分真机也不存在温度和压力传感器。其余传感器模拟器也存在。
     * @return true 为模拟器
     */
    public static Boolean notHasLightSensorManager(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        Sensor sensor8 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //光
        if (null == sensor8) {
            return true;
        } else {
            return false;
        }
    }

    /*
     *判断蓝牙是否有效来判断是否为模拟器
     *返回:true 为模拟器
     */
    public static boolean notHasBlueTooth() {//需要权限
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        if (ba == null) {
            return true;
        } else {
            // 如果有蓝牙不一定是有效的。获取蓝牙名称，若为null 则默认为模拟器
            String name = ba.getName();
            if (TextUtils.isEmpty(name)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /*
     *根据部分特征参数设备信息来判断是否为模拟器
     *返回:true 为模拟器
     */
    public static boolean isFeatures() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }


    /*
     *根据CPU是否为电脑来判断是否为模拟器
     *返回:true 为模拟器
     */
    public static boolean checkIsNotRealPhone() {
        String cpuInfo = readCpuInfo();
        if ((cpuInfo.contains("intel") || cpuInfo.contains("amd"))) {
            return true;
        }
        return false;
    }


    /*
     *根据CPU是否为电脑来判断是否为模拟器(子方法)
     *返回:String
     */
    public static String readCpuInfo() {
        String result = "";
        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            StringBuffer sb = new StringBuffer();
            String readLine = "";
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine);
            }
            responseReader.close();
            result = sb.toString().toLowerCase();
        } catch (IOException ex) {
        }
        return result;
    }

    public static void execShell2(String cmd){
        try {
            Runtime rt = Runtime.getRuntime();
            //Process proc = rt.exec("cat /proc/cpuinfo");
            Process proc = rt.exec("getprop ro.product.cpu.abi");
            //Process proc = rt.exec("dumpsys activity activities");
            InputStream stderr = proc.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            System.out.println("<error></error>");
            while ((line = br.readLine()) != null)
                System.out.println(line);
            System.out.println("");
            int exitVal = proc.waitFor();
            System.out.println("Process exitValue: " + exitVal);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    public static boolean isEmulator() {
        String cmd = "getprop ro.product.cpu.abi";
        String cpu =  execShell(cmd);
        if(!TextUtils.isEmpty(cpu)&&cpu.contains("x86")){
            return true;
        }
        return false;
    }
    public static String execShell(String cmd){
        StringBuffer s = new StringBuffer();
        try {
            Runtime rt = Runtime.getRuntime();
            Process process = rt.exec(cmd);
            //Process proc = rt.exec("dumpsys activity activities");
            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                process.waitFor();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                s.append(line).append("\n");
            }
            inputStream.close();
            bufferedReader.close();
            System.out.println("Process toString: " + s.toString());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return s.toString();
    }

    /**
     * 判断ip 是否是开了代理
     * @param context
     * @return
     */
    public static boolean isWifiProxy(Context context) {
        try {
            final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;

            String proxyAddress;

            int proxyPort;

            if (IS_ICS_OR_LATER) {

                proxyAddress = System.getProperty("http.proxyHost");

                String portStr = System.getProperty("http.proxyPort");

                proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));

            } else {

                proxyAddress = android.net.Proxy.getHost(context);

                proxyPort = android.net.Proxy.getPort(context);

            }

            return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
        }catch (Throwable e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean checkVPN(Context context){
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_VPN);
            if(networkInfo == null){
                return false;
            }else {
                return networkInfo.isConnected();
            }
        }catch (Throwable e){
            e.printStackTrace();
        }

        return false;
    }

}