package com.a78.audio.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.a78.audio.MainApplication;
import com.a78.audio.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;

/**
 * Base Activity
 */
public abstract class BasePlayActivity extends AppCompatActivity {

    protected String path;
    protected ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        executorService= Executors.newSingleThreadExecutor();
        path = ((MainApplication)getApplication()).getPath();
    }

    public abstract void initView();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdownNow();
    }
}
