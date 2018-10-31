package com.zhy.zlib.Base;

import android.app.Application;

public class LibApplication extends Application {
    private static LibApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static LibApplication getInstance() {
        return application;
    }
}
