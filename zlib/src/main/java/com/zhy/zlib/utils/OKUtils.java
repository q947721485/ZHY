package com.zhy.zlib.utils;

import android.os.Handler;
import android.os.Looper;


import com.zhy.zlib.listener.CommonListener;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by YangYang on 2017/7/27.
 */

public class OKUtils {
    private static OkHttpClient okHttpClient;
    /**
     * @param url      请求地址
     * @param Tag      请求标记
     * @param listener 回调
     * @param strs     请求参数
     */
    public static void get(String url, final String Tag, final CommonListener listener, String... strs) {
        Request.Builder b = new Request.Builder();
        StringBuilder stringBuilder = new StringBuilder(url);
        if (strs != null) {
            stringBuilder.append("?");
            for (int i = 0; i < strs.length; i += 2) {
                if (i == strs.length - 2) {
                    stringBuilder.append(strs[i] + "=" + strs[i + 1]);
                } else
                    stringBuilder.append(strs[i] + "=" + strs[i + 1] + "&");
            }
        }
        LogUtils.e(Tag + "==",  stringBuilder.toString());
        Request request = b.url(stringBuilder.toString()).build();
        result(Tag, listener, request);
    }

    /**
     * @param url      请求地址
     * @param Tag      请求标记
     * @param listener 回调
     * @param strs     请求参数
     */
    public static void post(String url, final String Tag, final CommonListener listener, String... strs) {
        Request.Builder b = new Request.Builder();
        SortedMap<String, String> map = new TreeMap<>();
        FormBody.Builder body = new FormBody.Builder();
        StringBuilder stringBuilder = new StringBuilder(url);
        if (strs != null)
            stringBuilder.append("?");
        for (int i = 0; i < strs.length; i += 2) {
            body.add(strs[i], strs[i + 1]);
            if (i == strs.length - 2) {
                stringBuilder.append(strs[i] + "=" + strs[i + 1]);
            } else
                stringBuilder.append(strs[i] + "=" + strs[i + 1] + "&");
            map.put(strs[i], strs[i + 1]);
        }
        Request request = b.url(url).tag(Tag).post(body.build()).build();
        result(Tag, listener, request);
    }

    private static void result(final String Tag, final CommonListener listener, Request request) {
        Call call = getInstance().newCall(request);
        final Handler handler = new Handler(Looper.getMainLooper());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure(Tag, e.getMessage());
                        listener.onFinish(Tag,e.getMessage());
                        LogUtils.e("===结果onFailure===" + Tag, e == null ? "请求失败" : e.toString());
                    }
                });
            }
            @Override
            public void onResponse(Call call, final Response response) throws
                    IOException {
                final String str = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!str.equals("")) {
                                JSONObject object = new JSONObject(str);
                                // 请求成功
                                if (str.contains("code") &&
                                        object.optInt("code", 0) == 200) {
                                    LogUtils.e("===结果onSuccess===" + Tag, str);
                                    listener.onSuccess(Tag, str);
                                    // 请求失败
                                } else if (str.contains("code")){
                                    LogUtils.e("===结果onException===" + Tag, str);
                                    listener.onException(Tag, str);
                                }else {
                                    listener.onException(Tag,str);
                                }

                            }
                            listener.onFinish(Tag,str);
                        } catch (Exception e) {

                        }
                    }
                });

            }

        });
    }

    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (OKUtils.class) {
                if (okHttpClient == null)
                    okHttpClient = new OkHttpClient();
            }
        }
        return okHttpClient;
    }

}
