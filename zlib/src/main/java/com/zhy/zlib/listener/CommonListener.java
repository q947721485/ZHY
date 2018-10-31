package com.zhy.zlib.listener;

import android.os.Bundle;
import android.view.View;

/**
 * Created by YangYang on 2017/7/27.
 */

public interface
CommonListener {
    void onSuccess(String Tag, String value);

    void onException(String Tag, String value);

    void onFailure(String Tag, String value);

    void onFinish(String Tag, String value);

    View contentView(Bundle savedInstanceState);

    void initView();

    void showToast(String message);

    void log(String Tag, String value);

    View getView(int id);
}
