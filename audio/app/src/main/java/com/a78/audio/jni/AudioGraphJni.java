package com.a78.audio.jni;

/**
 * Created by niejun on 2017/6/11.
 */

public class AudioGraphJni {

    private final int mBufferSize;//缓冲区大小
    private final byte[] mOutBuffer; //输出缓冲区
    private final float[] mFloatInput; //临时缓冲区
    private final float[] mFloatOutput;

    static {
        System.loadLibrary("audiograph");
    }

    public AudioGraphJni(int mBufferSize) {
        this.mBufferSize = mBufferSize;
        this.mOutBuffer = new byte[mBufferSize];
        //底层中，两个byte对应一个float
        this.mFloatInput = new float[mBufferSize / 2];
        this.mFloatOutput = new float[mBufferSize / 2];
    }

    /**
     * @param ratio 0-2
     * @param input
     * @param sampleRate
     * @return
     */
    public synchronized byte[] process(float ratio,byte[] input,int sampleRate) {
        process(ratio,input,mOutBuffer,mBufferSize,sampleRate,mFloatInput,mFloatOutput);
        return mOutBuffer;
    }

    private native void process(float ratio ,byte[] in, byte[] out,
                               int size,int sampleRate,float[] floatInput,float[] floatOutput);




}
