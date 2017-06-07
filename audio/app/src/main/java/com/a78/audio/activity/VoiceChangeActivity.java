package com.a78.audio.activity;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.a78.audio.MainApplication;
import com.a78.audio.R;
import com.a78.audio.jni.VoiceChangeJni;
import com.iflytek.debuglog.DebugLog;

import java.io.FileOutputStream;
import java.io.IOException;
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
    public void initView() {
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


    private final int kSampleRate = 44100;
    private final int kChannelMode = AudioFormat.CHANNEL_IN_STEREO;
    private final int kEncodeFormat = AudioFormat.ENCODING_PCM_16BIT;
    private AudioRecord mRecord;
    boolean mReqStop = false;

    private void init() {
        int minBufferSize = AudioRecord.getMinBufferSize(kSampleRate, kChannelMode,
                kEncodeFormat);
        mRecord = new AudioRecord(MediaRecorder.AudioSource.REMOTE_SUBMIX,
                kSampleRate, kChannelMode, kEncodeFormat, minBufferSize * 2);
    }

    private final int kFrameSize = 2048;
    private String filePath = "/sdcard/voice.pcm";

    private void recordAndPlay() {
        FileOutputStream os = null;
        mRecord.startRecording();
        try {
            os = new FileOutputStream(filePath);
            byte[] buffer = new byte[kFrameSize];
            int num = 0;
            while (!mReqStop) {
                num = mRecord.read(buffer, 0, kFrameSize);
                DebugLog.d("buffer = " + buffer.toString() + ", num = " + num);
                os.write(buffer, 0, num);
            }

           DebugLog.d("exit loop");
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            DebugLog.d("Dump PCM to file failed");
        }
        mRecord.stop();
        mRecord.release();
        mRecord = null;
        DebugLog.d("clean up");
    }



}
