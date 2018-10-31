package com.zhy.zlib.Base;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zhy.zlib.listener.CommonListener;
import com.zhy.zlib.utils.LogUtils;

public abstract class LibActivity extends AppCompatActivity implements CommonListener {

    @Override
    public void onSuccess(String Tag, String value) {

    }

    @Override
    public void onException(String Tag, String value) {

    }

    @Override
    public void onFailure(String Tag, String value) {

    }

    @Override
    public void onFinish(String Tag, String value) {

    }


    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void log(String Tag, String value) {
        LogUtils.i(this.getClass().getName() + "==" + Tag, value);
    }

    @Override
    public View getView(int id) {
        return View.inflate(this, id, null);
    }
}
