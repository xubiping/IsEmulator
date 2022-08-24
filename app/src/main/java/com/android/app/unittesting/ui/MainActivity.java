package com.android.app.unittesting.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.android.app.unittesting.R;
import com.snail.antifake.jni.EmulatorDetectUtil;
import com.android.app.unittesting.staticf.IEmulatorUtil;

public class MainActivity extends AppCompatActivity {

    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_title = (TextView) this.findViewById(R.id.tv_title);
        tv_content = (TextView) this.findViewById(R.id.tv_content);
        boolean ismoniq= IEmulatorUtil.isEmulator(this);

//                EmulatorCheckUtil.checkIsRunningInEmulator(this, new EmulatorCheckCallback() {
//            @Override
//            public void findEmulator(String emulatorInfo) {
//                tv_content.setText(emulatorInfo);
//             }
//        });
        boolean isDetect= true;
        try {
            isDetect= EmulatorDetectUtil.isEmulator();
            Log.v("EmulatorDetectUtil"," isDetect "+isDetect);
        }catch (Throwable e){
            Log.e("EmulatorDetectUtil"," isDetect "+e.toString());
            isDetect= false;
        }
        if(ismoniq){
            tv_title.setText("模拟器 "+IEmulatorUtil.getSensorNumber(this)+" isDetect="+isDetect);
        }else{
            tv_title.setText("真机 "+IEmulatorUtil.getSensorNumber(this)+" isDetect="+isDetect);
        }

       // tv_content = (TextView) this.findViewById(R.id.tv_content);


    }

}