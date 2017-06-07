package com.a78.audio.activity.base;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.widget.Toast;

import com.iflytek.debuglog.DebugLog;

import java.io.File;
import java.io.IOException;

/**
 * Created by niejun on 2017/6/7.
 */

public class LowRecordPlayController extends RecordPlayController{


    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private File mAudioFile;


    @Override
    public void startRecord() {
        try {
            mediaRecorder = new MediaRecorder();
            mAudioFile = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/a78/audio/"+System.currentTimeMillis()+".m4a");
            mAudioFile.getParentFile().mkdirs();
            mAudioFile.createNewFile();

            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncodingBitRate(44100);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setAudioEncodingBitRate(96000);
            mediaRecorder.setOutputFile(mAudioFile.getAbsolutePath());

            mediaRecorder.prepare();
            mediaRecorder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stopRecord() {
        if(mediaRecorder != null){
            try {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
            } catch (Exception e) {
                DebugLog.e(e);
            }
        }
    }

    @Override
    public void startPlay() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(mAudioFile.getAbsolutePath());
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlay();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    stopPlay();
                    return true;
                }
            });
            mediaPlayer.setVolume(1,1);
            mediaPlayer.setLooping(false);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopPlay() {
        if(mediaPlayer!=null) {
            mediaPlayer.setOnErrorListener(null);
            mediaPlayer.setOnCompletionListener(null);
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void release() {
        stopPlay();
        stopRecord();
    }


}
