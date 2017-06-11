package com.a78.audio.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.a78.audio.R;
import com.a78.audio.activity.base.HighRecordPlayController;
import com.a78.audio.activity.base.LowRecordPlayController;
import com.a78.audio.activity.base.RecordPlayController;
import com.a78.audio.activity.base.ShiftRecordPlayController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RecordPlayActivity extends BasePlayActivity {

    @InjectView(R.id.record_time_tv)
    TextView recordTimeTv;
    @InjectView(R.id.record_btn)
    Button recordBtn;
    @InjectView(R.id.play_btn)
    Button playBtn;


    //是否正在录音
    boolean isRecording = false;
    //是否在播放
    boolean isPlaying = false;
    private ExecutorService executorService;

    private RecordPlayController recordPlayController;

    @Override
    public void initView() {
        setContentView(R.layout.activity_record_play);
        ButterKnife.inject(this);
        recordPlayController = new ShiftRecordPlayController();
        executorService = Executors.newSingleThreadExecutor();
    }

    @OnClick({R.id.record_btn,R.id.play_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.record_btn:
                if (!isRecording) {
                    startRecord();
                    isRecording = true;
                    recordBtn.setText("停止录音");
                } else {
                    stopRecord();
                    isRecording = false;
                    recordBtn.setText("开始录音");
                }
                break;
            case R.id.play_btn:
                if(!isPlaying) {
                    startPlay();
                    isPlaying = true;
                    playBtn.setText("停止播放");
                } else {
                    stopPlay();
                    isPlaying = false;
                    playBtn.setText("开始播放");
                }
                break;
        }
    }

    /**
     * 开始录音
     */
    private void startRecord() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                recordPlayController.startRecord();
            }
        });
    }


    /**
     * 停止录音
     */
    private void stopRecord() {
        recordPlayController.stopRecord();
    }

    /**
     * 开始播放
     */
    private void startPlay() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                recordPlayController.startPlay();
            }
        });
    }


    /**
     * 停止播放
     */
    private void stopPlay() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                recordPlayController.stopPlay();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdownNow();
        recordPlayController.release();
    }


}
