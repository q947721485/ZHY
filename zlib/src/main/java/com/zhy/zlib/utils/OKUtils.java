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
    private static final String key = "a2dc8a390e92ab87678a5bf922fbd5dd";

    /**
     * @param url      请求地址
     * @param Tag      请求标记
     * @param listener 回调
     * @param strs     请求参数
     */
    public static void get(String url, final String Tag, final CommonListener listener, String... strs) {
        LogUtils.i(strs.toString());
        Request.Builder b = new Request.Builder();
//        b.header(Constants.SOURCE, "ANDROID");
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
        Request request = b.url(stringBuilder.toString()).build();
        LogUtils.e(Tag + "==", stringBuilder.toString());
        Call call = getInstance().newCall(request);
        final Handler handler = new Handler(Looper.getMainLooper());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure(Tag, e.getMessage());
//                        ToastUtils.showToast(BaseActivity.context, "您距离网络十万八千里", ToastUtils.LENGTH_SHORT);
                        LogUtils.e("===结果onFailure===" + Tag, e == null ? "请求失败" : e.toString());
                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, final Response response) throws
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
                                    LogUtils.e("===结果onSuccess===" + Tag, str.toString());
                                    listener.onSuccess(Tag, str);
                                    // 请求失败
                                } else if(str.contains("code")){
                                    LogUtils.e("===结果onException===" + Tag, str.toString());
                                    listener.onException(Tag, str);
                                }else {
                                    listener.onException(Tag, str);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    /**
     * @param url      请求地址
     * @param Tag      请求标记
     * @param listener 回调
     * @param strs     请求参数
     */
    public static void post(String url, final String Tag, final CommonListener listener, String... strs) {
        Request.Builder b = new Request.Builder();
        b.header("Content-Type", "application/json");
//        b.header(Constants.VERSION, BaseApplication.getAppVersionName(BaseActivity.context));
//        b.header(Constants.SOURCE, "ANDROID");
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
        LogUtils.e(Tag + "==",  stringBuilder.toString());
        Request request = b.url(url).tag(Tag).post(body.build()).build();
        Call call = getInstance().newCall(request);
        final Handler handler = new Handler(Looper.getMainLooper());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure(Tag, e.getMessage());
                        LogUtils.e("===结果onFailure===" + Tag, e == null ? "请求失败" : e.toString());
                    }
                });
            }
            @Override
            public void onResponse(okhttp3.Call call, final Response response) throws
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
                                    saveHeader(response);
                                    LogUtils.e("===结果onSuccess===" + Tag, str.toString());
                                    listener.onSuccess(Tag, str);
                                    // 请求失败
                                } else if (str.contains("code")){
                                    LogUtils.e("===结果onException===" + Tag, str.toString());
                                    listener.onException(Tag, str);
                                }else {
                                    listener.onException(Tag,str);
                                }

                            }
                        } catch (Exception e) {

                        }
                    }
                });

            }

        });
    }

    /**
     * 下载文件
     *
     * @param url      下载地址
     * @param callback 回调接口
     */
    public static void getFile(String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
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

    private static void saveHeader(Response response) {
        if (response == null) {
            return;
        }
//        if (!TextUtils.isEmpty(response.header(Constants.U_I))) {
//            Hawk.put(Constants.U_I, response.header(Constants.U_I));
//            //            LogUtils.d(Constants.U_I + "\n" + response.header(Constants.U_I));
//        }

    }

    public static String createSign(SortedMap<String, String> packageParams, String key) throws NoSuchAlgorithmException {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();

        for (Object e : es) {
            Map.Entry entry = (Map.Entry) e;
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"signature".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }

        sb.append("key=" + key);
//        String sign = MathUtils.getMD5String(sb.toString()).toUpperCase();
        return "";
    }

    public static void postFile(String address, Map<String, String> map, Callback callback, File file, String fileName) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }

        if (file.exists()) {
            String TYPE = "application/octet-stream";
            RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE), file);

            RequestBody requestBody = builder
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(fileName, file.getName(), fileBody)
                    .build();

            Request request = new Request.Builder()
                    .url(address)
                    .post(requestBody)
                    .build();
            getInstance().newCall(request).enqueue(callback);
        }

    }

    public static void postFile(String url, final String Tag, Map<String, String> map, File file, String fileName, final CommonListener listener) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }

        if (file.exists()) {
            String TYPE = "application/octet-stream";
            RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE), file);

            RequestBody requestBody = builder
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(fileName, file.getName(), fileBody)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            final Handler handler = new Handler(Looper.getMainLooper());
            getInstance().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailure(Tag, e.getMessage());
                            LogUtils.e("===结果onFailure===" + Tag, e == null ? "请求失败" : e.toString());
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
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
                                        saveHeader(response);
                                        LogUtils.e("===结果onSuccess===" + Tag, str.toString());
                                        listener.onSuccess(Tag, str);
                                        // 请求失败
                                    } else if (str.contains("code")){
                                        LogUtils.e("===结果onException===" + Tag, str.toString());
                                        listener.onException(Tag, str);
                                    }else {
                                        listener.onException(Tag,str);
                                    }

                                }
                            } catch (Exception e) {

                            }
                        }
                    });
                }
            });
        }

    }

}
