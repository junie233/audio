package com.a78.audio;

import android.app.Application;

import com.iflytek.debuglog.DebugLog;
import com.iflytek.debuglog.setting.Constant;

/**
 * Created by niejun on 2017/6/6.
 */

public class MainApplication extends Application{

    //全局文件路径
    private String path;

    @Override
    public void onCreate() {
        super.onCreate();
        DebugLog.getConfig().setLogLevel(Constant.All);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
