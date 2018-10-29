package com.zhy.zlib.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zhy.zlib.R;
import com.zhy.zlib.listener.CommonListener;
import com.zhy.zlib.utils.LogUtils;
import com.zhy.zlib.view.TopBar;

public abstract class BaseActivity extends AppCompatActivity implements CommonListener {

    public boolean isBottom = false;
    public boolean hasTopBar = true;
    private View rootView;
    private LinearLayout content;
    public TopBar topBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = View.inflate(this, R.layout.contentview, null);
        topBar = rootView.findViewById(R.id.topbar);
        setContentView(rootView);
    }

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
    public View contentView(View view) {
        setContentView(view);
        return view;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void log(String Tag, String value) {
        LogUtils.i(Tag, value);
    }
}
