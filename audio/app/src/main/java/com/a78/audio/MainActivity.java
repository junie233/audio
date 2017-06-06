package com.a78.audio;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.a78.audio.activity.VoiceChangeActivity;
import com.a78.audio.util.FileUtils;
import com.iflytek.debuglog.DebugLog;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.select_btn)
    Button selectBtn;
    @InjectView(R.id.biansheng_btn)
    Button bianshengBtn;
    @InjectView(R.id.fun_ll)
    LinearLayout funLl;

    private String TAG = MainActivity.class.getSimpleName();
    private MainApplication application;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        application = (MainApplication) getApplication();

//
//        DebugLog.d(TAG, Environment.getExternalStorageDirectory().getAbsolutePath() + "/1234.mp3");
//        new VoiceChangeJni().fix(Environment.getExternalStorageDirectory().getAbsolutePath() + "/1234.mp3", 2);
    }


    @OnClick({R.id.select_btn, R.id.biansheng_btn, R.id.fun_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_btn:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                //intent.setType(“image/*”);//选择图片
                //intent.setType(“audio/*”); //选择音频
                //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
                //intent.setType(“video/*;image/*”);//同时选择视频和图片
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
                break;
            case R.id.biansheng_btn:
                Intent bsIntent = new Intent(this, VoiceChangeActivity.class);
                startActivity(bsIntent);
                break;
            case R.id.fun_ll:
                break;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                application.setPath(FileUtils.getRealFilePath(this, uri));
                DebugLog.d(application.getPath());
                selectBtn.setVisibility(View.GONE);
                funLl.setVisibility(View.VISIBLE);

            }
        }
    }
}
