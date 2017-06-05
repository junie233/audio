package com.a78.audio;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.a78.audio.jni.VoiceChangeJni;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,Environment.getExternalStorageDirectory().getAbsolutePath()+"/1234.mp3");
        new VoiceChangeJni().fix(Environment.getExternalStorageDirectory().getAbsolutePath()+"/1234.mp3",2);
    }


}
