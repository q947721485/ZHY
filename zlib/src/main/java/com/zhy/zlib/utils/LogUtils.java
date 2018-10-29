package com.zhy.zlib.utils;

import android.util.Log;

public class LogUtils {
//    public static boolean showLog = "debug".equals(BuildConfig.BUILD_TYPE);//不显示日志
    public static boolean showLog = true;//显示日志

    public static boolean debug = false;// 正式环境
//    public static boolean debug = true;// 测试环境

    public static String tag = "DBX";

    public static void d(String local, String msg) {
        if (showLog)
            Log.d(local, msg);
    }

    public static void t(String local, String msg) {
        if (showLog)
            Log.d(tag, local + "\n" + msg);
    }

    public static void d(String msg) {
        if (showLog)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (showLog)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (showLog)
            Log.e(tag, msg);
    }

    public static void i(String msg) {
        if (showLog)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (showLog)
            Log.v(tag, msg);
    }

    public static void v(String msg) {
        if (showLog)
            Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (showLog)
            Log.w(tag, msg);
    }

    public static void w(String msg) {
        if (showLog)
            Log.w(tag, msg);
    }

    public static void e(String msg) {
        if (showLog)
            Log.e(tag, msg);
    }

    public static void printStack() {
        Throwable throwable = new Throwable();
        Log.w(tag, Log.getStackTraceString(throwable));
    }

}
