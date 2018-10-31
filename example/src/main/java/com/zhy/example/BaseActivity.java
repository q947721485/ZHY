package com.zhy.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zhy.zlib.Base.LibActivity;
import com.zhy.zlib.listener.CommonListener;
import com.zhy.zlib.utils.LogUtils;

import butterknife.ButterKnife;

public abstract class BaseActivity extends LibActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView(savedInstanceState));
        ButterKnife.bind(this);
        initView();
    }
}
