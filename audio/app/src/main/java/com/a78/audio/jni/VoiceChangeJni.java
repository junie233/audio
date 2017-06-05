package com.a78.audio.jni;

/**
 * Created by niejun on 2017/6/6.
 */

public class VoiceChangeJni {

        static {
                System.loadLibrary("voice_changer");
                System.loadLibrary("fmod");
                System.loadLibrary("fmodL");
        }

        public native void fix(String path,int mode);

}
