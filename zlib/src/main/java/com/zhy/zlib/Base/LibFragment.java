package com.zhy.zlib.Base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhy.zlib.listener.CommonListener;
import com.zhy.zlib.utils.LogUtils;

public abstract class LibFragment extends Fragment  implements CommonListener {

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
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void log(String Tag, String value) {
        LogUtils.i(this.getClass().getName() + "==" + Tag, value);
    }

    @Override
    public View getView(int id) {
        return View.inflate(getContext(),id,null);
    }
}
