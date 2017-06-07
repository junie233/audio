package com.a78.audio.activity.base;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Environment;

import com.iflytek.debuglog.DebugLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by niejun on 2017/6/7.
 */

public class HighRecordPlayController extends RecordPlayController{

    private AudioRecord audioRecord;
    private AudioTrack audioTrack;

    private File mAudioFile;
    private byte[] mBuffer;

    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;
    boolean isRecording = false;

    private static final int BUFFER_SIZE = 2048;

    public HighRecordPlayController() {
        mBuffer = new byte[BUFFER_SIZE];
    }

    @Override
    public void startRecord() {
        try {
            mAudioFile = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/a78/audio/"+System.currentTimeMillis()+".pcm");
            mAudioFile.getParentFile().mkdirs();
            mAudioFile.createNewFile();

            fileOutputStream = new FileOutputStream(mAudioFile);

            int audioSource = MediaRecorder.AudioSource.MIC;
            int sampleRate = 44100;
            int channelConfig = AudioFormat.CHANNEL_IN_MONO;
            int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
            int minBufferSize = AudioRecord.getMinBufferSize(sampleRate,channelConfig,audioFormat);

            audioRecord = new AudioRecord(audioSource,sampleRate,channelConfig,audioFormat,Math.max(minBufferSize, BUFFER_SIZE));

            audioRecord.startRecording();
            isRecording = true;
            while (isRecording) {
                int read= audioRecord.read(mBuffer,0,BUFFER_SIZE);
                DebugLog.d("录用数据大小"+read);
                if(read > 0 ) {
                    fileOutputStream.write(mBuffer,0,read);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            isRecording =false;
            audioRecord.release();
        }

    }

    @Override
    public void stopRecord() {
        if(audioRecord != null) {
            isRecording = false;
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }
    }

    @Override
    public void startPlay() {
        try {
            int streamType = AudioManager.STREAM_MUSIC;
            int sampleRate = 44100;
            int audioFormat  = AudioFormat.ENCODING_PCM_16BIT;
            int mode = AudioTrack.MODE_STREAM;
            int channelConfig = AudioFormat.CHANNEL_OUT_MONO;  //输入是in,输出是out
            int minBufferSize = AudioTrack.getMinBufferSize(sampleRate,channelConfig,audioFormat);
            audioTrack = new AudioTrack(streamType,sampleRate,channelConfig,audioFormat,Math.max(BUFFER_SIZE,minBufferSize),mode);
            fileInputStream = new FileInputStream(mAudioFile);
            DebugLog.d("准备播放");
            audioTrack.play();

            int read;
            while((read = fileInputStream.read(mBuffer)) > 0) {
                int ret = audioTrack.write(mBuffer,0,read);
                DebugLog.d("读取数据大小"+ret);
                switch (ret) {
                    case AudioTrack.ERROR_BAD_VALUE:
                    case AudioTrack.ERROR_DEAD_OBJECT:
                    case AudioTrack.ERROR_INVALID_OPERATION:
                        DebugLog.d("playFail");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stopPlay();
        }

    }

    @Override
    public void stopPlay() {
        if(audioTrack != null) {
            audioTrack.stop();
            audioTrack.release();
            audioTrack = null;
        }
    }

    @Override
    public void release() {
        stopRecord();
        stopPlay();
    }
}
