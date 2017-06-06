package com.a78.audio.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.a78.audio.MainApplication;
import com.a78.audio.R;
import com.a78.audio.jni.VoiceChangeJni;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class VoiceChangeActivity extends BasePlayActivity {

    @InjectView(R.id.play_btn)
    Button playBtn;
    @InjectView(R.id.vc_luoli_btn)
    Button vcLuoliBtn;
    @InjectView(R.id.vc_jingsong_btn)
    Button vcJingsongBtn;
    @InjectView(R.id.vc_dashu_btn)
    Button vcDashuBtn;
    @InjectView(R.id.vc_gaoguai_btn)
    Button vcGaoguaiBtn;
    @InjectView(R.id.vc_kongling_btn)
    Button vcKonglingBtn;


    @Override
    void initView() {
        setContentView(R.layout.activity_voice_change);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.play_btn, R.id.vc_luoli_btn, R.id.vc_jingsong_btn, R.id.vc_dashu_btn, R.id.vc_gaoguai_btn, R.id.vc_kongling_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.play_btn:
                changVoice(0);
                break;
            case R.id.vc_luoli_btn:
                changVoice(1);
                break;
            case R.id.vc_jingsong_btn:
                changVoice(3);
                break;
            case R.id.vc_dashu_btn:
                changVoice(2);
                break;
            case R.id.vc_gaoguai_btn:
                changVoice(4);
                break;
            case R.id.vc_kongling_btn:
                changVoice(5);
                break;
        }
    }

    private void changVoice(final int type) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                new VoiceChangeJni().fix(path,type);
            }
        });


    }



}
